package mcpc.tedo0627.kzeaddon.forge.screen

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import mcpc.tedo0627.kzeaddon.forge.option.OptionConfig
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.OptionsList
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class SettingScreen : Screen(Component.literal("KZEAddonのオプション")) {

    private lateinit var widget: OptionsList

    override fun init() {
        widget = OptionsList(minecraft ?: return, width, height, 32, height - 32, 25)
        widget.addSmall(AddonOptions.getOptions())
        addWidget(widget)

        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE) {
            minecraft?.setScreen(null)
        }
            .pos(width / 2 - 100, height - 27)
            .size(200, 20)
            .build())
    }

    override fun render(matrixStack: PoseStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrixStack)
        widget.render(matrixStack, mouseX, mouseY, delta)
        drawCenteredString(matrixStack, font, title, width / 2, 5, 0xffffff)
        super.render(matrixStack, mouseX, mouseY, delta)
    }

    override fun removed() {
        OptionConfig.save()
    }
}