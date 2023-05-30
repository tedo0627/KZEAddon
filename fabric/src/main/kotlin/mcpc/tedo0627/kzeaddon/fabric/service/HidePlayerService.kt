package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.Options
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

            val myTeam = player.scoreboardTeam ?: return invisibleFlag

            if (myTeam != team) return invisibleFlag

            return executing || invisibleFlag
        }
    }

    private val key = KeyBinding("Hide player toggle key", -1, "KZEAddon")

    init {
        KeyBindingHelper.registerKeyBinding(key)

        ClientTickEvents.END_CLIENT_TICK.register {
            when (Options.hidePlayer.value) {
                ToggleType.CLICK -> {
                    while (key.wasPressed()) {
                        executing = !executing
                    }
                }
                ToggleType.PRESSING -> executing = key.isPressed
                else -> throw IllegalStateException()
            }
        }
    }

    enum class ToggleType {
        CLICK, // キーを押したときに切り替える
        PRESSING // キーを押しているときに有効
    }
}