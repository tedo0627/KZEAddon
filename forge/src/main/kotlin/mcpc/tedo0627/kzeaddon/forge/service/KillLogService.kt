package mcpc.tedo0627.kzeaddon.forge.service

import com.mojang.blaze3d.platform.InputConstants
import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.forge.event.ChatReceiveEvent
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions
import mcpc.tedo0627.kzeaddon.forge.screen.KillLogScreen
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.Resource
import net.minecraftforge.client.event.InputEvent
import net.minecraftforge.client.event.RenderGuiOverlayEvent
import net.minecraftforge.client.gui.overlay.GuiOverlayManager
import net.minecraftforge.event.TickEvent
import net.minecraftforge.event.TickEvent.ClientTickEvent
import net.minecraftforge.eventbus.api.EventPriority
import net.minecraftforge.eventbus.api.SubscribeEvent
import org.apache.logging.log4j.LogManager

class KillLogService(private val key: KeyMapping) {

    private val regex = Regex("""》(FirstBlood! |)(.*) killed by (.*) \(([A-Za-z0-9_-]+) ?\)""")

    private val overlayList = mutableListOf<KillLog>()
    private val guiList = mutableListOf<KillLog>()

    private val addonIdentifiers = mutableMapOf<String, ResourceLocation>()
    private val textureSize = mutableMapOf<ResourceLocation, Pair<Int, Int>>()

    @SubscribeEvent
    fun onKey(event: InputEvent.Key) = input(event.key, event.action)

    @SubscribeEvent
    fun onMouseButton(event: InputEvent.MouseButton) = input(event.button, event.action)

    private fun input(key: Int, action: Int) {
        if (key != this.key.key.value) return

        if (action == InputConstants.PRESS && this.key.isDown) {
            val client = Minecraft.getInstance()
            if (client.player == null) return

            client.setScreen(KillLogScreen(this.key, guiList, this))
        }
    }

    /**
     * Samples
     * 》target killed by killer (infected)
     * 》FirstBlood! target killed by killer (infected)
     * 》target killed by killer (PSG-1 )
     * 》FirstBlood! target killed by killer (PSG-1 )
     */
    @SubscribeEvent(priority = EventPriority.HIGH)
    fun onChatReceive(event: ChatReceiveEvent) {
        if (!AddonOptions.displayKillLog.get()) return

        val result = regex.matchEntire(event.component.string) ?: return

        val group = result.groupValues.toMutableList()
        group.removeAt(0)

        val weapon = group[3].replace("-", "").lowercase()
        val killLog = KillLog(group[1], group[2], weapon, group[0].isNotEmpty())
        overlayList.add(killLog)
        guiList.add(killLog)

        if (10 < overlayList.size) overlayList.removeAt(0)
        if (500 < guiList.size) guiList.removeAt(0)
    }

    @SubscribeEvent
    fun onClientTick(event: ClientTickEvent) {
        if (event.phase == TickEvent.Phase.START) return

        overlayList.toList().forEach {
            if (--it.displayTIme < 0) overlayList.remove(it)
        }
    }

    @SubscribeEvent
    fun onRenderGuiOverlayEvent(event: RenderGuiOverlayEvent.Pre) {
        if (GuiOverlayManager.getOverlays()[0] != event.overlay) return

        val client = Minecraft.getInstance()
        if (client.screen is KillLogScreen) return

        val size = overlayList.size
        overlayList.forEachIndexed { index, killLog ->
            renderWeapon(killLog, size - index - 1, event.poseStack)
        }
    }

