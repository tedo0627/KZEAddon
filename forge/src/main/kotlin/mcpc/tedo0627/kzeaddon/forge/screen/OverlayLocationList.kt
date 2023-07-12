package mcpc.tedo0627.kzeaddon.forge.screen

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.Minecraft
import net.minecraft.client.OptionInstance
import net.minecraft.client.gui.components.*
import net.minecraft.network.chat.Component

class OverlayLocationList(
    width: Int, height: Int, y0: Int, y1: Int, itemHeight: Int
) : ContainerObjectSelectionList<OverlayLocationList.OverlayLocationEntry>(Minecraft.getInstance(), width, height, y0, y1, itemHeight) {

    init {
        add("currentBullet", AddonOptions.currentBulletOverlayLocationX, AddonOptions.currentBulletOverlayLocationY)
        add("remainingBullet", AddonOptions.remainingBulletOverlayLocationX, AddonOptions.remainingBulletOverlayLocationY)
        add("killLog", AddonOptions.killLogOverlayLocationX, AddonOptions.killLogOverlayLocationY)
        add("glassTimer", AddonOptions.glassTimerOverlayLocationX, AddonOptions.glassTimerOverlayLocationY)
        add("scoreboardTimer", AddonOptions.scoreboardTimerOverlayLocationX, AddonOptions.scoreboardTimerOverlayLocationY)

        setRenderBackground(false)
        setRenderTopAndBottom(false)
    }

    private fun add(translatable: String, optionX: OptionInstance<Int>, optionY: OptionInstance<Int>) {
        addEntry(OverlayLocationEntry(width, translatable, optionX, optionY))
    }

    override fun getRowWidth() = 400

    override fun getScrollbarPosition() = super.getScrollbarPosition() + 32

    class OverlayLocationEntry(
        width: Int, translatable: String,
        optionX: OptionInstance<Int>, optionY: OptionInstance<Int>
    ) : Entry<OverlayLocationEntry>() {

        private val children = mutableListOf<AbstractWidget>()

        init {
            val font = Minecraft.getInstance().font
            val text = Component.translatable("kzeaddon.screen.overlayLocation.$translatable")

            val textWidget = StringWidget(width / 2 - 250 + font.width(text.visualOrderText) / 2, 0, 140, 20, text, font)
            children.add(textWidget)

            val textFieldWidgetX = EditBox(font, width / 2 + 40, 0, 80, 20, text)
            textFieldWidgetX.value = optionX.get().toString()
            textFieldWidgetX.setResponder {
                val value = it.toIntOrNull()
                textFieldWidgetX.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
                if (value != null) optionX.set(value)
            }
            val toolTipX = Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipX"))
            textFieldWidgetX.setTooltip(toolTipX)
            children.add(textFieldWidgetX)

            val textFieldWidgetY = EditBox(font, width / 2 + 130, 0, 80, 20, text)
            textFieldWidgetY.value = optionY.get().toString()
            textFieldWidgetY.setResponder {
                val value = it.toIntOrNull()
                textFieldWidgetY.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
                if (value != null) optionY.set(value)
            }
            val toolTipY = Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltipY"))
            textFieldWidgetY.setTooltip(toolTipY)
            children.add(textFieldWidgetY)
        }

        override fun render(
            poseStack: PoseStack, i: Int, j: Int, k: Int, l: Int, m: Int, n: Int, o: Int, bl: Boolean, f: Float
        ) {
            children.forEach { abstractWidget: AbstractWidget ->
                abstractWidget.y = j
                abstractWidget.render(poseStack, n, o, f)
            }
        }

        override fun children() = children

        override fun narratables() = children
    }
}