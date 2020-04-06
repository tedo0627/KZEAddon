package mcpc.tedo0627.kzeaddon.extension

import net.minecraft.client.Minecraft
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.text.StringTextComponent

interface PlayerExtension {

    fun PlayerEntity.sendMessage(message: Any) {
        sendMessage(StringTextComponent(message.toString()))
    }

    fun PlayerEntity.isZombie(): Boolean {
        val mc = Minecraft.getInstance()
        val world = mc.world ?: return false
        val scoreboard = world.scoreboard
        val team = scoreboard.getPlayersTeam(name.unformattedComponentText) ?: return false
        return team.name == "z"
    }

    fun PlayerEntity.isHuman(): Boolean {
        val mc = Minecraft.getInstance()
        val world = mc.world ?: return false
        val scoreboard = world.scoreboard
        val team = scoreboard.getPlayersTeam(name.unformattedComponentText) ?: return false
        return team.name == "e"
    }

    fun PlayerEntity.confirmIdentity(): Boolean {
        val mc = Minecraft.getInstance()
        val player = mc.player ?: return false
        return this == player
    }
}