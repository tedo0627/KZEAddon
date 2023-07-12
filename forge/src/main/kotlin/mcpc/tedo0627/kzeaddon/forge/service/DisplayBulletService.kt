package mcpc.tedo0627.kzeaddon.forge.service

import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.Minecraft
import net.minecraft.world.item.Items
import net.minecraftforge.client.event.RenderGuiOverlayEvent
import net.minecraftforge.client.gui.overlay.GuiOverlayManager
import net.minecraftforge.eventbus.api.SubscribeEvent

class DisplayBulletService {

    @SubscribeEvent
    fun onRenderGuiOverlayEvent(event: RenderGuiOverlayEvent.Pre) {
        if (GuiOverlayManager.getOverlays()[0] != event.overlay) return
        val poseStack = event.poseStack

        val mc = Minecraft.getInstance()
        val player = mc.player ?: return

        if (!AddonOptions.displayBullet.get()) return

        val inventory = player.inventory
        for (slot in 0 until 2) {
            val itemStack = inventory.items[slot]
            if (itemStack.item != Items.DIAMOND_HOE) return

            val name = itemStack.displayName.string
            val split = name.substring(1, name.length - 1).split(" ").filter { it.isNotEmpty() }
            if (split.size != 3) continue

            val currentBullet = split[1]
            val remainingBullet = split[2]
            if (currentBullet.toIntOrNull() == null || remainingBullet.toIntOrNull() == null) return

            val x = (mc.window.guiScaledWidth / 2).toFloat()
            val y = (mc.window.guiScaledHeight - 49).toFloat()
            val renderer = mc.font
            renderer.draw(
                poseStack,
                currentBullet,
                x - 20 * (4 - slot) - renderer.width(currentBullet) / 2 + AddonOptions.currentBulletOverlayLocationX.get(),
                y + AddonOptions.currentBulletOverlayLocationY.get(),
                16777215
            )
            renderer.draw(
                poseStack,
                remainingBullet,
                x - 20 * (4 - slot) - renderer.width(remainingBullet) / 2 + AddonOptions.remainingBulletOverlayLocationX.get(),
                y + 10 + AddonOptions.remainingBulletOverlayLocationY.get(),
                16777215
            )
        }
    }
}