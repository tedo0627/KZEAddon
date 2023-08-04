package mcpc.tedo0627.kzeaddon.fabric

import mcpc.tedo0627.kzeaddon.fabric.option.OptionConfig
import mcpc.tedo0627.kzeaddon.fabric.service.*
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.loader.api.FabricLoader

class KZEAddon : ClientModInitializer {

    companion object {
        const val MOD_ID = "kzeaddon"
    }

    override fun onInitializeClient() {
        OptionConfig // load config

        val checkGeckoLib = FabricLoader.getInstance().getModContainer("geckolib").isPresent

        val status = KZEStatus()

        val battleRecord = BattleRecordService(status)
        ChatFilterService()
        CrosshairService()
        DisplayBulletService()
        GammaService()
        GlassTimerService()
        HidePlayerService()
        val killLog = KillLogService()
        KnifeAnimationService(checkGeckoLib)
        RegisterCommandService(battleRecord, killLog)
        ScoreboardTimerService()
    }
}