package mcpc.tedo0627.kzeaddon.fabric.screen

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import mcpc.tedo0627.kzeaddon.fabric.option.OptionConfig
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class SettingScreen(private val previous: Screen? = null) : Screen(Component.literal("KZEAddonのオプション")) {

    override fun init() {
        mutableListOf(
            AddonOptions.hidePlayerToggle,
            AddonOptions.hidePlayerOverlay,
            AddonOptions.invisibleType,
            AddonOptions.gamma,
            AddonOptions.displayBullet,
            AddonOptions.displayKillLog,
            AddonOptions.removeChatKillLog,
            AddonOptions.displayGlassTimer
        ).forEach {
            addRenderableWidget(it.createButton(minecraft?.options ?: return, getNextX(), getNextY(), 150))
        }

        addRenderableWidget(Button
            .builder(Component.translatable("kzeaddon.screen.setting.displayLocationButton")) { minecraft?.setScreen(OverlayLocationScreen(this)) }
            .bounds(getNextX(), getNextY(), 150, 20)
            .build()
        )

        addRenderableWidget(Button
            .Builder(CommonComponents.GUI_DONE) { minecraft?.setScreen(previous) }
            .pos(width / 2 - 100, height - 27)
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

    override fun render(matrixStack: PoseStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrixStack)
        drawCenteredString(matrixStack, font, title, width / 2, 5, 0xffffff)
        super.render(matrixStack, mouseX, mouseY, delta)
    }

    override fun onClose() {
        minecraft?.setScreen(previous)
    }

    override fun removed() {
        OptionConfig.save()
    }
}