package mcpc.tedo0627.kzeaddon.fabric.event

import net.fabricmc.fabric.api.event.Event
import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.world.item.ItemStack

fun interface InventoryUpdateCallback {

    companion object {

        /**
         * インベントリーが更新された時のイベント
         * falseを返すとイベントをキャンセルする
         */
        @JvmField
        val EVENT = EventFactory.createWithPhases(InventoryUpdateCallback::class.java, { listeners ->
            InventoryUpdateCallback { slot, itemStack ->
                for (listener in listeners) {
                    if (!listener.callback(slot, itemStack)) return@InventoryUpdateCallback false
                }

                return@InventoryUpdateCallback true
            }
        }, Event.DEFAULT_PHASE)
    }

    fun callback(slot: Int, itemStack: ItemStack): Boolean
}