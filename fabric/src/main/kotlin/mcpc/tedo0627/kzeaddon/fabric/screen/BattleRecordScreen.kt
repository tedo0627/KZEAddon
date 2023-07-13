package mcpc.tedo0627.kzeaddon.fabric.screen

import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.fabric.service.BattleRecordService
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.MultiLineTextWidget
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component
import kotlin.math.max
import kotlin.math.min

class BattleRecordScreen(private val battleRecord: List<BattleRecordService.BattleRecord>) : Screen(Component.literal("戦績")) {

    private var page = 0

    private lateinit var mapNameWidget: MultiLineTextWidget
    private lateinit var mapCreatorWidget: MultiLineTextWidget
    private lateinit var battleRecordWidget: MultiLineTextWidget
    private lateinit var pageWidget: MultiLineTextWidget

    override fun init() {
        if (battleRecord.isEmpty()) {
            val widget = MultiLineTextWidget(0, 0, Component.literal("まだ戦績がありません"), font)
            widget.setPosition(width / 2 - widget.width / 2, height / 2 - widget.height / 2)
            addRenderableWidget(widget)
            return
        }

        mapNameWidget = MultiLineTextWidget(0, 0, Component.empty(), font)
        mapCreatorWidget = MultiLineTextWidget(0, 0, Component.empty(), font)
        battleRecordWidget = MultiLineTextWidget(0, 0, Component.empty(), font)
        pageWidget = MultiLineTextWidget(0, 0, Component.empty(), font)
        addRenderableWidget(mapNameWidget)
        addRenderableWidget(mapCreatorWidget)
        addRenderableWidget(battleRecordWidget)
        addRenderableWidget(pageWidget)

        updatePage()

        val buttonLeft = Button.Builder(Component.literal("前へ")) {
            page--
            updatePage()
        }
            .bounds(width / 4 - 40, height / 4 * 3 - 10, 80, 20)
            .build()
        addRenderableWidget(buttonLeft)

        val buttonRight = Button.Builder(Component.literal("次へ")) {
            page++
            updatePage()
        }
            .bounds(width / 4 * 3 - 40, height / 4 * 3 - 10, 80, 20)
            .build()
        addRenderableWidget(buttonRight)
    }

    override fun render(poseStack: PoseStack, i: Int, j: Int, f: Float) {
        renderBackground(poseStack)
        drawCenteredString(poseStack, font, title, width / 2, 5, 0xffffff)
        super.render(poseStack, i, j, f)
    }

    override fun mouseScrolled(d: Double, e: Double, f: Double): Boolean {
        page -= f.toInt()
        updatePage()
        return super.mouseScrolled(d, e, f)
    }

    private fun updatePage() {
        val size = battleRecord.size
        if (size == 0) return

        page = max(0, page)
        page = min(size - 1, page)

        val record = battleRecord[size - 1 - page]

        mapNameWidget.message = Component.literal("MAP - ${record.map}")
        mapNameWidget.setPosition(width / 2 - mapNameWidget.width / 2, 25)

        mapCreatorWidget.message = Component.literal("Created by - ${record.mapCreator}")
        mapCreatorWidget.setPosition(width / 2 - mapCreatorWidget.width / 2, 25 + 12)

        battleRecordWidget.message = record.component
        battleRecordWidget.setPosition(width / 2 - battleRecordWidget.width / 2, height / 5 * 2 - battleRecordWidget.height / 2)

        pageWidget.message = Component.literal("${page + 1} / $size")
        pageWidget.setPosition(width / 2 - pageWidget.width / 2, height / 4 * 3 - pageWidget.height / 2)
    }

    override fun keyPressed(i: Int, j: Int, k: Int): Boolean {
        val key = Minecraft.getInstance().options.keyInventory
        if (key.matches(i, j)) {
            onClose()
            return true
        }

        return super.keyPressed(i, j, k)
    }

    override fun mouseClicked(d: Double, e: Double, i: Int): Boolean {
        val key = Minecraft.getInstance().options.keyInventory
        if (key.matchesMouse(i)) {
            onClose()
            return true
        }

        return super.mouseClicked(d, e, i)
    }

    override fun isPauseScreen() = false
}