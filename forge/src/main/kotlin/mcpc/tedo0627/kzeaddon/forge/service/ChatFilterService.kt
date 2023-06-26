package mcpc.tedo0627.kzeaddon.forge.service

import mcpc.tedo0627.kzeaddon.forge.event.ChatReceiveEvent
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraftforge.eventbus.api.SubscribeEvent

class ChatFilterService {

    private val regex = Regex("""》(FirstBlood! |)(.*) killed by (.*) \(([A-Za-z0-9_-]+) ?\)""")

    @SubscribeEvent
    fun onChatReceive(event: ChatReceiveEvent) {
        if (!AddonOptions.removeChatKillLog.get()) return

        if (event.component.string.matches(regex)) event.isCanceled = true
    }
}