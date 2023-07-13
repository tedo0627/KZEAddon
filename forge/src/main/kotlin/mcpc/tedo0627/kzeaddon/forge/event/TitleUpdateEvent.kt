package mcpc.tedo0627.kzeaddon.forge.event

import net.minecraft.network.chat.Component
import net.minecraftforge.eventbus.api.Event

class TitleUpdateEvent(val component: Component) : Event()

class SubtitleUpdateEvent(val component: Component) : Event()