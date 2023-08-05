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

            val name = itemStack.hoverName.string
            val split = name.split(" ").filter { it.isNotEmpty() }
            if (split.size != 3) continue

            val currentBullet = split[1]
            val remainingBullet = split[2]
            if (currentBullet.toIntOrNull() == null || remainingBullet.toIntOrNull() == null) return

            val x = (mc.window.guiScaledWidth / 2).toFloat() - 20 * (4 - slot)
            val y = (mc.window.guiScaledHeight - 49).toFloat()
            val font = mc.font

            val currentOption = AddonOptions.currentBulletOverlay
            poseStack.pushPose()
            poseStack.scale(currentOption.scalePercent, currentOption.scalePercent, 1.0f)
            font.draw(
                poseStack,
                currentBullet,
                (x - font.width(currentBullet) / 2 + currentOption.x) / currentOption.scalePercent,
                (y + currentOption.y) / currentOption.scalePercent,
                currentOption.color
            )
            poseStack.popPose()

            val remainingOption = AddonOptions.remainingBulletOverlay
            poseStack.pushPose()
            poseStack.scale(remainingOption.scalePercent, remainingOption.scalePercent, 1.0f)
            font.draw(
                poseStack,
                remainingBullet,
                (x - font.width(remainingBullet) / 2 + remainingOption.x) / remainingOption.scalePercent,
                (y + 10 + remainingOption.y) / remainingOption.scalePercent,
                remainingOption.color
            )
            poseStack.popPose()
        }
    }
}