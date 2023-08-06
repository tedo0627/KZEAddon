package mcpc.tedo0627.kzeaddon.forge.screen.setting

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import mcpc.tedo0627.kzeaddon.forge.option.OverlayTextOption
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.*
import net.minecraft.network.chat.Component

class OverlayLocationList(
    width: Int, height: Int
) : ContainerObjectSelectionList<OverlayLocationList.OverlayLocationEntry>(
    Minecraft.getInstance(), width, height, 32, height - 32, 25 * 3 + 10
) {

    init {
        addEntry(OverlayLocationEntry(width, AddonOptions.currentBulletOverlay))
        addEntry(OverlayLocationEntry(width, AddonOptions.remainingBulletOverlay))
        addEntry(OverlayLocationEntry(width, AddonOptions.killLogOverlay))
        addEntry(OverlayLocationEntry(width, AddonOptions.glassTimerOverlay))
        addEntry(OverlayLocationEntry(width, AddonOptions.scoreboardTimerOverlay))

        setRenderBackground(false)
        setRenderTopAndBottom(false)
    }

    override fun getRowWidth() = 400

    override fun getScrollbarPosition() = super.getScrollbarPosition() + 32

    class OverlayLocationEntry(
        width: Int, option: OverlayTextOption
    ) : Entry<OverlayLocationEntry>() {

        private val children = mutableListOf<AbstractWidget>()

        init {
            val font = Minecraft.getInstance().font
            val text = Component.translatable("kzeaddon.screen.overlayLocation.${option.name}")

            // 内容

            val textWidget = StringWidget(width / 2 - 250 + font.width(text.visualOrderText) / 2, 0, 140, 20, text, font)
            children.add(textWidget)

            // 座標

            val textPosition = Component.translatable("kzeaddon.screen.overlayLocation.location")
            val textWidgetLocation = StringWidget(width / 2 - 250 + font.width(textPosition.visualOrderText) / 2, 0, 140, 20, textPosition, font)
            children.add(textWidgetLocation)

            val textFieldWidgetX = EditBox(font, width / 2 - 100, 0, 40, 20, text)
            textFieldWidgetX.setFilter { it.isEmpty() || it.toIntOrNull() != null }
            textFieldWidgetX.value = option.x.toString()
            textFieldWidgetX.setResponder {
                val value = it.toIntOrNull()
                textFieldWidgetX.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
                if (value != null) option.x = value
            }
            val toolTipX = Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltip.x"))
            textFieldWidgetX.setTooltip(toolTipX)
            children.add(textFieldWidgetX)

            val textFieldWidgetY = EditBox(font, width / 2 - 50, 0, 40, 20, text)
            textFieldWidgetY.value = option.y.toString()
            textFieldWidgetY.setFilter { it.isEmpty() || it.toIntOrNull() != null }
            textFieldWidgetY.setResponder {
                val value = it.toIntOrNull()
                textFieldWidgetY.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
                if (value != null) option.y = value
            }
            val toolTipY = Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltip.y"))
            textFieldWidgetY.setTooltip(toolTipY)
            children.add(textFieldWidgetY)

            // 大きさ

            val textScale = Component.translatable("kzeaddon.screen.overlayLocation.scale")
            val textWidgetScale = StringWidget(width / 2 - 50 + font.width(textScale.visualOrderText) / 2, 0, 140, 20, textScale, font)
            children.add(textWidgetScale)

            val textFieldWidgetScale = EditBox(font, width / 2 + 100, 0, 40, 20, text)
            textFieldWidgetScale.value = option.scale.toString()
            textFieldWidgetScale.setFilter { it.isEmpty() || it.toIntOrNull() != null }
            textFieldWidgetScale.setResponder {
                val value = it.toIntOrNull()
                textFieldWidgetScale.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
                if (value != null) option.scale = value
            }
            val toolTipScale = Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltip.scale"))
            textFieldWidgetScale.setTooltip(toolTipScale)
            children.add(textFieldWidgetScale)

            // 色

            val textColor = Component.translatable("kzeaddon.screen.overlayLocation.color")
            val textWidgetColor = StringWidget(width / 2 - 250 + font.width(textColor.visualOrderText) / 2, 0, 140, 20, textColor, font)
            children.add(textWidgetColor)

            if (option.useColor) {
                val textFieldWidgetR = EditBox(font, width / 2 - 100, 0, 40, 20, Component.empty())
                textFieldWidgetR.value = option.r.toString()
                textFieldWidgetR.setFilter { it.isEmpty() || it.toIntOrNull() != null }
                textFieldWidgetR.setResponder {
                    val value = it.toIntOrNull()
                    textFieldWidgetR.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
                    if (value != null) option.r = value
                }
                textFieldWidgetR.setTooltip(Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltip.r")))
                children.add(textFieldWidgetR)

                val textFieldWidgetG = EditBox(font, width / 2 - 50, 0, 40, 20, Component.empty())
                textFieldWidgetG.value = option.g.toString()
                textFieldWidgetG.setFilter { it.isEmpty() || it.toIntOrNull() != null }
                textFieldWidgetG.setResponder {
                    val value = it.toIntOrNull()
                    textFieldWidgetG.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
                    if (value != null) option.g = value
                }
                textFieldWidgetG.setTooltip(Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltip.g")))
                children.add(textFieldWidgetG)

                val textFieldWidgetB = EditBox(font, width / 2, 0, 40, 20, Component.empty())
                textFieldWidgetB.value = option.b.toString()
                textFieldWidgetB.setFilter { it.isEmpty() || it.toIntOrNull() != null }
                textFieldWidgetB.setResponder {
                    val value = it.toIntOrNull()
                    textFieldWidgetB.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
                    if (value != null) option.b = value
                }
                textFieldWidgetB.setTooltip(Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltip.b")))
                children.add(textFieldWidgetB)

                val textFieldWidgetA = EditBox(font, width / 2 + 50, 0, 40, 20, Component.empty())
                textFieldWidgetA.value = option.a.toString()
                textFieldWidgetA.setFilter { it.isEmpty() || it.toIntOrNull() != null }
                textFieldWidgetA.setResponder {
                    val value = it.toIntOrNull()
                    textFieldWidgetA.setTextColor(if (value != null) 0xE0E0E0 else 0xFF0000)
                    if (value != null) option.a = value
                }
                textFieldWidgetA.setTooltip(Tooltip.create(Component.translatable("kzeaddon.screen.overlayLocation.tooltip.a")))
                children.add(textFieldWidgetA)
            }
        }

        override fun render(
            poseStack: PoseStack, i: Int, j: Int, k: Int, l: Int, m: Int, n: Int, o: Int, bl: Boolean, f: Float
        ) {
            children.forEachIndexed { index, abstractWidget ->
                val y = if (index < 1) 0 else if (index < 6) 1 else 2

                abstractWidget.y = j + y * 25
                abstractWidget.render(poseStack, n, o, f)
            }
        }

        override fun children() = children

        override fun narratables() = children
    }
}