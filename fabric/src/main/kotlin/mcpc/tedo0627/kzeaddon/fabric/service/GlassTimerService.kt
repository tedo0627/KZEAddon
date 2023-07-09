package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.Items

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

        HudRenderCallback.EVENT.register { _, _ ->
            if (currentTime == -1) return@register
            if (!AddonOptions.displayGlassTimer.value) return@register

            val client = MinecraftClient.getInstance()
            val renderer = client.textRenderer

            val text = (currentTime / 20 + 1).toString()
            val x = (client.window.scaledWidth / 2 - 20 - renderer.getWidth(text) / 2).toFloat()
            val y = (client.window.scaledHeight - 49).toFloat()

            renderer.drawWithShadow(
                MatrixStack(),
                (currentTime / 20 + 1).toString(),
                x + AddonOptions.glassTimerOverlayLocationX.value,
                y + AddonOptions.glassTimerOverlayLocationY.value,
                16777215
            )
        }
    }

    private fun getTime(): Int? {
        val client = MinecraftClient.getInstance()
        val player = client.player ?: return null

        val itemStack = player.inventory.getStack(8) ?: return null
        if (itemStack.item != Items.RED_STAINED_GLASS_PANE) return null
        if (!itemStack.hasCustomName()) return null

        val name = itemStack.name.string
        return name.toIntOrNull()
    }
}