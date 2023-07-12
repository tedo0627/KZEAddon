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

            val client = Minecraft.getInstance()
            val renderer = client.font

            val text = (currentTime / 20 + 1).toString()
            val x = (client.window.guiScaledWidth / 2 - 20 - renderer.width(text) / 2).toFloat()
            val y = (client.window.guiScaledHeight - 49).toFloat()

            renderer.draw(
                poseStack,
                (currentTime / 20 + 1).toString(),
                x + AddonOptions.glassTimerOverlayLocationX.get(),
                y + AddonOptions.glassTimerOverlayLocationY.get(),
                16777215
            )
        }
    }

    private fun getTime(): Int? {
        val client = Minecraft.getInstance()
        val player = client.player ?: return null

        val itemStack = player.inventory.getItem(8) ?: return null
        if (itemStack.item != Items.RED_STAINED_GLASS_PANE) return null
        if (!itemStack.hasCustomHoverName()) return null

        val name = itemStack.hoverName.string
        return name.toIntOrNull()
    }
}