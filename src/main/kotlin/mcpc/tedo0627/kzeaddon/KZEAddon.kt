package mcpc.tedo0627.kzeaddon

import mcpc.tedo0627.kzeaddon.listener.EventListener
import mcpc.tedo0627.kzeaddon.listener.HidePlayerListener
import mcpc.tedo0627.kzeaddon.listener.Scheduler
import mcpc.tedo0627.kzeaddon.listener.OpenSettingGuiListener
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.client.registry.ClientRegistry
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext

@Mod(KZEAddon.MOD_ID)
class KZEAddon {

    companion object {
        const val MOD_ID = "kzeaddon"
    }

    lateinit var scheduler: Scheduler

    var hidePlayer = HidePlayerListener.Type.DISABLE

    val settingOpenKey = KeyBinding("設定画面を開くキー", 79, "KZEAddon")
    val hidePlayerKey = KeyBinding("プレイヤーを透明にするキー", 78, "KZEAddon")

    init {
        FMLJavaModLoadingContext.get().modEventBus.addListener { event: FMLCommonSetupEvent? -> setup(event!!) }
    }

    private fun setup(event: FMLCommonSetupEvent) {
        scheduler = Scheduler()

        MinecraftForge.EVENT_BUS.register(EventListener(this))
        MinecraftForge.EVENT_BUS.register(scheduler)
        MinecraftForge.EVENT_BUS.register(OpenSettingGuiListener(this))
        MinecraftForge.EVENT_BUS.register(HidePlayerListener(this))

        ClientRegistry.registerKeyBinding(settingOpenKey)
        ClientRegistry.registerKeyBinding(hidePlayerKey)
    }
}