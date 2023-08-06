package mcpc.tedo0627.kzeaddon.fabric.screen.setting

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component

class KillLogSettingScreen(private val previous: Screen? = null) : Screen(Component.literal("キルログの設定")) {

    override fun init() {
        val options = Minecraft.getInstance().options ?: return
        addRenderableWidget(OptionList(mutableListOf(
            AddonOptions.displayKillLog, AddonOptions.addKillLogWeaponName,
            AddonOptions.disableKillLogWhenPressTab, AddonOptions.killLogHeight,
            AddonOptions.removeChatKillLog
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