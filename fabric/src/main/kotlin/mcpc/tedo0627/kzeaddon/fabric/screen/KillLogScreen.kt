package mcpc.tedo0627.kzeaddon.fabric.screen

import mcpc.tedo0627.kzeaddon.fabric.service.KillLogService
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import kotlin.math.max
import kotlin.math.min

class KillLogScreen(
    private val key: KeyBinding,
    private val list: List<KillLogService.KillLog>,
    private val service: KillLogService
) : Screen(Text.literal("キルログ")) {

    private var scroll = 0

    override fun render(matrixStack: MatrixStack, i: Int, j: Int, f: Float) {
        val size = list.size
        val start = if (size <= 20) 0 else size - 20 - scroll
        val end = size - scroll
        for (index in start until end) {
            val killLog = list[index]
            service.renderWeapon(killLog, end - 1 - index)
        }

        super.render(matrixStack, i, j, f)
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
        if (key.matchesKey(i, j)) {
            close()
            return true
        }

        return super.keyPressed(i, j, k)
    }

    override fun mouseClicked(d: Double, e: Double, i: Int): Boolean {
        if (key.matchesMouse(i)) {
            close()
            return true
        }

        return super.mouseClicked(d, e, i)
    }

    override fun shouldPause() = false
}