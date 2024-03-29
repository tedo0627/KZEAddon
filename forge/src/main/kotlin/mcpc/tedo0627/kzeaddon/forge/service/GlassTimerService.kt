package mcpc.tedo0627.kzeaddon.forge.service

import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.Minecraft
import net.minecraft.world.item.Items
import net.minecraftforge.client.event.RenderGuiOverlayEvent
import net.minecraftforge.client.gui.overlay.GuiOverlayManager
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class GlassTimerService {

    private var currentTime = -1

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.START) return

        val time = getTime()
        if (time == null) {
            currentTime = -1
            return
        }

        if (currentTime == -1) {
            currentTime = time * 20
            return
        }

        currentTime--
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

    @SubscribeEvent
    fun onRenderGuiOverlayEvent(event: RenderGuiOverlayEvent.Pre) {
        if (GuiOverlayManager.getOverlays()[0] != event.overlay) return
        val poseStack = event.poseStack

        if (currentTime == -1) return
        if (!AddonOptions.displayGlassTimer.get()) return

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