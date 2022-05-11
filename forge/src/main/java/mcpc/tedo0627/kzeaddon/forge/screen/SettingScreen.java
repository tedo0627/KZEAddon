package mcpc.tedo0627.kzeaddon.forge.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import mcpc.tedo0627.kzeaddon.forge.CustomConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SettingScreen extends Screen {

    private final CustomConfig config;

    public SettingScreen(CustomConfig config) {
        super(Component.nullToEmpty("setting screen"));
        this.config = config;
    }

    @Override
    public void init() {
        addRenderableWidget(new Button(width / 10 + 120, height / 5, 150, 20, Component.nullToEmpty("メッセージの設定"), (button) -> {
            Minecraft mc = minecraft;
            if (mc != null) mc.setScreen(new MessageSettingScreen(config, this));
        }));
        addRenderableWidget(new Button(width / 2 - 20, height / 5 + 200, 40, 20, Component.nullToEmpty("閉じる"), (button) -> {
            onClose();
        }));
    }

    @Override
    public void render(PoseStack poseStack, int int1, int int2, float int3) {
        renderBackground(poseStack);
        super.render(poseStack, int1, int2, int3);
    }
}
