package mcpc.tedo0627.kzeaddon.fabric

import mcpc.tedo0627.kzeaddon.fabric.event.TitleUpdateCallback
import net.minecraft.network.chat.Component

class KZEStatus {

    private var latestTitle: Component? = null
    private var latestSubtitle: Component? = null

    var currentMap: String? = null
        private set
    var currentMapCreator: String? = null
        private set

    init {
        TitleUpdateCallback.TITLE.register {
            latestTitle = it
            updateMapTitle()
        }
        TitleUpdateCallback.SUBTITLE.register {
            latestSubtitle = it
            updateMapTitle()
        }
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