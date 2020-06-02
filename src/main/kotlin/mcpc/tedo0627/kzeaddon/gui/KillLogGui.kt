package mcpc.tedo0627.kzeaddon.gui

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.listener.KillLogListener
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.gui.screen.Screen
import net.minecraft.util.text.StringTextComponent

class KillLogGui(val addon: KZEAddon, val listener: KillLogListener, val log: MutableList<String>) : Screen(StringTextComponent("kill log gui")) {

    private var scroll = 0

    override fun render(p_render_1_: Int, p_render_2_: Int, p_render_3_: Float) {
        listener.deleteCache()

        val name = Minecraft.getInstance().player.name.formattedText
        var height = 2
        val start = if (log.size > 20) log.size - 20 - scroll else 0
        for (i in start until log.size - scroll) {
            val text = log[i]
            when (addon.fillKillLogName) {
                Type.DISABLE -> {
                    AbstractGui.fill(width / 3 * 2, height, width - 2, height + 12, minecraft!!.gameSettings.func_216839_a(Int.MIN_VALUE))
                }
                Type.SURROUND -> {
                    if (text.indexOf(name) != -1) {
                        AbstractGui.fill(width / 3 * 2, height, width - 2, height + 1, 1688862720)
                        AbstractGui.fill(width / 3 * 2, height + 11, width - 2, height + 12, 1688862720)
                        AbstractGui.fill(width / 3 * 2, height, width / 3 * 2 + 1, height + 12, 1688862720)
                        AbstractGui.fill(width - 3, height, width - 2, height + 12, 1688862720)

                        AbstractGui.fill(width / 3 * 2 + 1, height + 1, width - 3, height + 11, minecraft!!.gameSettings.func_216839_a(Int.MIN_VALUE))
                    } else {
                        AbstractGui.fill(width / 3 * 2, height, width - 2, height + 12, minecraft!!.gameSettings.func_216839_a(Int.MIN_VALUE))
                    }
                }
                Type.FILL -> {
                    if (text.indexOf(name) != -1) {
                        AbstractGui.fill(width / 3 * 2, height, width - 2, height + 12, 1688862720)
                    } else {
                        AbstractGui.fill(width / 3 * 2, height, width - 2, height + 12, minecraft!!.gameSettings.func_216839_a(Int.MIN_VALUE))
                    }
                }
            }

            font.drawString(text, (width / 3 * 2).toFloat() + 2, height.toFloat() + 2, 16777215)
            height += 12
        }
        super.render(p_render_1_, p_render_2_, p_render_3_)
    }

    override fun mouseScrolled(p_mouseScrolled_1_: Double, p_mouseScrolled_3_: Double, p_mouseScrolled_5_: Double): Boolean {
        scroll += p_mouseScrolled_5_.toInt()
        if (log.size < 21) {
            scroll = 0
            return false
        }

        if (log.size - 20 < scroll) scroll = log.size - 20
        if (scroll < 0) scroll = 0

        return super.mouseScrolled(p_mouseScrolled_1_, p_mouseScrolled_3_, p_mouseScrolled_5_)
    }

    override fun keyPressed(p_keyPressed_1_: Int, p_keyPressed_2_: Int, p_keyPressed_3_: Int): Boolean {
        if (addon.killLogKey.key.keyCode == p_keyPressed_1_) {
            close()
            return true
        }
        if (p_keyPressed_1_ == 256) {
            close()
            return true
        }
        return false
    }

    override fun isPauseScreen(): Boolean {
        return false
    }

    private fun close() {
        minecraft?.displayGuiScreen(null)
    }

    enum class Type(val message: String) {
        DISABLE("無効"),
        SURROUND("囲む"),
        FILL("埋める")
    }
}