package mcpc.tedo0627.kzeaddon.listener

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.gui.SettingGui
import net.minecraft.client.Minecraft
import net.minecraft.client.settings.KeyBinding
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class OpenSettingGuiListener(val addon: KZEAddon) {

    val key: KeyBinding
        get() = addon.settingOpenKey

    @SubscribeEvent
    fun onKeyInput(event: InputEvent.KeyInputEvent) {
        if (event.action != 1) return
        if (!key.isPressed) return
        Minecraft.getInstance().displayGuiScreen(SettingGui(addon))
    }
}