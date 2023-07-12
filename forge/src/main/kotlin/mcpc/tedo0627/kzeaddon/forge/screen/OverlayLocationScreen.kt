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
        add("currentBulletX", "tooltipX", AddonOptions.currentBulletOverlayLocationX)
        add("currentBulletY", "tooltipY", AddonOptions.currentBulletOverlayLocationY)

        add("remainingBulletX", "tooltipX", AddonOptions.remainingBulletOverlayLocationX)
        add("remainingBulletY", "tooltipY", AddonOptions.remainingBulletOverlayLocationY)

        add("killLogX", "tooltipX", AddonOptions.killLogOverlayLocationX)
        add("killLogY", "tooltipY", AddonOptions.killLogOverlayLocationY)

        add("glassTimerX", "tooltipX", AddonOptions.glassTimerOverlayLocationX)
        add("glassTimerY", "tooltipY", AddonOptions.glassTimerOverlayLocationY)

        addRenderableWidget(Button
            .builder(CommonComponents.GUI_DONE) { minecraft?.setScreen(previous) }
            .pos(width / 2 - 100, height - 27)
            .size(200, 20)
            .build()
        )
    }

    private fun add(translatable: String, toolTipStr: String, option: OptionInstance<Int>) {
        val size = children().size / 2
        val y = height / 6 - 12 + size * 30

        val textRenderer = minecraft?.font ?: return
        val toolTip = Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.$toolTipStr"))
        val text = Component.translatable("kzeaddon.screen.overlayLocation.$translatable")

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

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        renderBackground(poseStack)
        drawCenteredString(poseStack, font, title, width / 2, 5, 0xffffff)
        super.render(poseStack, i, j, f)
    }

    override fun onClose() {
        minecraft?.setScreen(previous)
    }

    override fun isPauseScreen() = false
}