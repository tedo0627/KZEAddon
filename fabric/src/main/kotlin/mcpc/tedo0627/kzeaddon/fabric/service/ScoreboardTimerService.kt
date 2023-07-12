package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.Minecraft

class ScoreboardTimerService {

    init {
        HudRenderCallback.EVENT.register { poseStack, _ ->
            if (!AddonOptions.displayScoreboardTimer.get()) return@register

            val mc = Minecraft.getInstance()
            val player = mc.player ?: return@register
            val scoreboard = player.scoreboard
            val objective = scoreboard.getDisplayObjective(0) ?: return@register
            val score = scoreboard.getPlayerScores(player.scoreboardName)[objective] ?: return@register
            val value = score.score
            if (value == 0) return@register

            val font = mc.font
            val text = value.toString()
            val x = (mc.window.guiScaledWidth / 2 - 20 - font.width(text) / 2).toFloat()
            val y = (mc.window.guiScaledHeight - 49).toFloat()

            font.draw(
                poseStack,
                text,
                x + AddonOptions.scoreboardTimerOverlayLocationX.get(),
                y + AddonOptions.scoreboardTimerOverlayLocationY.get(),
                16777215
            )
        }
    }
}