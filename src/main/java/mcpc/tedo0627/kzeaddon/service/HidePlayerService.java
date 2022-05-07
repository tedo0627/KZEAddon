package mcpc.tedo0627.kzeaddon.service;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HidePlayerService {

    private static boolean executing = false;

    public static boolean isExecuting() {
        return executing;
    }

    public static boolean isInvisible(Team team2, boolean bool) {
        if (team2 == null) return bool;

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return bool;

        Team team1 = player.getTeam();
        if (team1 == null) return bool;

        if (!team1.getName().equals(team2.getName())) return bool;
        return executing || bool;
    }

    private KeyMapping key;

    public HidePlayerService(KeyMapping key) {
        this.key = key;
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
