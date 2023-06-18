package mcpc.tedo0627.kzeaddon.forge

import mcpc.tedo0627.kzeaddon.forge.option.OptionConfig
import mcpc.tedo0627.kzeaddon.forge.service.DisplayBulletService
import mcpc.tedo0627.kzeaddon.forge.service.HidePlayerService
import mcpc.tedo0627.kzeaddon.forge.service.RegisterCommandService
import net.minecraft.client.KeyMapping
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist

@Mod(KZEAddon.MOD_ID)
object KZEAddon {

    const val MOD_ID = "kzeaddon"

    init {
        OptionConfig // load config
        runForDist({ MOD_BUS.addListener(::onRegisterKeyMappings) }, {})
    }

    private fun onRegisterKeyMappings(event: RegisterKeyMappingsEvent) {
        val key = KeyMapping("kzeaddon.key.hidePlayer", -1, "KZEAddon")
        event.register(key)

        mutableListOf(
            DisplayBulletService(),
            HidePlayerService(key),
            RegisterCommandService()
        ).forEach { MinecraftForge.EVENT_BUS.register(it) }
    }
}