package mcpc.tedo0627.kzeaddon.gui

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.listener.HidePlayerListener
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

class SettingGui(val addon: KZEAddon) : Screen(StringTextComponent("setting gui")) {

    override fun init() {
        addButton(Button(width / 5, height / 5, 200, 20, "プレイヤーの非表示${addon.hidePlayer}", fun(button: Button) {
            when (addon.hidePlayer) {
                HidePlayerListener.Type.DISABLE -> {
                    addon.hidePlayer = HidePlayerListener.Type.CLICK
                }
                HidePlayerListener.Type.CLICK -> {
                    addon.hidePlayer = HidePlayerListener.Type.PRESSING
                }
                HidePlayerListener.Type.PRESSING -> {
                    addon.hidePlayer = HidePlayerListener.Type.DISABLE
                }
            }
            button.message = "プレイヤーの非表示${addon.hidePlayer}"
        }))
        addButton(Button(width / 5, height / 5 + 20, 50, 20, "close", fun(button: Button) {
            close()
        }))
    }

    override fun render(p_render_1_: Int, p_render_2_: Int, p_render_3_: Float) {
        renderBackground()
        super.render(p_render_1_, p_render_2_, p_render_3_)
    }

    private fun close() {
        minecraft?.displayGuiScreen(null)
    }
}