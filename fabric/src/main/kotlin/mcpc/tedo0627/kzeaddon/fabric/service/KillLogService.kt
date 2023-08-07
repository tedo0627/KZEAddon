package mcpc.tedo0627.kzeaddon.fabric.service

import com.mojang.blaze3d.platform.NativeImage
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.PoseStack
import mcpc.tedo0627.kzeaddon.fabric.event.ChatReceiveCallback
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions
import mcpc.tedo0627.kzeaddon.fabric.screen.KillLogScreen
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback
import net.minecraft.client.KeyMapping
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.Resource
import org.apache.logging.log4j.LogManager

class KillLogService {

    private val key = KeyMapping("kzeaddon.key.killLog", -1, "KZEAddon")

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
        "m92fs" to "pt92",
        "howam1500" to "m1500"
    )

    private val addonResources = mutableMapOf<String, ResourceLocation>()
    private val textureSize = mutableMapOf<ResourceLocation, Pair<Int, Int>>()

    /**
     * Samples
     * 》target killed by killer (infected)
     * 》FirstBlood! target killed by killer (infected)
     * 》target killed by killer (PSG-1 )
     * 》FirstBlood! target killed by killer (PSG-1 )
     */
    init {
        KeyBindingHelper.registerKeyBinding(key)

        ClientTickEvents.END_CLIENT_TICK.register {
            if (Minecraft.getInstance().player == null) return@register

            if (key.isDown) openKillLogScreen()
        }

        ChatReceiveCallback.EVENT.register(ChatReceiveCallback.FIRST) { text ->
            if (!AddonOptions.displayKillLog.get()) return@register true

            val result = regex.matchEntire(text.string) ?: return@register true

            val group = result.groupValues.toMutableList()
            group.removeAt(0)

            val weapon = group[3].replace("-", "").lowercase()
            val killLog = KillLog(group[1], group[2], group[3], weapon, group[0].isNotEmpty())
            overlayList.add(killLog)
            guiList.add(killLog)

            if (AddonOptions.killLogHeight.get() < overlayList.size) overlayList.removeAt(0)
            if (500 < guiList.size) guiList.removeAt(0)

            return@register true
        }

        ClientTickEvents.END_CLIENT_TICK.register {
            overlayList.toList().forEach {
                if (--it.displayTIme < 0) overlayList.remove(it)
            }
        }

        HudRenderCallback.EVENT.register { poseStack, _ ->
            val mc = Minecraft.getInstance()
            if (mc.screen is KillLogScreen) return@register
            if (AddonOptions.disableKillLogWhenPressTab.get() && mc.options.keyPlayerList.isDown) return@register

            val size = overlayList.size
            val option = AddonOptions.killLogOverlay
            poseStack.pushPose()
            poseStack.scale(option.scalePercent, option.scalePercent, 1.0f)
            overlayList.forEachIndexed { index, killLog ->
                renderWeapon(killLog, size - index - 1, poseStack)
            }
            poseStack.popPose()
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

        val option = AddonOptions.killLogOverlay

        val nameLength = font.width("a".repeat(16))
        val weaponLength = font.width("a".repeat(6))
        val lineHeight = font.lineHeight + 2
        val height = step * lineHeight

        val addWeaponName = AddonOptions.addKillLogWeaponName.get()
        val weaponNameLength = if (addWeaponName) font.width("a".repeat(18)) else 0

        val startX = (window.guiScaledWidth - nameLength * 2 - weaponLength - weaponNameLength + option.x) / option.scalePercent
        val startY = height + (option.y + 5) / option.scalePercent

        val backColor = if (killLog.firstBlood) 1688862720 else mc.options.getBackgroundColor(Integer.MIN_VALUE)
        GuiComponent.fill(
            poseStack,
            (startX - 1).toInt(),
            (startY).toInt(),
            (startX + nameLength + 1).toInt(),
            (startY + lineHeight).toInt(),
            -90,
            backColor
        )
        GuiComponent.fill(
            poseStack,
            (startX + nameLength + weaponLength - 1).toInt(),
            (startY).toInt(),
            (startX + nameLength * 2 + weaponLength + weaponNameLength).toInt(),
            (startY + lineHeight).toInt(),
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
            startX,
            startY + 1,
            if (myName == killLog.killer) yellow else if (isZombieKiller) green else aqua
        )
        font.draw(
            poseStack, killLog.target,
            startX + nameLength + weaponLength + weaponNameLength,
            startY + 1,
            if (myName == killLog.target) yellow else if (!isZombieKiller) green else aqua
        )
        if (addWeaponName) {
            font.draw(
                poseStack, killLog.weaponName,
                startX + nameLength + weaponLength,
                startY + 1,
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
                (startX + nameLength + 2).toInt(),
                (startY + 1).toInt(),
                -90, 0.0f, 0.0f, size.first, size.second, size.first, size.second
            )
        } else {
            GuiComponent.blit(
                poseStack,
                (startX + nameLength).toInt(),
                (startY + 1).toInt(),
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