package mcpc.tedo0627.kzeaddon.forge.screen.setting

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class OverlayLocationScreen(private val previous: Screen? = null) : Screen(Component.literal("座標の変更")) {

    override fun init() {
        addRenderableWidget(OverlayLocationList(width, height))

        addRenderableWidget(Button
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