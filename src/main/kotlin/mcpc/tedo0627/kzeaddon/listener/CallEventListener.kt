package mcpc.tedo0627.kzeaddon.listener

import mcpc.tedo0627.kzeaddon.event.CreateBossBarEvent
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class CallEventListener {

    // call create boss bar event

    private var time = 0

    private val uuidCache = mutableMapOf<String, Int>()

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent.BossInfo) {
        val bossInfo = event.bossInfo
        val uuid = bossInfo.uniqueId.toString()
        if (!uuidCache.containsKey(uuid)) MinecraftForge.EVENT_BUS.post(CreateBossBarEvent(bossInfo))
        uuidCache[uuid] = time
    }

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) return

        time++
        if (time % 1200 != 0) return
        for (uuid in uuidCache.keys.toMutableList()) {
            val t = uuidCache[uuid] ?: continue
            if (time - t < 20) continue

            uuidCache.remove(uuid)
        }
    }
}