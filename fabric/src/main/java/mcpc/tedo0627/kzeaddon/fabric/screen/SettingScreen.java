package mcpc.tedo0627.kzeaddon.fabric.screen;

import mcpc.tedo0627.kzeaddon.fabric.CustomConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class SettingScreen extends Screen {

    private final CustomConfig config;

    public SettingScreen(CustomConfig config) {
        super(Text.of("message setting screen"));
        this.config = config;
    }

    @Override
    public void init() {
        addDrawableChild(new ButtonWidget(width / 10 + 120, height / 5, 150, 20, Text.of("メッセージの設定"), (button) -> {
            MinecraftClient mc = client;
            if (mc != null) mc.setScreen(new MessageSettingScreen(config, this));
        }));
        addDrawableChild(new ButtonWidget(width / 2 - 20, height / 5 + 200, 40, 20, Text.of("閉じる"), (button) -> {
            onClose();
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
