package mcpc.tedo0627.kzeaddon.fabric

import net.fabricmc.api.ClientModInitializer

class KZEAddon : ClientModInitializer {

    override fun onInitializeClient() {
        HidePlayerService()
    }
}