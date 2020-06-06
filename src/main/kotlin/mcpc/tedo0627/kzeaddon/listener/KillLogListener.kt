package mcpc.tedo0627.kzeaddon.listener

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.event.CreateBossBarEvent
import mcpc.tedo0627.kzeaddon.gui.KillLogGui
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class KillLogListener(val addon: KZEAddon) {

    private val maxLogSize = 200

    val key: KeyBinding
        get() = addon.killLogKey

    private val log = mutableListOf<String>()

    @SubscribeEvent
    fun onKeyInput(event: InputEvent.KeyInputEvent) {
        if (event.action != 1) return
        if (!key.isPressed) return
        Minecraft.getInstance().displayGuiScreen(KillLogGui(addon, this, log))
    }

    @SubscribeEvent
    fun onCreateBossBar(event: CreateBossBarEvent) {
        val bossInfo = event.bossInfo
        log.add(bossInfo.name.formattedText.trim())
    }

    fun deleteCache() {
        while (log.size > maxLogSize) {
            log.removeAt(0)
        }
    }
}