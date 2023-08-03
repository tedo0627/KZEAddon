package mcpc.tedo0627.kzeaddon.forge.screen

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import mcpc.tedo0627.kzeaddon.forge.option.OptionConfig
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
            AddonOptions.hidePlayerItem,
            AddonOptions.gamma,
            AddonOptions.darknessRemoveGamma,
            AddonOptions.displayBullet,
            AddonOptions.displayKillLog,
            AddonOptions.addKillLogWeaponName,
            AddonOptions.disableKillLogWhenPressTab,
            AddonOptions.killLogHeight,
            AddonOptions.removeChatKillLog,
            AddonOptions.displayGlassTimer,
            AddonOptions.displayScoreboardTimer,
            AddonOptions.knifeAnimation
        ).forEach {
            addRenderableWidget(it.createButton(minecraft?.options ?: return, getNextX(), getNextY(), 200))
        }

        addRenderableWidget(Button
            .builder(Component.translatable("kzeaddon.screen.setting.crosshair")) { minecraft?.setScreen(CrosshairScreen(this)) }
            .bounds(getNextX(), getNextY(), 200, 20)
            .build()
        )

        addRenderableWidget(Button
            .builder(Component.translatable("kzeaddon.screen.setting.displayLocationButton")) { minecraft?.setScreen(OverlayLocationScreen(this)) }
            .bounds(getNextX(), getNextY(), 200, 20)
            .build()
        )

        addRenderableWidget(Button
            .builder(CommonComponents.GUI_DONE) { minecraft?.setScreen(null) }
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

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        renderBackground(poseStack)
        drawCenteredString(poseStack, font, title, width / 2, 5, 0xffffff)
        super.render(poseStack, i, j, f)
    }

    override fun onClose() {
        minecraft?.setScreen(previous)
    }

    override fun removed() {
        OptionConfig.save()
    }

    override fun isPauseScreen() = false
}