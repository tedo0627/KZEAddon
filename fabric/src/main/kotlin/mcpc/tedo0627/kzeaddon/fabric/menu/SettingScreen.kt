package mcpc.tedo0627.kzeaddon.fabric.menu

import mcpc.tedo0627.kzeaddon.fabric.Options
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawableHelper
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.screen.option.GameOptionsScreen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.OptionListWidget
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.screen.ScreenTexts
import net.minecraft.text.Text

class SettingScreen(private val previous: Screen) : GameOptionsScreen(
    previous,
    MinecraftClient.getInstance().options,
    Text.literal("KZEAddonのオプション")
) {

    private lateinit var widget: OptionListWidget

    override fun init() {
        widget = OptionListWidget(client, width, height, 32, height - 32, 25)
        widget.addAll(Options.toSimpleOptions())
        addSelectableChild(widget)

        addDrawableChild(ButtonWidget.Builder(ScreenTexts.DONE) {
            client?.setScreen(previous)
        }
            .position(width / 2 - 100, height - 27)
            .size(200, 20)
            .build()
        )
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrixStack)
        widget.render(matrixStack, mouseX, mouseY, delta)
        DrawableHelper.drawCenteredTextWithShadow(matrixStack, textRenderer, title, width / 2, 5, 0xffffff)
        super.render(matrixStack, mouseX, mouseY, delta)
    }

    override fun removed() {
        Options.save()
    }
}