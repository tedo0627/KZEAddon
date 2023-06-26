package mcpc.tedo0627.kzeaddon.fabric

import mcpc.tedo0627.kzeaddon.fabric.option.OptionConfig
import mcpc.tedo0627.kzeaddon.fabric.service.*
import net.fabricmc.api.ClientModInitializer

class KZEAddon : ClientModInitializer {

    override fun onInitializeClient() {
        OptionConfig // load config

        ChatFilterService()
        DisplayBulletService()
        HidePlayerService()
        KillLogService()
        RegisterCommandService()
    }
}