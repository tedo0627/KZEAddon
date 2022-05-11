package mcpc.tedo0627.kzeaddon.forge.service;

import mcpc.tedo0627.kzeaddon.forge.CustomConfig;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class DeleteMessageService {

    public final CustomConfig config;

    public DeleteMessageService(CustomConfig config) {
        this.config = config;
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        Component component = event.getMessage();
        String str = component.getString();
        if (config.isDeleteJoinMessage() && str.startsWith("》 ") && str.endsWith("が参加しました。")) {
            event.setCanceled(true);
            return;
        }

        if (config.isDeleteQuitMessage() && str.startsWith("》 ") && str.endsWith("が退出しました。")) {
            event.setCanceled(true);
        }
    }
}