    fun renderWeapon(killLog: KillLog, step: Int, matrixStack: PoseStack) {
        val identifier = addonIdentifiers.getOrPut(killLog.weapon) {
            ResourceLocation("textures/font/${killLog.weapon}.png")
        }
        val client = Minecraft.getInstance()
        val window = client.window
        val renderer = client.font

        val size = textureSize.getOrPut(identifier) {
            val pair = getSize(identifier)
            if (pair == null) {
                val addonIdentifier = ResourceLocation("kzeaddon", "textures/font/${killLog.weapon}.png")
                val addonPair = getSize(addonIdentifier)
                if (addonPair == null) {
                    LogManager.getLogger().info("not found weapon, log target: ${killLog.target}, killer: ${killLog.killer}, weapon: ${killLog.weapon}")
                } else {
                    addonIdentifiers[killLog.weapon] = addonIdentifier
                    return@getOrPut addonPair
                }
            }
            pair ?: Pair(0, 0)
        }

        val nameLength = renderer.width("a".repeat(16))
        val weaponLength = renderer.width("a".repeat(6))
        val height = 5 + step * (renderer.lineHeight + 2)

        val backColor = if (killLog.firstBlood) 1688862720 else client.options.getBackgroundColor(Integer.MIN_VALUE)
        GuiComponent.fill(
            matrixStack,
            window.guiScaledWidth - nameLength * 2 - weaponLength - 1 + AddonOptions.killLogOverlayLocationX.get(),
            height + AddonOptions.killLogOverlayLocationY.get(),
            window.guiScaledWidth - nameLength - weaponLength + 1 + AddonOptions.killLogOverlayLocationX.get(),
            height + renderer.lineHeight + 2 + AddonOptions.killLogOverlayLocationY.get(),
            -90,
            backColor
        )
        GuiComponent.fill(
            matrixStack,
            window.guiScaledWidth - nameLength - 1 + AddonOptions.killLogOverlayLocationX.get(),
            height + AddonOptions.killLogOverlayLocationY.get(),
            window.guiScaledWidth + AddonOptions.killLogOverlayLocationX.get(),
            height + renderer.lineHeight + 2 + AddonOptions.killLogOverlayLocationY.get(),
            -90,
            backColor
        )

        val aqua = 43690
        val green = 5635925
        val yellow = 16777045
        val isZombieKiller = killLog.weapon == "infected"
        val myName = client.player?.name?.string ?: return
        renderer.draw(
            matrixStack, killLog.killer,
            (window.guiScaledWidth - nameLength * 2 - weaponLength).toFloat() + AddonOptions.killLogOverlayLocationX.get(),
            height + 1f + AddonOptions.killLogOverlayLocationY.get(),
            if (myName == killLog.killer) yellow else if (isZombieKiller) green else aqua
        )
        renderer.draw(
            matrixStack, killLog.target,
            (window.guiScaledWidth - nameLength).toFloat() + AddonOptions.killLogOverlayLocationX.get(),
            height + 1f + AddonOptions.killLogOverlayLocationY.get(),
            if (myName == killLog.target) yellow else if (!isZombieKiller) green else aqua
        )

        if (size == Pair(0, 0)) return
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        RenderSystem.setShaderTexture(0, identifier)
        if (isZombieKiller) {
            // ゾンビの攻撃のテクスチャの位置を変える
            GuiComponent.blit(
                matrixStack,
                window.guiScaledWidth - nameLength - weaponLength + 2 + AddonOptions.killLogOverlayLocationX.get(),
                height + 1 + AddonOptions.killLogOverlayLocationY.get(),
                -90, 0.0f, 0.0f, size.first, size.second, size.first, size.second
            )
        } else {
            GuiComponent.blit(
                matrixStack,
                window.guiScaledWidth - nameLength - weaponLength + AddonOptions.killLogOverlayLocationX.get(),
                height + 1 + AddonOptions.killLogOverlayLocationY.get(),
                -90, 0.0f, 0.0f, size.first, size.second, size.first, size.second
            )
        }
        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
    }

    private fun getSize(identifier: ResourceLocation): Pair<Int, Int>? {
        val client = Minecraft.getInstance()
        val renderer = client.font
        return try {
            val resource: Resource = client.resourceManager.getResourceOrThrow(identifier)
            resource.open().use { inputStream ->
                val nativeImage = NativeImage.read(inputStream)
                val divide = nativeImage.height / renderer.lineHeight
                Pair(nativeImage.width / divide, nativeImage.height / divide)
            }
        } catch (e: Exception) {
            null
        }
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