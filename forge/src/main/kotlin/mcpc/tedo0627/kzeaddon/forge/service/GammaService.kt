package mcpc.tedo0627.kzeaddon.forge.service

import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.KeyMapping
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class GammaService(private val key: KeyMapping) {

    @SubscribeEvent
    fun onKey(event: InputEvent.Key) = input(event.key)

    @SubscribeEvent
    fun onMouseButton(event: InputEvent.MouseButton) = input(event.button)

    private fun input(key: Int) {
        if (key != this.key.key.value) return


        while (this.key.consumeClick()) {
            AddonOptions.gamma.set(!AddonOptions.gamma.get())
        }
    }
}