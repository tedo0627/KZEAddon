package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.event.InventoryUpdateCallback
import mcpc.tedo0627.kzeaddon.fabric.item.KnifeItem
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items

class KnifeAnimationService(checkGeckoLib: Boolean) {

    private val items: Map<Int, Item?> = mutableMapOf(
        1031 to "survival_knife",
        1040 to "crowbar",
        1131 to "pen_knife",
        1141 to "tetra_knife",
        1171 to "raw_fish"
    ).mapValues {
        if (!checkGeckoLib) return@mapValues null

        val item = KnifeItem(it.value)
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation("kzeaddon", item.name), item)
        item
    }

    private var currentId = -1
    private var currentItem: ItemStack? = null

    init {
        InventoryUpdateCallback.EVENT.register { slot, itemStack ->
            if (!checkGeckoLib) return@register true
            if (!AddonOptions.knifeAnimation.get()) return@register true

            if (itemStack.item != Items.DIAMOND_HOE) return@register true

            val nbt = itemStack.orCreateTag.copy()
            val customModelData = nbt.getInt("CustomModelData")
            val item = items[customModelData] ?: return@register true

            val newItemStack = item.defaultInstance
            newItemStack.tag = nbt

            val player = Minecraft.getInstance().player ?: return@register true
            player.inventory.setItem(slot, newItemStack)
            val knife = item as? KnifeItem
            knife?.runIdle(player, newItemStack)
            return@register false
        }

        ClientTickEvents.END_CLIENT_TICK.register {
            if (!checkGeckoLib) return@register
            if (!AddonOptions.knifeAnimation.get()) return@register

            val player = Minecraft.getInstance().player ?: return@register
            val itemStack = player.inventory.getSelected()
            val id = itemStack.orCreateTag.getInt("CustomModelData")
            if (currentId == id) return@register

            val oldItemStack = currentItem
            val oldItem = oldItemStack?.item as? KnifeItem
            oldItem?.runIdle(player, oldItemStack)

            currentItem = itemStack
            currentId = id
            val item = itemStack.item as? KnifeItem ?: return@register
            item.runAnimation(player, itemStack)
        }
    }
}