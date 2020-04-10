package mcpc.tedo0627.kzeaddon.listener

import mcpc.tedo0627.kzeaddon.KZEAddon
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.common.util.Constants
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class ReloadDurationListener(val addon: KZEAddon) {

    private var reloading = false
    private var slot = -1
    private var time = 0

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.START) return

        val mc = Minecraft.getInstance()
        val player = mc.player ?: return
        val inventory = player.inventory
        val item = inventory.getCurrentItem()
        if (item.item != Items.DIAMOND_HOE) return

        if (reloading) {
            if (slot != inventory.currentItem) {
                reloading = false
                slot = -1
                time = 0
                return
            }

            time--
            if (time <= 0) {
                reloading = false
                slot = -1
                time = 0
            }
        } else {
            if (!item.displayName.formattedText.startsWith("§c")) return
            reloading = true
            slot = inventory.currentItem
            time = item.reloadDuration
        }
    }

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent) {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return

        val mc = Minecraft.getInstance()
        if (mc.player == null) return
        if (!addon.displayReloadDuration) return

        val window = event.window
        val x = (window.scaledWidth / 2).toFloat()
        val y = (window.scaledHeight).toFloat()
        mc.fontRenderer.drawString("${time / 20}.${time / 2 % 10}", x - 115, y - 15, 16777215)
    }

    private val ItemStack.reloadDuration: Int
        get() {
            val nbt = tag ?: return 0
            if (!nbt.contains("display", Constants.NBT.TAG_COMPOUND)) return 0

            val display = nbt.getCompound("display")
            if (display.getTagId("Lore") != Constants.NBT.TAG_LIST.toByte()) return 0

            val list = display.getList("Lore", Constants.NBT.TAG_STRING)
            for (i in 0 until list.size) {
                val str = list.getString(i)
                if (str.length < 31) continue
                return str.substring(31, str.length - 2).toIntOrNull() ?: continue
            }

            return 0
        }
}