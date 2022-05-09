package mcpc.tedo0627.kzeaddon.fabric;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.impl.client.keybinding.KeyBindingRegistryImpl;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.scoreboard.AbstractTeam;

import java.util.UUID;

public class HidePlayerService {

    private static boolean executing = false;

    public static boolean isInvisible(UUID uuid, AbstractTeam team, boolean bool) {
        if (team == null) return bool;

        MinecraftClient mc = MinecraftClient.getInstance();
        ClientPlayerEntity player = mc.player;
        if (player == null) return bool;

        if (player.getUuid().equals(uuid)) return bool;

        AbstractTeam team1 = player.getScoreboardTeam();
        if (team1 == null) return bool;

        if (!team1.getName().equals(team.getName())) return bool;
        return executing || bool;
    }

    private final KeyBinding key = new KeyBinding("Hide player toggle key", -1, "KZEAddon");

    public HidePlayerService() {
        KeyBindingRegistryImpl.registerKeyBinding(key);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (key.wasPressed()) {
                executing = !executing;
            }
        });
    }
}
