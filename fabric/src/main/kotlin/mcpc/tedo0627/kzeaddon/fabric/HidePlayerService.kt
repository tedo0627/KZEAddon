package mcpc.tedo0627.kzeaddon.fabric

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl
import net.minecraft.client.MinecraftClient
import net.minecraft.client.option.KeyBinding
import net.minecraft.scoreboard.AbstractTeam
import java.util.UUID

class HidePlayerService {

    companion object {
        private var executing = false

        @JvmStatic
        fun isInvisible(uuid: UUID, team: AbstractTeam?, bool: Boolean): Boolean {
            if (team == null) return bool

            val mc = MinecraftClient.getInstance() ?: return bool
            val player = mc.player ?: return bool
            if (player.uuid == uuid) return bool

            if (!team.isEqual(player.scoreboardTeam)) return bool

            return executing || bool
        }
    }

    private val key = KeyBinding("Hide player toggle key", -1, "KZEAddon")

    init {
        KeyBindingRegistryImpl.registerKeyBinding(key)

        ClientTickEvents.END_CLIENT_TICK.register {
            while (key.wasPressed()) {
                executing = !executing
            }
        }
    }
}