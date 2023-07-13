package mcpc.tedo0627.kzeaddon.forge.service

import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.util.FastColor
import net.minecraftforge.client.event.RenderGuiOverlayEvent
import net.minecraftforge.client.gui.overlay.GuiOverlayManager
import net.minecraftforge.eventbus.api.SubscribeEvent

class CrosshairService {

    @SubscribeEvent
    fun onRenderGuiOverlayEvent(event: RenderGuiOverlayEvent.Pre) {
        if (GuiOverlayManager.getOverlays()[0] != event.overlay) return
        val poseStack = event.poseStack

        if (!AddonOptions.crosshair.get()) return

        val mc = Minecraft.getInstance()
        val window = mc.window
        val color = FastColor.ARGB32.color(
            AddonOptions.crosshairColorA.get(),
            AddonOptions.crosshairColorR.get(),
            AddonOptions.crosshairColorG.get(),
            AddonOptions.crosshairColorB.get()
        )
        mutableListOf(
            // 横
            Pos(-6, 0, -5, 1),
            Pos(-4, 0, -2, 1),
            Pos(1, 0, 3, 1),
            Pos(4, 0, 5, 1),

            // 真ん中
            Pos(-1, 0, 0, 1),

            // 縦
            Pos(-1, -5, 0, -4),
            Pos(-1, -3, 0, -1),
            Pos(-1, 2, 0, 4),
            Pos(-1, 3, 0, 6),
        ).forEach {
            GuiComponent.fill(
                poseStack,
                window.guiScaledWidth / 2 + it.x,
                window.guiScaledHeight / 2 + it.y,
                window.guiScaledWidth / 2 + it.x2,
                window.guiScaledHeight / 2 + it.y2,
                -90,
                color
            )
        }
    }

    class Pos(val x: Int, val y: Int, val x2: Int, val y2: Int)
}