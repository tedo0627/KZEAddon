package mcpc.tedo0627.kzeaddon.forge.service

import mcpc.tedo0627.kzeaddon.forge.KZEStatus
import mcpc.tedo0627.kzeaddon.forge.event.ChatReceiveEvent
import mcpc.tedo0627.kzeaddon.forge.screen.BattleRecordScreen
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraftforge.eventbus.api.SubscribeEvent

class BattleRecordService(private val status: KZEStatus) {

    companion object {
        const val START = "》―――――――――――――――――――――――――――――――\n》            戦績\n》"
        const val END = "》\n》―――――――――――――――――――――――――――――――"
    }

    private var recording = false

    private val battleRecords = mutableListOf<BattleRecord>()
    private val components = mutableListOf<Component>()

    @SubscribeEvent
    fun onChatReceive(event: ChatReceiveEvent) {
        val component = event.component

        val str = component.string
        if (str == START) recording = true

        if (recording) components.add(component)

        if (str == END) {
            recording = false
            battleRecords.add(BattleRecord(CommonComponents.joinLines(components), status.currentMap, status.currentMapCreator))
            components.clear()
        }
    }

    fun openBattleRecordScreen() {
        Minecraft.getInstance().setScreen(BattleRecordScreen(battleRecords))
    }

    class BattleRecord(val component: Component, val map: String?, val mapCreator: String?)
}