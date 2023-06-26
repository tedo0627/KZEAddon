package mcpc.tedo0627.kzeaddon.fabric.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.text.Text
import net.minecraft.util.Identifier

fun interface ChatReceiveCallback {

    companion object {

        @JvmField
        val FIRST = Identifier("kzeaddon", "chatreceive-first")

        @JvmField
        val SECOND = Identifier("kzeaddon", "chatreceive-second")

        /**
         * チャットを受け取った時のイベント
         * falseを返すとチャットの受け取りをキャンセルする
         */
        @JvmField
        val EVENT = EventFactory.createWithPhases(ChatReceiveCallback::class.java, { listeners ->
            ChatReceiveCallback { text ->
                for (listener in listeners) {
                    if (!listener.callback(text)) return@ChatReceiveCallback false
                }

                return@ChatReceiveCallback true
            }
        }, FIRST, SECOND, Event.DEFAULT_PHASE)
    }

    fun callback(text: Text): Boolean
}
