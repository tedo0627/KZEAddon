package mcpc.tedo0627.kzeaddon.fabric.service;

import mcpc.tedo0627.kzeaddon.fabric.CustomConfig;
import mcpc.tedo0627.kzeaddon.fabric.screen.SettingScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class OpenScreenService {

    private final KeyBinding key = new KeyBinding("Open KZEAddon settings key", InputUtil.Type.KEYSYM, -1, "KZEAddon");

    public OpenScreenService(CustomConfig config) {
        KeyBindingRegistryImpl.registerKeyBinding(key);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (key.wasPressed()) {
                MinecraftClient.getInstance().setScreen(new SettingScreen(config));
            }
        });
    }
}
