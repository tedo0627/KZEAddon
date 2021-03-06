package mcpc.tedo0627.kzeaddon.listener

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.extension.PlayerExtension
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.event.RenderPlayerEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class HidePlayerListener(val addon: KZEAddon) : PlayerExtension {

    val key = addon.hidePlayerKey
    val type: Type
        get() = addon.hidePlayer

    var execute = false

    @SubscribeEvent
    fun onRenderPlayer(event: RenderPlayerEvent.Pre) {
        val player = event.player
        if (player.confirmIdentity()) return

        val world = player.world ?: return
        val team = world.scoreboard.getPlayersTeam(player.name.unformattedComponentText) ?: return
        val myTeam = world.scoreboard.getPlayersTeam(Minecraft.getInstance().player.name.unformattedComponentText) ?: return
        if (team.name != myTeam.name) return

        player.isInvisible = execute
    }

    @SubscribeEvent
    fun onKeyInput(event: InputEvent.KeyInputEvent) {
        if (event.key != key.key.keyCode) return
        if (type == Type.CLICK && event.action == 1 && key.isPressed) execute = !execute
    }

    @SubscribeEvent
    fun onMounse(event: InputEvent.MouseInputEvent) {
        if (event.button != key.key.keyCode) return
        if (type == Type.CLICK && event.action == 1 && key.isPressed) execute = !execute
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) return
        if (type != Type.PRESSING) return
        execute = key.isKeyDown
    }

    enum class Type(val message: String) {
        DISABLE("無効"), //無効
        CLICK("クリックで有効無効を切り替える"), //クリックしてon/off
        PRESSING("押している間有効") //押してる間
    }
}