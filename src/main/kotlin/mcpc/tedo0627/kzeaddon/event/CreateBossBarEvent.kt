package mcpc.tedo0627.kzeaddon.event

import net.minecraft.client.gui.ClientBossInfo
import net.minecraftforge.eventbus.api.Event

class CreateBossBarEvent(val bossInfo: ClientBossInfo) : Event()