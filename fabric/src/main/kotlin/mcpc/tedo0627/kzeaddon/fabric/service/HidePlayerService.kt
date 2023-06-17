package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import mcpc.tedo0627.kzeaddon.fabric.option.HideToggleType
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.scoreboard.AbstractTeam
import java.util.*

class HidePlayerService {

    companion object {

        private var executing = false

        @JvmStatic
        fun isInvisible(uuid: UUID, team: AbstractTeam?, invisibleFlag: Boolean): Boolean {
            if (team == null) return invisibleFlag

            val mc = MinecraftClient.getInstance()
            val player = mc.player ?: return invisibleFlag
            if (player.uuid == uuid) return invisibleFlag
            if (player.scoreboardTeam != team) return invisibleFlag

            return executing || invisibleFlag
        }
    }

    private val key = KeyBinding("kzeaddon.key.hidePlayer", -1, "KZEAddon")

    init {
        KeyBindingHelper.registerKeyBinding(key)

        ClientTickEvents.END_CLIENT_TICK.register {
            when (AddonOptions.hidePlayerToggle.value) {
                HideToggleType.SWITCH -> {
                    while (key.wasPressed()) {
                        executing = !executing
                    }
                }
                HideToggleType.LONG_PRESS -> executing = key.isPressed
                else -> throw IllegalStateException()
            }
        }
    }
}