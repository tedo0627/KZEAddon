package mcpc.tedo0627.kzeaddon.fabric.service

import com.mojang.brigadier.Command
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import mcpc.tedo0627.kzeaddon.fabric.screen.SettingScreen
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.server.command.ServerCommandSource

class RegisterCommandService {

    init {
        CommandRegistrationCallback.EVENT.register { dispatcher, _, _ ->
            dispatcher.register(LiteralArgumentBuilder.literal<ServerCommandSource?>("kzeaddon")
                .executes {
                    MinecraftClient.getInstance().setScreen(SettingScreen())
                    Command.SINGLE_SUCCESS
                }
            )
        }
    }
}