package mcpc.tedo0627.kzeaddon.forge

import mcpc.tedo0627.kzeaddon.forge.option.OptionConfig
import mcpc.tedo0627.kzeaddon.forge.service.*
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
        val hideKey = KeyMapping("kzeaddon.key.hidePlayer", -1, "KZEAddon")
        val killLogKey = KeyMapping("kzeaddon.key.killLog", -1, "KZEAddon")
        event.register(hideKey)
        event.register(killLogKey)

        mutableListOf(
            ChatFilterService(),
            DisplayBulletService(),
            GlassTimerService(),
            HidePlayerService(hideKey),
            KillLogService(killLogKey),
            RegisterCommandService()
        ).forEach { MinecraftForge.EVENT_BUS.register(it) }
    }
}