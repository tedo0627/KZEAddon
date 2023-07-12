package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.event.ChatReceiveCallback
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions

class ChatFilterService {

    private val regex = Regex("""》(FirstBlood! |)(.*) killed by (.*) \(([A-Za-z0-9_-]+) ?\)""")

    init {
        ChatReceiveCallback.EVENT.register(ChatReceiveCallback.SECOND) { text ->
            if (!AddonOptions.removeChatKillLog.get()) return@register true

            return@register !text.string.matches(regex)
        }
    }
}