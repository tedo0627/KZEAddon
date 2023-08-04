package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.KeyMapping

class GammaService {

    private val key = KeyMapping("kzeaddon.key.gamma", -1, "KZEAddon")

    init {
        KeyBindingHelper.registerKeyBinding(key)

        ClientTickEvents.END_CLIENT_TICK.register {
            while (key.consumeClick()) {
                AddonOptions.gamma.set(!AddonOptions.gamma.get())
            }
        }
    }
}