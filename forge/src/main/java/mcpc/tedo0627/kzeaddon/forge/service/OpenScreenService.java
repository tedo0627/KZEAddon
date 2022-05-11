package mcpc.tedo0627.kzeaddon.forge.service;

import com.mojang.blaze3d.platform.InputConstants;
import mcpc.tedo0627.kzeaddon.forge.CustomConfig;
import mcpc.tedo0627.kzeaddon.forge.screen.SettingScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

public class OpenScreenService {

    private final CustomConfig config;
    private final KeyMapping key = new KeyMapping("Open KZEAddon settings key", InputConstants.Type.KEYSYM, -1, "KZEAddon");

    public OpenScreenService(CustomConfig config) {
        this.config = config;
        ClientRegistry.registerKeyBinding(key);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.getKey() != key.getKey().getValue()) return;
        if (event.getAction() == 1 && key.isDown()) {
            Minecraft.getInstance().setScreen(new SettingScreen(config));
        }
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        if (event.getButton() != key.getKey().getValue()) return;
        if (event.getAction() == 1 && key.isDown()) {
            Minecraft.getInstance().setScreen(new SettingScreen(config));
        }
    }
}
