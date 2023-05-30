package mcpc.tedo0627.kzeaddon.fabric

import mcpc.tedo0627.kzeaddon.fabric.service.HidePlayerService
import net.fabricmc.api.ClientModInitializer

class KZEAddon : ClientModInitializer {

    override fun onInitializeClient() {
        HidePlayerService()
    }
}