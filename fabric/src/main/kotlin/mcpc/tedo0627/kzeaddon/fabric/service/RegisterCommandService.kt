package mcpc.tedo0627.kzeaddon.fabric.service

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import mcpc.tedo0627.kzeaddon.fabric.screen.setting.SettingScreen
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.minecraft.client.Minecraft

class RegisterCommandService(
    battleRecord: BattleRecordService,
    killLog: KillLogService
) {

    private var enable = false
    private var history = false
    private var log = false

    init {
        ClientCommandRegistrationCallback.EVENT.register { dispatcher, _ ->
            dispatcher.register(LiteralArgumentBuilder.literal<FabricClientCommandSource>("kzeaddon")
                .executes {
                    enable = true
                    Command.SINGLE_SUCCESS
                }
                .then(LiteralArgumentBuilder.literal<FabricClientCommandSource?>("history")
                    .executes {
                        history = true
                        Command.SINGLE_SUCCESS
                    }
                )
                .then(LiteralArgumentBuilder.literal<FabricClientCommandSource>("killlog")
                    .executes {
                        log = true
                        Command.SINGLE_SUCCESS
                    }
                )
            )
        }

        ClientTickEvents.END_CLIENT_TICK.register {
            if (enable) {
                Minecraft.getInstance().setScreen(SettingScreen())
                enable = false
            }
            if (history) {
                battleRecord.openBattleRecordScreen()
                history = false
            }
            if (log) {
                killLog.openKillLogScreen()
                log = false
            }
        }
    }
}