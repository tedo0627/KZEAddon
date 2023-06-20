package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import mcpc.tedo0627.kzeaddon.fabric.option.HideToggleType
import mcpc.tedo0627.kzeaddon.fabric.option.InvisibleType
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.scoreboard.AbstractTeam
import java.util.*

class HidePlayerService {

    companion object {

        private var executing = false

        /**
         * クリックして 半透明 -> 透明 -> 見える を切り替える時のフラグ
         * 半透明の時 false, 透明の時 true
         * */
        private var toggleInvisible = false

        @JvmStatic
        fun isInvisible(uuid: UUID, team: AbstractTeam?, invisibleFlag: Boolean): Boolean {
            if (team == null) return invisibleFlag

            val mc = MinecraftClient.getInstance()
            val player = mc.player ?: return invisibleFlag
            if (player.uuid == uuid) return invisibleFlag
            if (player.scoreboardTeam != team) return invisibleFlag

            return executing || invisibleFlag
        }

        @JvmStatic
        fun isOverrideIsInvisibleToFunc(): Boolean {
            if (!executing) return false

            if (AddonOptions.invisibleType.value == InvisibleType.TOGGLE) {
                if (AddonOptions.hidePlayerToggle.value != HideToggleType.SWITCH) return false

                return toggleInvisible
            }

            return AddonOptions.invisibleType.value == InvisibleType.INVISIBLE
        }
    }

    private val key = KeyBinding("kzeaddon.key.hidePlayer", -1, "KZEAddon")

    init {
        KeyBindingHelper.registerKeyBinding(key)

        ClientTickEvents.END_CLIENT_TICK.register {
            when (AddonOptions.hidePlayerToggle.value) {
                HideToggleType.SWITCH -> {
                    while (key.wasPressed()) {
                        if (AddonOptions.invisibleType.value == InvisibleType.TOGGLE) {
                            if (executing && !toggleInvisible) { // 半透明の時
                                toggleInvisible = true
                            } else if (executing && toggleInvisible) { // 透明の時
                                executing = false
                                toggleInvisible = false
                            } else { // 見える時
                                executing = true
                            }
                        } else {
                            executing = !executing
                        }
                    }
                }
                HideToggleType.LONG_PRESS -> executing = key.isPressed
                else -> throw IllegalStateException()
            }
        }
    }
}