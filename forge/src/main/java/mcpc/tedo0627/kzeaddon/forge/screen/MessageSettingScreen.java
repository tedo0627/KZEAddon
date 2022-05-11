package mcpc.tedo0627.kzeaddon.forge.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import mcpc.tedo0627.kzeaddon.forge.CustomConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class MessageSettingScreen extends Screen {

    private final CustomConfig config;
    private final Screen old;

    public MessageSettingScreen(CustomConfig config, Screen old) {
        super(Component.nullToEmpty("message setting screen"));
        this.config = config;
        this.old = old;
    }

    @Override
    public void init() {
        addRenderableWidget(new Button(width / 10 + 120, height / 5, 150, 20, getButtonText("参加メッセージを消す", config.isDeleteJoinMessage()), (button) -> {
            config.setDeleteJoinMessage(!config.isDeleteJoinMessage());
            button.setMessage(getButtonText("参加メッセージを消す", config.isDeleteJoinMessage()));
        }));
        addRenderableWidget(new Button(width / 10 + 120, height / 5 + 30, 150, 20, getButtonText("退出メッセージを消す", config.isDeleteQuitMessage()), (button) -> {
            config.setDeleteQuitMessage(!config.isDeleteQuitMessage());
            button.setMessage(getButtonText("退出メッセージを消す", config.isDeleteQuitMessage()));
        }));
        addRenderableWidget(new Button(width / 2 - 20, height / 5 + 200, 40, 20, Component.nullToEmpty("閉じる"), (button) -> {
            onClose();
        }));
    }

    private Component getButtonText(String text, boolean bool) {
        return Component.nullToEmpty(text + ": " + (bool ? "オン" : "オフ"));
    }

    @Override
    public void render(PoseStack poseStack, int int1, int int2, float int3) {
        renderBackground(poseStack);
        super.render(poseStack, int1, int2, int3);
    }

    @Override
    public void onClose() {
        config.save();
        Minecraft mc = minecraft;
        if (mc != null) mc.setScreen(old);
    }
}
