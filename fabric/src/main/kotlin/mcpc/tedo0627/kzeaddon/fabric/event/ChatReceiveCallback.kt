package mcpc.tedo0627.kzeaddon.fabric.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

fun interface ChatReceiveCallback {

    companion object {

        @JvmField
        val FIRST = ResourceLocation("kzeaddon", "chatreceive-first")

        @JvmField
        val SECOND = ResourceLocation("kzeaddon", "chatreceive-second")

        /**
         * チャットを受け取った時のイベント
         * falseを返すとチャットの受け取りをキャンセルする
         */
        @JvmField
        val EVENT = EventFactory.createWithPhases(ChatReceiveCallback::class.java, { listeners ->
            ChatReceiveCallback { component ->
                for (listener in listeners) {
                    if (!listener.callback(component)) return@ChatReceiveCallback false
                }

                return@ChatReceiveCallback true
            }
        }, FIRST, SECOND, Event.DEFAULT_PHASE)
    }

    fun callback(component: Component): Boolean
}
