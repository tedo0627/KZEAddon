package mcpc.tedo0627.kzeaddon.forge.service

import com.mojang.blaze3d.platform.InputConstants
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import mcpc.tedo0627.kzeaddon.forge.option.HideToggleType
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.world.scores.Team
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import java.util.*

class HidePlayerService(private val key: KeyMapping) {

    companion object {

        private var executing = false

        @JvmStatic
        fun isInvisible(uuid: UUID, team: Team?, invisibleFlag: Boolean): Boolean {
            if (team == null) return invisibleFlag

            val mc = Minecraft.getInstance()
            val player = mc.player ?: return invisibleFlag
            if (player.uuid == uuid) return invisibleFlag
            if (player.team != team) return invisibleFlag

            return executing || invisibleFlag
        }
    }

    @SubscribeEvent
    fun onKey(event: InputEvent.Key) = input(event.key, event.action)

    @SubscribeEvent
    fun onMouseButton(event: InputEvent.MouseButton) = input(event.button, event.action)

    private fun input(key: Int, action: Int) {
        if (key != this.key.key.value) return

        when (AddonOptions.hidePlayerToggle.get()) {
            HideToggleType.SWITCH -> if (action == InputConstants.PRESS && this.key.isDown) executing = !executing
            HideToggleType.LONG_PRESS -> executing = action == InputConstants.REPEAT || this.key.isDown
            else -> {}
        }
    }
}