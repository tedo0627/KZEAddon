package mcpc.tedo0627.kzeaddon.forge.service

import com.mojang.brigadier.Command
import com.mojang.brigadier.context.CommandContext
import mcpc.tedo0627.kzeaddon.forge.screen.SettingScreen
import net.minecraft.client.Minecraft
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraftforge.client.event.RegisterClientCommandsEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class RegisterCommandService {

    @SubscribeEvent
    fun onRegisterClientCommands(event: RegisterClientCommandsEvent) {
        val builder = Commands.literal("kzeaddon")
            .executes { _: CommandContext<CommandSourceStack> ->
                Minecraft.getInstance().setScreen(SettingScreen())
                Command.SINGLE_SUCCESS
            }
        event.dispatcher.register(builder)

        Minecraft.getInstance().level
    }
}