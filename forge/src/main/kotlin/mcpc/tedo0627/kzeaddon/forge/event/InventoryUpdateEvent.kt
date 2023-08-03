package mcpc.tedo0627.kzeaddon.forge.event

import net.minecraft.world.item.ItemStack
import net.minecraftforge.eventbus.api.Cancelable
import net.minecraftforge.eventbus.api.Event

@Cancelable
class InventoryUpdateEvent(val slot: Int, val itemStack: ItemStack) : Event()