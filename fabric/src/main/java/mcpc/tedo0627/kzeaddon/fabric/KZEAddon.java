package mcpc.tedo0627.kzeaddon.fabric;

import net.fabricmc.api.ClientModInitializer;

public class KZEAddon implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        new HidePlayerService();
    }
}
