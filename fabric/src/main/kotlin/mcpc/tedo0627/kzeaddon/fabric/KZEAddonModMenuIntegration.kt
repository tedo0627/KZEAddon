package mcpc.tedo0627.kzeaddon.fabric

import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import mcpc.tedo0627.kzeaddon.fabric.screen.SettingScreen

class KZEAddonModMenuIntegration : ModMenuApi {

    override fun getModConfigScreenFactory(): ConfigScreenFactory<*> {
        return ConfigScreenFactory(::SettingScreen)
    }
}