package mcpc.tedo0627.kzeaddon.forge

import mcpc.tedo0627.kzeaddon.forge.event.SubtitleUpdateEvent
import mcpc.tedo0627.kzeaddon.forge.event.TitleUpdateEvent
import net.minecraft.network.chat.Component
import net.minecraftforge.eventbus.api.SubscribeEvent

class KZEStatus {

    private var latestTitle: Component? = null
    private var latestSubtitle: Component? = null

    var currentMap: String? = null
        private set
    var currentMapCreator: String? = null
        private set

    @SubscribeEvent
    fun onTitleUpdate(event: TitleUpdateEvent) {
        latestTitle = event.component
        updateMapTitle()
    }

    @SubscribeEvent
    fun onSubtitleUpdate(event: SubtitleUpdateEvent) {
        latestSubtitle = event.component
        updateMapTitle()
    }

    private fun updateMapTitle() {
        val title = latestTitle?.string ?: return
        val subtitle = latestSubtitle?.string ?: return
        if (title.startsWith("MAP - ") && subtitle.startsWith("Created by - ")) {
            currentMap = title.removePrefix("MAP - ")
            currentMapCreator = subtitle.removePrefix("Created by - ")
        }
    }
}