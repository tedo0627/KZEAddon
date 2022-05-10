package mcpc.tedo0627.kzeaddon.forge.service;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

import java.util.UUID;

public class HidePlayerService {

    private static boolean executing = false;

    public static boolean isInvisible(UUID uuid, Team team, boolean bool) {
        if (team == null) return bool;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return bool;

        if (player.getUUID().equals(uuid)) return bool;

        Team team1 = player.getTeam();
        if (team1 == null) return bool;

        if (!team1.getName().equals(team.getName())) return bool;
        return executing || bool;
    }

    private final KeyMapping key = new KeyMapping("Hide player toggle key", InputConstants.Type.KEYSYM, -1, "KZEAddon");

    public HidePlayerService() {
        ClientRegistry.registerKeyBinding(key);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (event.getKey() != key.getKey().getValue()) return;
        if (event.getAction() == 1 && key.isDown()) executing = !executing;
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseInputEvent event) {
        if (event.getButton() != key.getKey().getValue()) return;
        if (event.getAction() == 1 && key.isDown()) executing = !executing;
    }
}
