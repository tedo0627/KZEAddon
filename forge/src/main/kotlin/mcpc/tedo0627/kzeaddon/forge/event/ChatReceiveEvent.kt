package mcpc.tedo0627.kzeaddon.forge.event

import net.minecraft.network.chat.Component
import net.minecraftforge.eventbus.api.Cancelable
import net.minecraftforge.eventbus.api.Event

@Cancelable
class ChatReceiveEvent(val component: Component) : Event()