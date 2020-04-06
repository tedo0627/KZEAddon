package mcpc.tedo0627.kzeaddon.listener

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.extension.PlayerExtension
import net.minecraft.client.Minecraft
import net.minecraftforge.client.event.ClientChatEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class EventListener(val addon: KZEAddon) : PlayerExtension {

    @SubscribeEvent
    fun onClientChat(event: ClientChatEvent) {
        if (event.message != "test") return

        event.isCanceled = true
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
        val player = Minecraft.getInstance().player ?: return
        player.sendMessage(addon.hidePlayer)
    }
}