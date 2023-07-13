package mcpc.tedo0627.kzeaddon.fabric.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.network.chat.Component

fun interface TitleUpdateCallback {

    companion object {

        @JvmField
        val TITLE = EventFactory.createWithPhases(TitleUpdateCallback::class.java, { listeners ->
            TitleUpdateCallback { component ->
                for (listener in listeners) listener.callback(component)
            }
        }, Event.DEFAULT_PHASE)

        @JvmField
        val SUBTITLE = EventFactory.createWithPhases(TitleUpdateCallback::class.java, { listeners ->
            TitleUpdateCallback { component ->
                for (listener in listeners) listener.callback(component)
            }
        }, Event.DEFAULT_PHASE)
    }

    fun callback(component: Component)
}