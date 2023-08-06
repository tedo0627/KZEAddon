package mcpc.tedo0627.kzeaddon.forge.screen.setting

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class InvisibleSettingScreen(private val previous: Screen? = null) : Screen(Component.literal("透明の設定")) {

    override fun init() {
        val options = Minecraft.getInstance().options ?: return
        addRenderableWidget(OptionList(mutableListOf(
            AddonOptions.hidePlayerToggle, AddonOptions.hidePlayerOverlay,
            AddonOptions.invisibleType, AddonOptions.hidePlayerItem
        ).map { it.createButton(options, 0, 0, 0) }, width, height))

        addRenderableWidget(
            Button
                .Builder(CommonComponents.GUI_DONE) { minecraft?.setScreen(previous) }
                .pos(width / 2 - 100, height - 27)
                .size(200, 20)
                .build()
        )
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