package mcpc.tedo0627.kzeaddon.fabric;

import mcpc.tedo0627.kzeaddon.fabric.service.HidePlayerService;
import mcpc.tedo0627.kzeaddon.fabric.service.OpenScreenService;
import net.fabricmc.api.ClientModInitializer;

public class KZEAddon implements ClientModInitializer {

    public static CustomConfig config;

    @Override
    public void onInitializeClient() {
        config = CustomConfig.load();
        new HidePlayerService();
        new OpenScreenService(config);
    }
}
