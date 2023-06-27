package mcpc.tedo0627.kzeaddon.fabric.screen

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import mcpc.tedo0627.kzeaddon.fabric.option.OptionConfig
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.screen.ScreenTexts
import net.minecraft.text.Text

class SettingScreen(private val previous: Screen? = null) : Screen(Text.literal("KZEAddonのオプション")) {

    override fun init() {
        mutableListOf(
            AddonOptions.hidePlayerToggle,
            AddonOptions.hidePlayerOverlay,
            AddonOptions.invisibleType,
            AddonOptions.gamma,
            AddonOptions.displayBullet,
            AddonOptions.displayKillLog,
            AddonOptions.removeChatKillLog
        ).forEach {
            addDrawableChild(it.createWidget(client?.options, getNextX(), getNextY(), 150))
        }

        addDrawableChild(ButtonWidget
            .builder(Text.translatable("kzeaddon.screen.setting.displayLocationButton")) { client?.setScreen(OverlayLocationScreen(this)) }
            .dimensions(getNextX(), getNextY(), 150, 20)
            .build()
        )

        addDrawableChild(ButtonWidget
            .Builder(ScreenTexts.DONE) { client?.setScreen(previous) }
            .position(width / 2 - 100, height - 27)
            .size(200, 20)
            .build()
        )
    }

    private fun getNextX(): Int {
        val size = children().size
        return if (size % 2 == 0) width / 2 - 155 else width / 2 - 155 + 160
    }

    private fun getNextY(): Int {
        val size = children().size
        return height / 6 - 12 + (size / 2 * 24)
    }

    override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrixStack)
        drawCenteredTextWithShadow(matrixStack, textRenderer, title, width / 2, 5, 0xffffff)
        super.render(matrixStack, mouseX, mouseY, delta)
    }

    override fun close() {
        client?.setScreen(previous)
    }

    override fun removed() {
        OptionConfig.save()
    }
}