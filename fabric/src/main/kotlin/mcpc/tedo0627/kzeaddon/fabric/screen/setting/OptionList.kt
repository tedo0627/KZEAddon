package mcpc.tedo0627.kzeaddon.fabric.screen.setting

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.AbstractWidget
import net.minecraft.client.gui.components.ContainerObjectSelectionList

class OptionList(
    options: List<AbstractWidget>, width: Int, height: Int
) : ContainerObjectSelectionList<OptionList.OptionEntry>(
    Minecraft.getInstance(), width, height, 32, height - 32, 25
) {

    init {
        val queue = mutableListOf<AbstractWidget>()
        options.forEach {
            queue.add(it)
            if (queue.size != 2) return@forEach

            addEntry(OptionEntry(width, queue[0], queue[1]))
            queue.clear()
        }
        if (queue.isNotEmpty()) addEntry(OptionEntry(width, queue[0]))

        setRenderBackground(false)
        setRenderTopAndBottom(false)
    }

    override fun getRowWidth() = 400

    override fun getScrollbarPosition() = super.getScrollbarPosition() + 32

    class OptionEntry(
        width: Int, widget1: AbstractWidget, widget2: AbstractWidget? = null
    ) : Entry<OptionEntry>() {

        private val children = mutableListOf<AbstractWidget>()

        init {
            widget1.x = width / 2 - 200 - 5
            widget1.width = 200
            children.add(widget1)

            if (widget2 != null) {
                widget2.x = width / 2 + 5
                widget2.width = 200
                children.add(widget2)
            }
        }

        override fun render(
            poseStack: PoseStack, i: Int, j: Int, k: Int, l: Int, m: Int, n: Int, o: Int, bl: Boolean, f: Float
        ) {
            children.forEach { abstractWidget ->
                abstractWidget.y = j
                abstractWidget.render(poseStack, n, o, f)
            }
        }

        override fun children() = children

        override fun narratables() = children
    }
}