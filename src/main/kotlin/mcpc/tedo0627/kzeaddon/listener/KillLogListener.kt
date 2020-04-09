package mcpc.tedo0627.kzeaddon.listener

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.gui.KillLogGui
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class KillLogListener(val addon: KZEAddon) {

    val key: KeyBinding
        get() = addon.killLogKey

    private val log = mutableListOf<String>()
    private val uuidCache = mutableListOf<String>()

    @SubscribeEvent
    fun onKeyInput(event: InputEvent.KeyInputEvent) {
        if (event.action != 1) return
        if (!key.isPressed) return
        Minecraft.getInstance().displayGuiScreen(KillLogGui(addon, log))
    }

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent.BossInfo) {
        val bossInfo = event.bossInfo
        val uuid = bossInfo.uniqueId.toString()
        if (uuidCache.contains(uuid)) return

        uuidCache.add(uuid)
        log.add(bossInfo.name.formattedText.trim())
    }
}