package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.Minecraft
import net.minecraft.world.item.Items

class DisplayBulletService {

    init {
        HudRenderCallback.EVENT.register { poseStack, _ ->
            val mc = Minecraft.getInstance()
            val player = mc.player ?: return@register

            if (!AddonOptions.displayBullet.get()) return@register

            val inventory = player.inventory
            for (slot in 0 until 2) {
                val itemStack = inventory.items[slot]
                if (itemStack.item != Items.DIAMOND_HOE) continue

                val name = itemStack.hoverName.string
                val split = name.split(" ").filter { it.isNotEmpty() }
                if (split.size != 3) continue

                val currentBullet = split[1]
                val remainingBullet = split[2]
                if (currentBullet.toIntOrNull() == null || remainingBullet.toIntOrNull() == null) return@register

                val x = (mc.window.guiScaledWidth / 2).toFloat() - 20 * (4 - slot)
                val y = (mc.window.guiScaledHeight - 49).toFloat()
                val font = mc.font
                font.draw(
                    poseStack,
                    currentBullet,
                    x - font.width(currentBullet) / 2 + AddonOptions.currentBulletOverlayLocationX.get(),
                    y + AddonOptions.currentBulletOverlayLocationY.get(),
                    16777215
                )
                font.draw(
                    poseStack,
                    remainingBullet,
                    x - font.width(remainingBullet) / 2 + AddonOptions.remainingBulletOverlayLocationX.get(),
                    y + 10 + AddonOptions.remainingBulletOverlayLocationY.get(),
                    16777215
                )
            }
        }
    }
}