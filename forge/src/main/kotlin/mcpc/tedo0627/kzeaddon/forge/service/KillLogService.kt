package mcpc.tedo0627.kzeaddon.forge.service

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

    private val weaponConverter = mutableMapOf(
        "barrettm82" to "m82",
        "saiga12" to "saiga",
        "taurusjudge" to "judge",
        "scoutelite" to "scout",
        "remingtonm870" to "m870",
        "benellim4" to "beneli",
        "conderfieldshotgun" to "conderfield",
        "s_wm37" to "m37",
        "l96a1" to "l96",
        "bizonpp19" to "bizon",
        "mauserc96" to "c96",
        "mosinnagant" to "mosin",
        "bizonpp19" to "bizon",
        "ruger_mki" to "ruger",
        "survival_knife" to "knife",
        "krissvector" to "kriss",
        "m92fs" to "pt92"
    )

    private val addonResources = mutableMapOf<String, ResourceLocation>()
    private val textureSize = mutableMapOf<ResourceLocation, Pair<Int, Int>>()

    @SubscribeEvent
    fun onKey(event: InputEvent.Key) = input(event.key)

    @SubscribeEvent
    fun onMouseButton(event: InputEvent.MouseButton) = input(event.button)

    private fun input(key: Int) {
        if (key != this.key.key.value) return

        if (Minecraft.getInstance().player == null) return

        if (this.key.isDown) openKillLogScreen()
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
        val killLog = KillLog(group[1], group[2], group[3], weapon, group[0].isNotEmpty())
        overlayList.add(killLog)
        guiList.add(killLog)

        if (AddonOptions.killLogHeight.get() < overlayList.size) overlayList.removeAt(0)
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

        val mc = Minecraft.getInstance()
        if (mc.screen is KillLogScreen) return
        if (AddonOptions.disableKillLogWhenPressTab.get() && mc.options.keyPlayerList.isDown) return

        val size = overlayList.size
        overlayList.forEachIndexed { index, killLog ->
            renderWeapon(killLog, size - index - 1, event.poseStack)
        }
    }

    fun renderWeapon(killLog: KillLog, step: Int, poseStack: PoseStack) {
        var resourceLocation = addonResources.getOrPut(killLog.weaponId) {
            ResourceLocation("textures/font/${killLog.weaponId}.png")
        }
        val mc = Minecraft.getInstance()
        val window = mc.window
        val font = mc.font

        val size = textureSize.getOrPut(resourceLocation) {
            val pair = getSize(resourceLocation)
            if (pair == null) {
                val convert = weaponConverter[killLog.weaponId]
                if (convert != null) {
                    val convertResourceLocation = ResourceLocation("textures/font/${convert}.png")
                    val convertPair = getSize(convertResourceLocation)
                    if (convertPair != null) {
                        addonResources[killLog.weaponId] = convertResourceLocation
                        resourceLocation = convertResourceLocation
                        return@getOrPut convertPair
                    }
                }

                val addonResourceLocation = ResourceLocation("kzeaddon", "textures/font/${killLog.weaponId}.png")
                val addonPair = getSize(addonResourceLocation)
                if (addonPair == null) {
                    LogManager.getLogger().info("not found weapon, log target: ${killLog.target}, killer: ${killLog.killer}, weapon: ${killLog.weaponId}")
                } else {
                    addonResources[killLog.weaponId] = addonResourceLocation
                    resourceLocation = addonResourceLocation
                    return@getOrPut addonPair
                }
            }
            pair ?: Pair(0, 0)
        }

        val nameLength = font.width("a".repeat(16))
        val weaponLength = font.width("a".repeat(6))
        val height = 5 + step * (font.lineHeight + 2)

        val addWeaponName = AddonOptions.addKillLogWeaponName.get()
        val weaponNameLength = if (addWeaponName) font.width("a".repeat(18)) else 0

        val overlayX = AddonOptions.killLogOverlayLocationX.get()
        val overlayY = AddonOptions.killLogOverlayLocationY.get()

        val backColor = if (killLog.firstBlood) 1688862720 else mc.options.getBackgroundColor(Integer.MIN_VALUE)
        GuiComponent.fill(
            poseStack,
            window.guiScaledWidth - nameLength * 2 - weaponLength - weaponNameLength - 1 + overlayX,
            height + overlayY,
            window.guiScaledWidth - nameLength - weaponLength - weaponNameLength + 1 + overlayX,
            height + font.lineHeight + 2 + overlayY,
            -90,
            backColor)
        GuiComponent.fill(
            poseStack,
            window.guiScaledWidth - nameLength - weaponNameLength - 1 + overlayX,
            height + overlayY,
            window.guiScaledWidth + overlayX,
            height + font.lineHeight + 2 + overlayY,
            -90,
            backColor
        )

        val aqua = 43690
        val green = 5635925
        val yellow = 16777045
        val white = 16777215
        val isZombieKiller = killLog.weaponId == "infected"
        val myName = mc.player?.name?.string ?: return
        font.draw(
            poseStack, killLog.killer,
            (window.guiScaledWidth - nameLength * 2 - weaponLength - weaponNameLength).toFloat() + overlayX,
            height + 1f + overlayY,
            if (myName == killLog.killer) yellow else if (isZombieKiller) green else aqua
        )
        font.draw(
            poseStack, killLog.target,
            (window.guiScaledWidth - nameLength).toFloat() + overlayX,
            height + 1f + overlayY,
            if (myName == killLog.target) yellow else if (!isZombieKiller) green else aqua
        )
        if (addWeaponName) {
            font.draw(
                poseStack, killLog.weaponName,
                (window.guiScaledWidth - nameLength - weaponNameLength).toFloat() + overlayX,
                height + 1f + overlayY,
                white
            )
        }

        if (size == Pair(0, 0)) return
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        RenderSystem.setShaderTexture(0, resourceLocation)
        if (isZombieKiller) {
            // ゾンビの攻撃のテクスチャの位置を変える
            GuiComponent.blit(
                poseStack,
                window.guiScaledWidth - nameLength - weaponLength - weaponNameLength + 2 + overlayX,
                height + 1 + overlayY,
                -90, 0.0f, 0.0f, size.first, size.second, size.first, size.second
            )
        } else {
            GuiComponent.blit(
                poseStack,
                window.guiScaledWidth - nameLength - weaponLength - weaponNameLength + overlayX,
                height + 1 + overlayY,
                -90, 0.0f, 0.0f, size.first, size.second, size.first, size.second
            )
        }
        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
    }

    private fun getSize(resourceLocation: ResourceLocation): Pair<Int, Int>? {
        val mc = Minecraft.getInstance()
        val font = mc.font
        return try {
            val resource: Resource = mc.resourceManager.getResourceOrThrow(resourceLocation)
            resource.open().use { inputStream ->
                val nativeImage = NativeImage.read(inputStream)
                val divide = nativeImage.height / font.lineHeight
                Pair(nativeImage.width / divide, nativeImage.height / divide)
            }
        } catch (e: Exception) {
            null
        }
    }

    fun openKillLogScreen() {
        Minecraft.getInstance().setScreen(KillLogScreen(key, guiList, this))
    }

    class KillLog(
        val target: String,
        val killer: String,
        val weaponName: String,
        val weaponId: String,
        val firstBlood: Boolean = false
    ) {
        var displayTIme = 200
    }
}