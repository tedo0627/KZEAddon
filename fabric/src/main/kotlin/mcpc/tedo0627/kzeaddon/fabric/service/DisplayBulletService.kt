package mcpc.tedo0627.kzeaddon.fabric.service

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.item.Items

class DisplayBulletService {

    init {
        HudRenderCallback.EVENT.register { _, _ ->
            val client = MinecraftClient.getInstance()
            val player = client.player ?: return@register

            if (!AddonOptions.displayBullet.value) return@register

            val inventory = player.inventory
            for (slot in 0 until 2) {
                val itemStack = inventory.main[slot]
                if (itemStack.item != Items.DIAMOND_HOE) continue

                val name = itemStack.name.string
                val split = name.split(" ").filter { it.isNotEmpty() }
                if (split.size != 3) continue

                val currentBullet = split[1]
                val remainingBullet = split[2]

                val x = (client.window.scaledWidth / 2).toFloat()
                val y = (client.window.scaledHeight - 49).toFloat()
                val renderer = client.textRenderer
                renderer.drawWithShadow(MatrixStack(), currentBullet, x - 20 * (4 - slot) - renderer.getWidth(currentBullet) / 2, y, 16777215)
                renderer.drawWithShadow(MatrixStack(), remainingBullet, x - 20 * (4 - slot) - renderer.getWidth(remainingBullet) / 2, y + 10, 16777215)
            }
        }
    }
}