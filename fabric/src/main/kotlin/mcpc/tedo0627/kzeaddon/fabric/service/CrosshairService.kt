package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.util.FastColor

class CrosshairService {

    init {
        HudRenderCallback.EVENT.register { poseStack, _ ->
            if (!AddonOptions.crosshair.get()) return@register

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
    }

    class Pos(val x: Int, val y: Int, val x2: Int, val y2: Int)
}