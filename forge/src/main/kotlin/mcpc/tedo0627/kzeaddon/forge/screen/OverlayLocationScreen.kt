package mcpc.tedo0627.kzeaddon.forge.screen

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.OptionInstance
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.components.StringWidget
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class OverlayLocationScreen(private val previous: Screen? = null) : Screen(Component.literal("座標の変更")) {

    override fun init() {
        add("kzeaddon.screen.overlayLocation.currentBulletX", Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipX")), AddonOptions.currentBulletOverlayLocationX)
        add("kzeaddon.screen.overlayLocation.currentBulletY", Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipY")), AddonOptions.currentBulletOverlayLocationY)

        add("kzeaddon.screen.overlayLocation.remainingBulletX", Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipX")), AddonOptions.remainingBulletOverlayLocationX)
        add("kzeaddon.screen.overlayLocation.remainingBulletY", Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipY")), AddonOptions.remainingBulletOverlayLocationY)

        add("kzeaddon.screen.overlayLocation.killLogX", Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipX")), AddonOptions.killLogOverlayLocationX)
        add("kzeaddon.screen.overlayLocation.killLogY", Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipY")), AddonOptions.killLogOverlayLocationY)

        add("kzeaddon.screen.overlayLocation.glassTimerX", Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipX")), AddonOptions.glassTimerOverlayLocationX)
        add("kzeaddon.screen.overlayLocation.glassTimerY", Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipY")), AddonOptions.glassTimerOverlayLocationY)

        addRenderableWidget(Button
            .builder(CommonComponents.GUI_DONE) { minecraft?.setScreen(previous) }
            .pos(width / 2 - 100, height - 27)
            .size(200, 20)
            .build()
        )
    }

    private fun add(translatable: String, toolTip: Tooltip, option: OptionInstance<Int>) {
        val size = children().size / 2
        val y = height / 6 - 12 + size * 30

        val textRenderer = minecraft?.font ?: return
        val text = Component.translatable(translatable)

        val textWidget = StringWidget(width / 2 - 250 + textRenderer.width(text.visualOrderText) / 2, y, 140, 20, text, textRenderer)
        textWidget.setTooltip(toolTip)
        addRenderableWidget(textWidget)

        val textFieldWidget = EditBox(textRenderer, width / 2 + 40, y, 80, 20, text)
        textFieldWidget.value = option.get().toString()
        textFieldWidget.setResponder {
            val value = it.toIntOrNull()
            textFieldWidget.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
            if (value != null) option.set(value)
        }
        textFieldWidget.setTooltip(toolTip)
        addRenderableWidget(textFieldWidget)
    }

    override fun render(matrixStack: PoseStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrixStack)
        drawCenteredString(matrixStack, font, title, width / 2, 5, 0xffffff)
        super.render(matrixStack, mouseX, mouseY, delta)
    }

    override fun onClose() {
        minecraft?.setScreen(previous)
    }
}