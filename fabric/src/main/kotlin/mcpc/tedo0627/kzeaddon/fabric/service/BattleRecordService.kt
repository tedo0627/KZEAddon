package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.KZEStatus
import mcpc.tedo0627.kzeaddon.fabric.event.ChatReceiveCallback
import mcpc.tedo0627.kzeaddon.fabric.screen.BattleRecordScreen
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class BattleRecordService(status: KZEStatus) {

    companion object {
        const val START = "》―――――――――――――――――――――――――――――――\n》            戦績\n》"
        const val END = "》\n》―――――――――――――――――――――――――――――――"
    }

    private var recording = false

    private val battleRecords = mutableListOf<BattleRecord>()
    private val components = mutableListOf<Component>()

    init {
        ChatReceiveCallback.EVENT.register { component ->
            val str = component.string
            if (str == START) recording = true

            if (recording) components.add(component)

            if (str == END) {
                recording = false
                battleRecords.add(BattleRecord(CommonComponents.joinLines(components), status.currentMap, status.currentMapCreator))
                components.clear()
            }

            return@register true
        }
    }

    fun openBattleRecordScreen() {
        Minecraft.getInstance().setScreen(BattleRecordScreen(battleRecords))
    }

    class BattleRecord(val component: Component, val map: String?, val mapCreator: String?)
}