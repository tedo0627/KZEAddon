package mcpc.tedo0627.kzeaddon.fabric.screen

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.minecraft.client.OptionInstance
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.components.Tooltip
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class CrosshairScreen(private val previous: Screen? = null) : Screen(Component.literal("クロスヘア")) {

    override fun init() {
        val button = AddonOptions.crosshair.createButton(minecraft?.options ?: return, width / 2 - 100, height / 6 - 12, 200)
        addRenderableWidget(button)

        add(AddonOptions.crosshairColorR, "r", 0)
        add(AddonOptions.crosshairColorG, "g", 1)
        add(AddonOptions.crosshairColorB, "b", 2)
        add(AddonOptions.crosshairColorA, "a", 3)

        addRenderableWidget(
            Button
            .Builder(CommonComponents.GUI_DONE) { minecraft?.setScreen(previous) }
            .pos(width / 2 - 100, height - 27)
            .size(200, 20)
            .build()
        )
    }

    private fun add(option: OptionInstance<Int>, tooltip: String, index: Int) {
        val textFieldWidgetA = EditBox(font, width / 2 - 40 - 10 - 40 - 5 + (index * (40 + 10)), height / 6 - 12 + 24, 40, 20, Component.empty())
        textFieldWidgetA.value = option.get().toString()
        textFieldWidgetA.setResponder {
            val value = it.toIntOrNull()
            textFieldWidgetA.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
            if (value != null) option.set(value)
        }
        textFieldWidgetA.setTooltip(Tooltip.create(Component.translatable("kzeaddon.screen.color.$tooltip")))
        addRenderableWidget(textFieldWidgetA)
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