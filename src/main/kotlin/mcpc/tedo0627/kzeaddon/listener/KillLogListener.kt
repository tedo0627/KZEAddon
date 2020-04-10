package mcpc.tedo0627.kzeaddon.listener

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.gui.KillLogGui
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class KillLogListener(val addon: KZEAddon) {

    private val maxLogSize = 200

    val key: KeyBinding
        get() = addon.killLogKey

    private val log = mutableListOf<String>()
    private val uuidCache = mutableListOf<String>()

    @SubscribeEvent
    fun onKeyInput(event: InputEvent.KeyInputEvent) {
        if (event.action != 1) return
        if (!key.isPressed) return
        Minecraft.getInstance().displayGuiScreen(KillLogGui(addon, this, log))
    }

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent.BossInfo) {
        val bossInfo = event.bossInfo
        val uuid = bossInfo.uniqueId.toString()
        if (uuidCache.contains(uuid)) return

        uuidCache.add(uuid)

        val name = Minecraft.getInstance().player.name.formattedText
        var text = bossInfo.name.formattedText.trim()
        val index = text.indexOf(name)
        if (index != -1) text = "${text.substring(0, index)}§c${name}§f${text.substring(index + name.length, text.length)}"
        log.add(text)
    }

    fun deleteCache() {
        while (log.size > maxLogSize) {
            log.removeAt(0)
            uuidCache.removeAt(0)
        }
    }
}