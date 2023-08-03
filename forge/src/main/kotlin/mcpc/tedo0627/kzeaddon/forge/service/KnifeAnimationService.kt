package mcpc.tedo0627.kzeaddon.forge.service

import mcpc.tedo0627.kzeaddon.forge.KZEAddon
import mcpc.tedo0627.kzeaddon.forge.event.InventoryUpdateEvent
import mcpc.tedo0627.kzeaddon.forge.item.KnifeItem
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.Minecraft
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import software.bernie.geckolib.GeckoLib

class KnifeAnimationService(private val checkGeckoLib: Boolean) {

    companion object {
        val REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, KZEAddon.MOD_ID)
    }

    private var items: Map<Int, RegistryObject<Item>?> = mutableMapOf(
        1031 to "survival_knife",
        1040 to "crowbar",
        1131 to "pen_knife",
        1141 to "tetra_knife",
        1171 to "raw_fish"
    ).mapValues {
        if (!checkGeckoLib) return@mapValues null
        REGISTRY.register(it.value) { KnifeItem(it.value) }
    }

    private var currentId = -1
    private var currentItem: ItemStack? = null

    init {
        if (checkGeckoLib) GeckoLib.initialize()
    }

    @SubscribeEvent
    fun onInventoryUpdate(event: InventoryUpdateEvent) {
        if (!checkGeckoLib) return
        if (!AddonOptions.knifeAnimation.get()) return

        val itemStack = event.itemStack
        if (itemStack.item != Items.DIAMOND_HOE) return

        val nbt = itemStack.orCreateTag.copy()
        val customModelData = nbt.getInt("CustomModelData")
        val item = items[customModelData] ?: return

        val newItemStack = item.get().defaultInstance
        newItemStack.tag = nbt

        val player = Minecraft.getInstance().player ?: return
        player.inventory.setItem(event.slot, newItemStack)
        val knife = item.get() as? KnifeItem
        knife?.runIdle(player, newItemStack)
        event.isCanceled = true
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.START) return

        if (!checkGeckoLib) return
        if (!AddonOptions.knifeAnimation.get()) return

        val player = Minecraft.getInstance().player ?: return
        val itemStack = player.inventory.getSelected()
        val id = itemStack.orCreateTag.getInt("CustomModelData")
        if (currentId == id) return

        val oldItemStack = currentItem
        val oldItem = oldItemStack?.item as? KnifeItem
        oldItem?.runIdle(player, oldItemStack)

        currentItem = itemStack
        currentId = id
        val item = itemStack.item as? KnifeItem ?: return
        item.runAnimation(player, itemStack)
    }
}