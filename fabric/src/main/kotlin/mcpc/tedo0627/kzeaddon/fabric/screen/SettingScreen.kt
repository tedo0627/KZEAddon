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
            AddonOptions.addKillLogWeaponName,
            AddonOptions.disableKillLogWhenPressTab,
            AddonOptions.removeChatKillLog,
            AddonOptions.displayGlassTimer,
            AddonOptions.displayScoreboardTimer
        ).forEach {
            addRenderableWidget(it.createButton(minecraft?.options ?: return, getNextX(), getNextY(), 200))
        }

        addRenderableWidget(Button
            .builder(Component.translatable("kzeaddon.screen.setting.displayLocationButton")) { minecraft?.setScreen(OverlayLocationScreen(this)) }
            .bounds(getNextX(), getNextY(), 200, 20)
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
        return width / 2 + if (size % 2 == 0) - 200 - 5 else 5
    }

    private fun getNextY(): Int {
        val size = children().size
        return height / 6 - 12 + (size / 2 * 24)
    }

    override fun render(matrixStack: PoseStack, i: Int, j: Int, f: Float) {
        renderBackground(matrixStack)
        drawCenteredString(matrixStack, font, title, width / 2, 5, 0xffffff)
        super.render(matrixStack, i, j, f)
    }

    override fun onClose() {
        minecraft?.setScreen(previous)
    }

    override fun removed() {
        OptionConfig.save()
    }

    override fun isPauseScreen() = false
}