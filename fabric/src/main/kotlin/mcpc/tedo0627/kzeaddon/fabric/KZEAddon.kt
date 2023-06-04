package mcpc.tedo0627.kzeaddon.fabric

import mcpc.tedo0627.kzeaddon.fabric.option.OptionConfig
import mcpc.tedo0627.kzeaddon.fabric.service.HidePlayerService
import mcpc.tedo0627.kzeaddon.fabric.service.RegisterCommandService
import net.fabricmc.api.ClientModInitializer

class KZEAddon : ClientModInitializer {

    override fun onInitializeClient() {
        OptionConfig // load config
        HidePlayerService()
        RegisterCommandService()
    }
}