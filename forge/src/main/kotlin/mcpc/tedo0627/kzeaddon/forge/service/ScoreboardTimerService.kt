package mcpc.tedo0627.kzeaddon.forge.service

import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.RenderGuiOverlayEvent
import net.minecraftforge.client.gui.overlay.GuiOverlayManager
import net.minecraftforge.eventbus.api.SubscribeEvent

class ScoreboardTimerService {

    @SubscribeEvent
    fun onRenderGuiOverlayEvent(event: RenderGuiOverlayEvent.Pre) {
        if (GuiOverlayManager.getOverlays()[0] != event.overlay) return
        val poseStack = event.poseStack

        if (!AddonOptions.displayScoreboardTimer.get()) return

        val mc = Minecraft.getInstance()
        val player = mc.player ?: return
        val scoreboard = player.scoreboard
        val objective = scoreboard.getDisplayObjective(0) ?: return
        val score = scoreboard.getPlayerScores(player.scoreboardName)[objective] ?: return
        val value = score.score
        if (value == 0) return

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