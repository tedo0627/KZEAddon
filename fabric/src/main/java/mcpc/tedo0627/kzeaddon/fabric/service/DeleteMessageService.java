package mcpc.tedo0627.kzeaddon.fabric.service;

import mcpc.tedo0627.kzeaddon.fabric.KZEAddon;
import net.minecraft.text.Text;

public class DeleteMessageService {

    public static boolean onClientChatReceived(Text text) {
        String str = text.getString();
        System.out.println("chat");
        System.out.println(KZEAddon.config.isDeleteJoinMessage() ? "true" : "false");
        System.out.println(KZEAddon.config.isDeleteQuitMessage() ? "true" : "false");
        if (KZEAddon.config.isDeleteJoinMessage() && str.startsWith("》 ") && str.endsWith("が参加しました。")) return true;
        return KZEAddon.config.isDeleteQuitMessage() && str.startsWith("》 ") && str.endsWith("が退出しました。");
    }
}
