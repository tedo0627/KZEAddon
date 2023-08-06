package mcpc.tedo0627.kzeaddon.forge.service

import com.mojang.brigadier.Command
import mcpc.tedo0627.kzeaddon.forge.screen.setting.SettingScreen
import net.minecraft.client.Minecraft
import net.minecraft.commands.Commands
import net.minecraftforge.client.event.RegisterClientCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class RegisterCommandService(
    private val battleRecord: BattleRecordService,
    private val killLog: KillLogService
) {

    @SubscribeEvent
    fun onRegisterClientCommands(event: RegisterClientCommandsEvent) {
        event.dispatcher.register(Commands.literal("kzeaddon")
            .executes {
                Minecraft.getInstance().setScreen(SettingScreen())
                Command.SINGLE_SUCCESS
            }
            .then(Commands.literal("history")
                .executes {
                    battleRecord.openBattleRecordScreen()
                    Command.SINGLE_SUCCESS
                }
            )
            .then(Commands.literal("killlog")
                .executes {
                    killLog.openKillLogScreen()
                    Command.SINGLE_SUCCESS
                }
            )
        )
    }
}