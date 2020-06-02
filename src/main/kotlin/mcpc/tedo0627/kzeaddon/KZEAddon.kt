package mcpc.tedo0627.kzeaddon

import mcpc.tedo0627.kzeaddon.listener.*
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.LogManager

@Mod(KZEAddon.MOD_ID)
class KZEAddon {

    companion object {
        const val MOD_ID = "kzeaddon"
    }

    lateinit var scheduler: Scheduler

    val logger = LogManager.getLogger()

    lateinit var config: Config
        private set

    var hidePlayer = HidePlayerListener.Type.CLICK
    var displayBullet = true
    var displayReloadDuration = true
    var fillKillLogName = false

    val settingOpenKey = KeyBinding("設定画面を開くキー", 79, "KZEAddon")
    val hidePlayerKey = KeyBinding("プレイヤーを透明にするキー", 78, "KZEAddon")
    val killLogKey = KeyBinding("キルログを表示するキー", 75, "KZEAddon")

    init {
        FMLJavaModLoadingContext.get().modEventBus.addListener { event: FMLCommonSetupEvent -> setup(event) }
        FMLJavaModLoadingContext.get().modEventBus.addListener { event: FMLLoadCompleteEvent -> complete(event) }
    }

    private fun setup(event: FMLCommonSetupEvent) {
        scheduler = Scheduler()

        MinecraftForge.EVENT_BUS.register(scheduler)
        MinecraftForge.EVENT_BUS.register(DisplayBulletListener(this))
        MinecraftForge.EVENT_BUS.register(OpenSettingGuiListener(this))
        MinecraftForge.EVENT_BUS.register(HidePlayerListener(this))
        MinecraftForge.EVENT_BUS.register(KillLogListener(this))
        MinecraftForge.EVENT_BUS.register(ReloadDurationListener(this))

        ClientRegistry.registerKeyBinding(settingOpenKey)
        ClientRegistry.registerKeyBinding(hidePlayerKey)
        ClientRegistry.registerKeyBinding(killLogKey)
    }

    private fun complete(event: FMLLoadCompleteEvent) {
        config = Config()
        hidePlayer = config.hidePlayer
        displayBullet = config.displayBullet
        fillKillLogName = config.fillKillLogName
    }
}