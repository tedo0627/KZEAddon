package mcpc.tedo0627.kzeaddon.forge.screen.setting

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import mcpc.tedo0627.kzeaddon.forge.option.OptionConfig
import mcpc.tedo0627.kzeaddon.forge.screen.CrosshairScreen
import net.minecraft.client.Minecraft
import net.minecraft.client.OptionInstance
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class SettingScreen(private val previous: Screen? = null) : Screen(Component.literal("KZEAddonのオプション")) {

    override fun init() {
        addRenderableWidget(OptionList(mutableListOf(
            button("kzeaddon.screen.setting.invisible") { InvisibleSettingScreen(it) },
            optionWidget(AddonOptions.gamma),
            optionWidget(AddonOptions.darknessRemoveGamma),
            optionWidget(AddonOptions.displayBullet),
            button("kzeaddon.screen.setting.killLog") { KillLogSettingScreen(it) },
            optionWidget(AddonOptions.displayGlassTimer),
            optionWidget(AddonOptions.displayScoreboardTimer),
            button("kzeaddon.screen.setting.crosshair") { CrosshairScreen(it) },
            optionWidget(AddonOptions.knifeAnimation),
            button("kzeaddon.screen.setting.displayLocationButton") { OverlayLocationScreen(it) },
        ), width, height))

        addRenderableWidget(Button
            .Builder(CommonComponents.GUI_DONE) { minecraft?.setScreen(previous) }
            .pos(width / 2 - 100, height - 27)
            .size(200, 20)
            .build()
        )
    }

    private fun optionWidget(option: OptionInstance<*>): AbstractWidget {
        val options = Minecraft.getInstance().options ?: throw IllegalStateException()
        return option.createButton(options, 0, 0, 0)
    }

    private fun button(translatable: String, screen: (previous: Screen) -> Screen): Button {
        return Button.Builder(Component.translatable(translatable)) {
            minecraft?.setScreen(screen(this))
        }.build()
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