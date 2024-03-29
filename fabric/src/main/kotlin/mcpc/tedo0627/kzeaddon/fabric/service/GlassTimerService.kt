package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.Minecraft
import net.minecraft.world.item.Items

class GlassTimerService {

    private var currentTime = -1

    init {
        ClientTickEvents.END_CLIENT_TICK.register {
            val time = getTime()
            if (time == null) {
                currentTime = -1
                return@register
            }

            if (currentTime == -1) {
                currentTime = time * 20
                return@register
            }

            currentTime--
        }

        HudRenderCallback.EVENT.register { poseStack, _ ->
            if (currentTime == -1) return@register
            if (!AddonOptions.displayGlassTimer.get()) return@register

            val mc = Minecraft.getInstance()
            val font = mc.font

            val text = (currentTime / 20 + 1).toString()
            val x = (mc.window.guiScaledWidth / 2 - 20 - font.width(text) / 2).toFloat()
            val y = (mc.window.guiScaledHeight - 49).toFloat()

            val option = AddonOptions.glassTimerOverlay
            poseStack.pushPose()
            poseStack.scale(option.scalePercent, option.scalePercent, 1.0f)
            font.draw(
                poseStack,
                text,
                (x + option.x) / option.scalePercent,
                (y + option.y) / option.scalePercent,
                option.color
            )
            poseStack.popPose()
        }
    }

    private fun getTime(): Int? {
        val mc = Minecraft.getInstance()
        val player = mc.player ?: return null

        val itemStack = player.inventory.getItem(8) ?: return null
        if (itemStack.item != Items.RED_STAINED_GLASS_PANE) return null
        if (!itemStack.hasCustomHoverName()) return null

        val name = itemStack.hoverName.string
        return name.toIntOrNull()
    }
}