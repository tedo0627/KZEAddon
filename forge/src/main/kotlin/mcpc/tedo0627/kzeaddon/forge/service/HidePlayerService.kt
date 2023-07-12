package mcpc.tedo0627.kzeaddon.forge.service

import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import mcpc.tedo0627.kzeaddon.forge.option.HideToggleType
import mcpc.tedo0627.kzeaddon.forge.option.InvisibleType
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.world.scores.Team
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import java.util.*

class HidePlayerService(private val key: KeyMapping) {

    companion object {

        private var executing = false

        /**
         * クリックして 半透明 -> 透明 -> 見える を切り替える時のフラグ
         * 半透明の時 false, 透明の時 true
         * */
        private var toggleInvisible = false

        @JvmStatic
        fun isInvisible(uuid: UUID, team: Team?, invisibleFlag: Boolean): Boolean {
            if (team == null) return invisibleFlag

            val mc = Minecraft.getInstance()
            val player = mc.player ?: return invisibleFlag
            if (player.uuid == uuid) return invisibleFlag
            if (player.team != team) return invisibleFlag

            return executing || invisibleFlag
        }

        @JvmStatic
        fun isOverrideIsInvisibleToFunc(): Boolean {
            if (!executing) return false

            if (AddonOptions.invisibleType.get() == InvisibleType.TOGGLE) {
                if (AddonOptions.hidePlayerToggle.get() != HideToggleType.SWITCH) return false

                return toggleInvisible
            }

            return AddonOptions.invisibleType.get() == InvisibleType.INVISIBLE
        }
    }

    @SubscribeEvent
    fun onKey(event: InputEvent.Key) = input(event.key, event.action)

    @SubscribeEvent
    fun onMouseButton(event: InputEvent.MouseButton) = input(event.button, event.action)

    private fun input(key: Int, action: Int) {
        if (key != this.key.key.value) return

        when (AddonOptions.hidePlayerToggle.get()) {
            HideToggleType.SWITCH -> {
                while (this.key.consumeClick()) {
                    if (AddonOptions.invisibleType.get() == InvisibleType.TOGGLE) {
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
            HideToggleType.LONG_PRESS -> executing = this.key.isDown
            else -> {}
        }
    }
}