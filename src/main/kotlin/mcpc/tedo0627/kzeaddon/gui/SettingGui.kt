package mcpc.tedo0627.kzeaddon.gui

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.listener.HidePlayerListener
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.button.Button
import net.minecraft.util.text.StringTextComponent

class SettingGui(val addon: KZEAddon) : Screen(StringTextComponent("setting gui")) {

    override fun init() {
        addButton(Button(width / 5, height / 5, 250, 20, "プレイヤーの透明化 現在: ${addon.hidePlayer.message}", fun(button: Button) {
            when (addon.hidePlayer) {
                HidePlayerListener.Type.DISABLE -> addon.hidePlayer = HidePlayerListener.Type.CLICK
                HidePlayerListener.Type.CLICK -> addon.hidePlayer = HidePlayerListener.Type.PRESSING
                HidePlayerListener.Type.PRESSING -> addon.hidePlayer = HidePlayerListener.Type.DISABLE
            }
            button.message = "プレイヤーの透明化 現在: ${addon.hidePlayer.message}"
            addon.config.hidePlayer = addon.hidePlayer
            addon.config.save()
        }))
        addButton(Button(width / 5, height / 5 + 20, 250, 20, "弾の残りの数を表示する 現在: ${if (addon.displayBullet) "有効" else "無効"}", fun(button: Button) {
            addon.displayBullet = !addon.displayBullet
            button.message = "弾の残りの数を表示する 現在: ${if (addon.displayBullet) "有効" else "無効"}"
            addon.config.displayBullet = addon.displayBullet
            addon.config.save()
        }))
        addButton(Button(width / 5, height / 5 + 40, 250, 20, "リロードの時間を表示する 現在: ${if (addon.displayReloadDuration) "有効" else "無効"}", fun(button: Button) {
            addon.displayReloadDuration = !addon.displayReloadDuration
            button.message = "リロードの時間を表示する 現在: ${if (addon.displayReloadDuration) "有効" else "無効"}"
            addon.config.displayReloadDuration = addon.displayReloadDuration
            addon.config.save()
        }))
        addButton(Button(width / 5, height / 5 + 60, 250, 20, "キルログの自分の名前の行の背景の設定 現在: ${addon.fillKillLogName.message}", fun(button: Button) {
            when (addon.fillKillLogName) {
                KillLogGui.Type.DISABLE -> addon.fillKillLogName = KillLogGui.Type.SURROUND
                KillLogGui.Type.SURROUND -> addon.fillKillLogName = KillLogGui.Type.FILL
                KillLogGui.Type.FILL -> addon.fillKillLogName = KillLogGui.Type.DISABLE
            }
            button.message = "キルログの自分の名前の行の背景の設定 現在: ${addon.fillKillLogName.message}"
            addon.config.fillKillLogName = addon.fillKillLogName
            addon.config.save()
        }))
        addButton(Button(width / 5, height / 5 + 80, 50, 20, "閉じる", fun(button: Button) {
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