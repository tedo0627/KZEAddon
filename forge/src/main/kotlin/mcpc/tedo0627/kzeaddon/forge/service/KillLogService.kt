package mcpc.tedo0627.kzeaddon.forge.service

import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.gui.GuiComponent
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.Resource
import net.minecraftforge.client.event.RenderGuiOverlayEvent
import net.minecraftforge.client.gui.overlay.GuiOverlayManager
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.ClientTickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.apache.logging.log4j.LogManager

class KillLogService {

    companion object {

        private val list = mutableListOf<KillLog>()

        @JvmStatic
        fun receiveChat(text: Component): Boolean {
            if (!AddonOptions.displayKillLog.get()) return true

            val split = text.string.split(" ")
            when (split.size) {
                5 -> { // 》target killed by killer (infected)
                    if (split[1] != "killed" || split[2] != "by") return true

                    val target = split[0]
                    if (target[0] != '》') return true

                    val weapon = split[4].removePrefix("(").removeSuffix(")").replace("-", "").lowercase()
                    list.add(KillLog(target.removePrefix("》"), split[3], weapon))
                }
                6 -> {
                    if (split[0] == "》FirstBlood!") { // 》FirstBlood! target killed by killer (infected)
                        if (split[2] != "killed" || split[3] != "by") return true

                        val target = split[1]

                        val weapon = split[5].removePrefix("(").removeSuffix(")").replace("-", "").lowercase()
                        list.add(KillLog(target, split[4], weapon, true))
                    } else if (split[1] == "killed") { // 》target killed by killer (PSG-1 )
                        if (split[2] != "by" || split[5] != ")") return true

                        val target = split[0]
                        if (target[0] != '》') return true

                        val weapon = split[4].removePrefix("(").replace("-", "").lowercase()
                        list.add(KillLog(target.removePrefix("》"), split[3], weapon))
                    } else {
                        return true
                    }
                }
                7 -> { // 》FirstBlood! target killed by killer (PSG-1 )
                    if (split[0] != "》FirstBlood!" || split[2] != "killed" || split[3] != "by" || split[6] != ")") return true

                    val target = split[1]

                    val weapon = split[5].removePrefix("(").replace("-", "").lowercase()
                    list.add(KillLog(target.removePrefix("》"), split[4], weapon, true))
                }
                else -> return true
            }

            if (10 < list.size) list.removeAt(0)

            return false
        }
    }

    private val textureSize = mutableMapOf<ResourceLocation, Pair<Int, Int>>()

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent) {
        if (event.phase == TickEvent.Phase.START) return

        list.toList().forEach {
            if (--it.displayTIme < 0) list.remove(it)
        }
    }

    @SubscribeEvent
    fun onRenderGuiOverlayEvent(event: RenderGuiOverlayEvent.Pre) {
        if (GuiOverlayManager.getOverlays()[0] != event.overlay) return

        val size = list.size
        list.forEachIndexed { index, killLog ->
            renderWeapon(killLog, size - index - 1, event.poseStack)
        }
    }

    private fun renderWeapon(killLog: KillLog, step: Int, matrixStack: PoseStack) {
        //val matrixStack = PoseStack()
        val identifier = ResourceLocation("textures/font/${killLog.weapon}.png")
        val client = Minecraft.getInstance()
        val window = client.window
        val renderer = client.font

        val size = textureSize.getOrPut(identifier) {
            try {
                val resource: Resource = client.resourceManager.getResourceOrThrow(identifier)
                resource.open().use { inputStream ->
                    val nativeImage = NativeImage.read(inputStream)
                    val divide = nativeImage.height / renderer.lineHeight
                    Pair(nativeImage.width / divide, nativeImage.height / divide)
                }
            } catch (e: Exception) {
                LogManager.getLogger().info("not found weapon, log target: ${killLog.target}, killer: ${killLog.killer}, weapon: ${killLog.weapon}")
                Pair(0, 0)
            }
        }

        val nameLength = renderer.width("a".repeat(16))
        val weaponLength = renderer.width("a".repeat(5))
        val height = 5 + step * (renderer.lineHeight + 2)

        val backColor = if (killLog.firstBlood) 1688862720 else client.options.getBackgroundColor(Integer.MIN_VALUE)
        GuiComponent.fill(matrixStack, window.guiScaledWidth - nameLength * 2 - weaponLength - 1, height, window.guiScaledWidth - nameLength - weaponLength + 1, height + renderer.lineHeight + 2, -90, backColor)
        GuiComponent.fill(matrixStack, window.guiScaledWidth - nameLength - 1, height, window.guiScaledWidth, height + renderer.lineHeight + 2, -90, backColor)

        val aqua = 43690
        val green = 5635925
        val yellow = 16777045
        val isZombieKiller = killLog.weapon == "infected"
        val myName = client.player?.name?.string ?: return
        renderer.draw(
            matrixStack, killLog.killer,
            (window.guiScaledWidth - nameLength * 2 - weaponLength).toFloat(), height + 1f,
            if (myName == killLog.killer) yellow else if (isZombieKiller) green else aqua
        )
        renderer.draw(
            matrixStack, killLog.target,
            (window.guiScaledWidth - nameLength).toFloat(), height + 1f,
            if (myName == killLog.target) yellow else if (!isZombieKiller) green else aqua
        )

        if (size == Pair(0, 0)) return
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        RenderSystem.setShaderTexture(0, identifier)
        if (isZombieKiller) {
            // ゾンビの攻撃のテクスチャの位置を変える
            GuiComponent.blit(matrixStack, window.guiScaledWidth - nameLength - weaponLength + 2, height + 1, -90, 0.0f, 0.0f, size.first, size.second, size.first, size.second)
        } else {
            GuiComponent.blit(matrixStack, window.guiScaledWidth - nameLength - weaponLength, height + 1, -90, 0.0f, 0.0f, size.first, size.second, size.first, size.second)
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