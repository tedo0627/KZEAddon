package mcpc.tedo0627.kzeaddon.fabric.screen;

import mcpc.tedo0627.kzeaddon.fabric.CustomConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class MessageSettingScreen extends Screen {

    private final CustomConfig config;
    private final Screen old;

    public MessageSettingScreen(CustomConfig config, Screen old) {
        super(Text.of("message setting screen"));
        this.config = config;
        this.old = old;
    }

    @Override
    public void init() {
        addDrawableChild(new ButtonWidget(width / 10 + 120, height / 5, 150, 20, getButtonText("参加メッセージを消す", config.isDeleteJoinMessage()), (button) -> {
            config.setDeleteJoinMessage(!config.isDeleteJoinMessage());
            button.setMessage(getButtonText("参加メッセージを消す", config.isDeleteJoinMessage()));
        }));
        addDrawableChild(new ButtonWidget(width / 10 + 120, height / 5 + 30, 150, 20, getButtonText("退出メッセージを消す", config.isDeleteQuitMessage()), (button) -> {
            config.setDeleteQuitMessage(!config.isDeleteQuitMessage());
            button.setMessage(getButtonText("退出メッセージを消す", config.isDeleteQuitMessage()));
        }));
        addDrawableChild(new ButtonWidget(width / 2 - 20, height / 5 + 200, 40, 20, Text.of("閉じる"), (button) -> {
            onClose();
        }));
    }

    private Text getButtonText(String text, boolean bool) {
        return Text.of(text + ": " + (bool ? "オン" : "オフ"));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        config.save();
        MinecraftClient mc = client;
        if (mc != null) mc.setScreen(old);
    }
}
