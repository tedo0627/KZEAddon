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
        Minecraft mc = minecraft;
        if (mc == null) return;

        addRenderableWidget(new Button(width / 10 + 120, height / 5, 150, 20, Component.nullToEmpty("メッセージの設定"), (button) -> {
            mc.setScreen(new MessageSettingScreen(config, this));
        }));
        addRenderableWidget(new Button(width / 10 + 120, height / 5 + 30, 150, 20, getButtonText("リソパの再読み込みの無効化", config.isDisableResourcePackReload()), (button) -> {
            config.setDisableResourcePackReload(!config.isDisableResourcePackReload());
            button.setMessage(getButtonText("リソパの再読み込みの無効化", config.isDisableResourcePackReload()));
        }));
        addRenderableWidget(new Button(width / 10 + 120, height / 5 + 60, 150, 20, getButtonText("明るさを最大にする", mc.options.gamma >= 10000), (button) -> {
            mc.options.gamma = mc.options.gamma >= 10000 ? 1.0 : 10000;
            mc.options.save();
            button.setMessage(getButtonText("明るさを最大にする", mc.options.gamma >= 10000));
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
        super.onClose();
        config.save();
    }
}
