package mcpc.tedo0627.kzeaddon.listener

import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.extension.PlayerExtension
import net.minecraft.client.Minecraft
import net.minecraft.item.Items
import net.minecraftforge.client.event.RenderGameOverlayEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class DisplayBulletListener(val addon: KZEAddon) : PlayerExtension {

    private var mainBullet = 0
    private var subBullet = 0
    private var allBullet = 0

    var mainOffsetX = 0
    var mainOffsetY = 0

    var subOffsetX = 0
    var subOffsetY = 0

    var allOffsetX = 0
    var allOffsetY = 0

    private var testInt = 0

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.START) return

        val mc = Minecraft.getInstance()
        val player = mc.player ?: return
        val inventory = player.inventory ?: return

        val mainItem = inventory.getStackInSlot(0)
        if (mainItem.item == Items.DIAMOND_HOE) {
            var bullet: Int? = null
            var allBullet: Int? = null

            for (str in mainItem.displayName.formattedText.split(" ")) {
                if (str.isEmpty()) continue

                val number = if (str.startsWith("§") && str.length > 8) {
                    str.substring(4, str.length - 4).toIntOrNull()
                } else {
                    str.toIntOrNull()
                } ?: continue

                if (bullet == null) {
                    bullet = number
                    continue
                }

                if (allBullet == null) {
                    allBullet = number
                    break
                }
            }

            if (bullet != null) mainBullet = bullet
            if (allBullet != null && player.inventory.currentItem == 0) this.allBullet = allBullet
        }

        val subItem = inventory.getStackInSlot(1)
        if (subItem.item == Items.DIAMOND_HOE) {
            var bullet: Int? = null
            var allBullet: Int? = null

            for (str in subItem.displayName.unformattedComponentText.split(" ")) {
                if (str.isEmpty()) continue

                val number = if (str.startsWith("§") && str.length > 8) {
                    str.substring(4, str.length - 4).toIntOrNull()
                } else {
                    str.toIntOrNull()
                } ?: continue

                if (bullet == null) {
                    bullet = number
                    continue
                }

                if (allBullet == null) {
                    allBullet = number
                    break
                }
            }

            if (bullet != null) subBullet = bullet
            if (allBullet != null && player.inventory.currentItem == 1) this.allBullet = allBullet
        }

        if (mainItem.item != Items.DIAMOND_HOE && subItem.item != Items.DIAMOND_HOE) {
            mainBullet = 0
            subBullet = 0
            allBullet = 0
        }
    }

    @SubscribeEvent
    fun onRenderGameOverlay(event: RenderGameOverlayEvent) {
        if (event.type != RenderGameOverlayEvent.ElementType.EXPERIENCE) return

        val mc = Minecraft.getInstance()
        if (mc.player == null) return
        if (!addon.displayBullet) return

        val window = event.window
        val x = (window.scaledWidth / 2).toFloat()
        val y = (window.scaledHeight - 40).toFloat()

        val mainText = mainBullet.toString()
        val mainTextSize = mc.fontRenderer.getStringWidth(mainText) / 2

        val subText = subBullet.toString()
        val subTextSize = mc.fontRenderer.getStringWidth(subText) / 2

        val allText = allBullet.toString()
        val allTextSize = mc.fontRenderer.getStringWidth(allText) / 2

        mc.fontRenderer.drawString(mainText, x - 80 - mainTextSize + mainOffsetX, y + mainOffsetY, 16777215)
        mc.fontRenderer.drawString(subText, x - 60 - subTextSize + subOffsetX, y + subOffsetY, 16777215)
        mc.fontRenderer.drawString(allText, x - 40 - allTextSize + allOffsetX, y + allOffsetY, 16777215)
    }
}