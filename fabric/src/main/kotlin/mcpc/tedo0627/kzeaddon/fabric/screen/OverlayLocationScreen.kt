package mcpc.tedo0627.kzeaddon.fabric.screen

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.tooltip.Tooltip
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.client.gui.widget.TextWidget
import net.minecraft.client.option.SimpleOption
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.screen.ScreenTexts
import net.minecraft.text.Text

class OverlayLocationScreen(private val previous: Screen? = null) : Screen(Text.literal("座標の変更")) {

    override fun init() {
        add("kzeaddon.screen.overlayLocation.currentBulletX", Tooltip.of(Text.translatable("kzeaddon.screen.overlayLocation.tooltip")), AddonOptions.currentBulletOverlayLocationX)
        add("kzeaddon.screen.overlayLocation.currentBulletY", Tooltip.of(Text.translatable("kzeaddon.screen.overlayLocation.tooltip")), AddonOptions.currentBulletOverlayLocationY)

        add("kzeaddon.screen.overlayLocation.remainingBulletX", Tooltip.of(Text.translatable("kzeaddon.screen.overlayLocation.tooltip")), AddonOptions.remainingBulletOverlayLocationX)
        add("kzeaddon.screen.overlayLocation.remainingBulletY", Tooltip.of(Text.translatable("kzeaddon.screen.overlayLocation.tooltip")), AddonOptions.remainingBulletOverlayLocationY)

        add("kzeaddon.screen.overlayLocation.killLogX", Tooltip.of(Text.translatable("kzeaddon.screen.overlayLocation.tooltip")), AddonOptions.killLogOverlayLocationX)
        add("kzeaddon.screen.overlayLocation.killLogY", Tooltip.of(Text.translatable("kzeaddon.screen.overlayLocation.tooltip")), AddonOptions.killLogOverlayLocationY)

        addDrawableChild(ButtonWidget
            .Builder(ScreenTexts.DONE) { client?.setScreen(previous) }
            .position(width / 2 - 100, height - 27)
            .size(200, 20)
            .build()
        )
    }

    private fun add(translatable: String, toolTip: Tooltip, option: SimpleOption<Int>) {
        val size = children().size / 2
        val y = height / 6 - 12 + size * 30

        val textRenderer = client?.textRenderer ?: return
        val text = Text.translatable(translatable)

        val textWidget = TextWidget(width / 2 - 250 + textRenderer.getWidth(text.asOrderedText()) / 2, y, 140, 20, text, textRenderer)
        textWidget.setTooltip(toolTip)
        addDrawableChild(textWidget)

        val textFieldWidget = TextFieldWidget(textRenderer, width / 2 + 40, y, 80, 20, text)
        textFieldWidget.text = option.value.toString()
        textFieldWidget.setChangedListener {
            val value = it.toIntOrNull()
            textFieldWidget.setEditableColor(if (value != null) 0xE0E0E0 else 0xFF0000)
            if (value != null) option.value = value
        }
        textFieldWidget.setTooltip(toolTip)
        addDrawableChild(textFieldWidget)
    }

    override fun render(matrixStack: MatrixStack, i: Int, j: Int, f: Float) {
        renderBackground(matrixStack)
        drawCenteredTextWithShadow(matrixStack, textRenderer, title, width / 2, 5, 0xffffff)
        super.render(matrixStack, i, j, f)
    }

    override fun close() {
        client?.setScreen(previous)
    }
}