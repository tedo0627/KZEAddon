package mcpc.tedo0627.kzeaddon.fabric.service

import com.mojang.blaze3d.systems.RenderSystem
import mcpc.tedo0627.kzeaddon.fabric.event.ChatReceiveCallback
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.hud.InGameHud
import net.minecraft.client.texture.NativeImage
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.resource.Resource
import net.minecraft.util.Identifier
import org.apache.logging.log4j.LogManager

class KillLogService {

    private val regex = Regex("""》(FirstBlood! |)(.*) killed by (.*) \(([A-Za-z0-9_-]+) ?\)""")

    private val list = mutableListOf<KillLog>()

    private val textureSize = mutableMapOf<Identifier, Pair<Int, Int>>()

    /**
     * Samples
     * 》target killed by killer (infected)
     * 》FirstBlood! target killed by killer (infected)
     * 》target killed by killer (PSG-1 )
     * 》FirstBlood! target killed by killer (PSG-1 )
     */
    init {
        ChatReceiveCallback.EVENT.register(ChatReceiveCallback.FIRST) { text ->
            if (!AddonOptions.displayKillLog.value) return@register true

            val result = regex.matchEntire(text.string) ?: return@register true

            val group = result.groupValues.toMutableList()
            group.removeAt(0)

            val weapon = group[3].replace("-", "").lowercase()
            list.add(KillLog(group[1], group[2], weapon, group[0].isNotEmpty()))

            if (10 < list.size) list.removeAt(0)

            return@register true
        }

        ClientTickEvents.END_CLIENT_TICK.register {
            list.toList().forEach {
                if (--it.displayTIme < 0) list.remove(it)
            }
        }

        HudRenderCallback.EVENT.register { _, _ ->
            val size = list.size
            list.forEachIndexed { index, killLog ->
                renderWeapon(killLog, size - index - 1)
            }
        }
    }

    private fun renderWeapon(killLog: KillLog, step: Int) {
        val matrixStack = MatrixStack()
        val identifier = Identifier("textures/font/${killLog.weapon}.png")
        val client = MinecraftClient.getInstance()
        val window = client.window
        val renderer = client.textRenderer

        val size = textureSize.getOrPut(identifier) {
            try {
                val resource: Resource = client.resourceManager.getResourceOrThrow(identifier)
                resource.inputStream.use { inputStream ->
                    val nativeImage = NativeImage.read(inputStream)
                    val divide = nativeImage.height / renderer.fontHeight
                    Pair(nativeImage.width / divide, nativeImage.height / divide)
                }
            } catch (e: Exception) {
                LogManager.getLogger().info("not found weapon, log target: ${killLog.target}, killer: ${killLog.killer}, weapon: ${killLog.weapon}")
                Pair(0, 0)
            }
        }

        val nameLength = renderer.getWidth("a".repeat(16))
        val weaponLength = renderer.getWidth("a".repeat(6))
        val height = 5 + step * (renderer.fontHeight + 2)

        val backColor = if (killLog.firstBlood) 1688862720 else client.options.getTextBackgroundColor(Integer.MIN_VALUE)
        InGameHud.fill(matrixStack, window.scaledWidth - nameLength * 2 - weaponLength - 1, height, window.scaledWidth - nameLength - weaponLength + 1, height + renderer.fontHeight + 2, -90, backColor)
        InGameHud.fill(matrixStack, window.scaledWidth - nameLength - 1, height, window.scaledWidth, height + renderer.fontHeight + 2, -90, backColor)

        val aqua = 43690
        val green = 5635925
        val yellow = 16777045
        val isZombieKiller = killLog.weapon == "infected"
        val myName = client.player?.name?.string ?: return
        renderer.drawWithShadow(
            matrixStack, killLog.killer,
            (window.scaledWidth - nameLength * 2 - weaponLength).toFloat(), height + 1f,
            if (myName == killLog.killer) yellow else if (isZombieKiller) green else aqua
        )
        renderer.drawWithShadow(
            matrixStack, killLog.target,
            (window.scaledWidth - nameLength).toFloat(), height + 1f,
            if (myName == killLog.target) yellow else if (!isZombieKiller) green else aqua
        )

        if (size == Pair(0, 0)) return
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        RenderSystem.setShaderTexture(0, identifier)
        if (isZombieKiller) {
            // ゾンビの攻撃のテクスチャの位置を変える
            InGameHud.drawTexture(matrixStack, window.scaledWidth - nameLength - weaponLength + 2, height + 1, -90, 0.0f, 0.0f, size.first, size.second, size.first, size.second)
        } else {
            InGameHud.drawTexture(matrixStack, window.scaledWidth - nameLength - weaponLength, height + 1, -90, 0.0f, 0.0f, size.first, size.second, size.first, size.second)
        }
        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
    }

    class KillLog(
        val target: String,
        val killer: String,
        val weapon: String,
        val firstBlood: Boolean = false
    ) {
        var displayTIme = 200
    }
}