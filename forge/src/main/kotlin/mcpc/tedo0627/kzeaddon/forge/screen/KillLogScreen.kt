package mcpc.tedo0627.kzeaddon.forge.screen

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.service.KillLogService
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

    override fun render(p_96562_: PoseStack, p_96563_: Int, p_96564_: Int, p_96565_: Float) {
        val size = list.size
        val start = if (size <= 20) 0 else size - 20 - scroll
        val end = size - scroll
        for (index in start until end) {
            val killLog = list[index]
            service.renderWeapon(killLog, end - 1 - index, PoseStack())
        }

        super.render(p_96562_, p_96563_, p_96564_, p_96565_)
    }

    override fun mouseScrolled(p_94686_: Double, p_94687_: Double, p_94688_: Double): Boolean {
        scroll -= p_94688_.toInt()

        val size = list.size
        if (size < 21) {
            scroll = 0
            return false
        }

        scroll = max(0, scroll)
        scroll = min(size - 20, scroll)
        return super.mouseScrolled(p_94686_, p_94687_, p_94688_)
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