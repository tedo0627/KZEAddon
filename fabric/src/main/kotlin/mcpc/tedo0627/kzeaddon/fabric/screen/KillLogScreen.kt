package mcpc.tedo0627.kzeaddon.fabric.screen

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import mcpc.tedo0627.kzeaddon.fabric.service.KillLogService
import net.minecraft.client.KeyMapping
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import kotlin.math.max
import kotlin.math.min

class KillLogScreen(
    private val key: KeyMapping,
    private val list: List<KillLogService.KillLog>,
    private val service: KillLogService
) : Screen(Component.literal("キルログ")) {

    private var scroll = 0

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        val size = list.size
        val start = if (size <= 20) 0 else size - 20 - scroll
        val end = size - scroll

        val option = AddonOptions.killLogOverlay
        poseStack.pushPose()
        poseStack.scale(option.scalePercent, option.scalePercent, 1.0f)
        for (index in start until end) {
            val killLog = list[index]
            service.renderWeapon(killLog, end - 1 - index, poseStack)
        }
        poseStack.popPose()

        super.render(poseStack, i, j, f)
    }

    override fun mouseScrolled(d: Double, e: Double, f: Double): Boolean {
        scroll -= f.toInt()

        val size = list.size
        if (size < 21) {
            scroll = 0
            return false
        }

        scroll = max(0, scroll)
        scroll = min(size - 20, scroll)
        return super.mouseScrolled(d, e, f)
    }

    override fun keyPressed(i: Int, j: Int, k: Int): Boolean {
        if (key.matches(i, j)) {
            onClose()
            return true
        }

        return super.keyPressed(i, j, k)
    }

    override fun mouseClicked(d: Double, e: Double, i: Int): Boolean {
        if (key.matchesMouse(i)) {
            onClose()
            return true
        }

        return super.mouseClicked(d, e, i)
    }

    override fun isPauseScreen() = false
}