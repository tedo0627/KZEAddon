package mcpc.tedo0627.kzeaddon.listener

import com.electronwill.nightconfig.core.CommentedConfig
import mcpc.tedo0627.kzeaddon.KZEAddon
import mcpc.tedo0627.kzeaddon.event.CreateBossBarEvent
import mcpc.tedo0627.kzeaddon.extension.PlayerExtension
import net.minecraft.client.Minecraft
import net.minecraft.item.Items
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.eventbus.api.SubscribeEvent

class KillCountListener(val addon: KZEAddon) : PlayerExtension {

    private val killCount = mutableMapOf<String, Int>()

    fun loadConfig() {
        if (!addon.config.exist("weaponStats")) return

        val values = addon.config.get<CommentedConfig>("weaponStats").valueMap()
        for (key in values.keys) {
            val value = values[key] as? CommentedConfig ?: continue
            killCount[key] = value.getIntOrElse("killCount", 0)
        }
    }

    @SubscribeEvent
    fun onCreateBossBar(event: CreateBossBarEvent) {
        val components = event.bossInfo.name.siblings
        if (components.size < 3) return

        val player = Minecraft.getInstance().player
        val component = components[1]
        if (component.unformattedComponentText != "${player.name.unformattedComponentText} " || component.style.color != TextFormatting.DARK_AQUA) return

        val item = player.inventory.getCurrentItem()
        val split = item.displayName.unformattedComponentText.split(" ")
        if (split.isEmpty()) return

        val str = split[0]
        val weapon = str.substring(2, str.length)
        if (!killCount.containsKey(weapon)) killCount[weapon] = addon.config.getWeaponKillCount(weapon)

        val count = (killCount[weapon] ?: 0) + 1
        killCount[weapon] = count

        addon.config.setWeaponKillCount(weapon, count)
        addon.config.save()
    }

    @SubscribeEvent
    fun onItemTooltip(event: ItemTooltipEvent) {
        val itemStack = event.itemStack
        if (itemStack.item != Items.DIAMOND_HOE) return

        var str = event.itemStack.displayName.unformattedComponentText
        while (str.startsWith("§")) {
            if (str.length < 3) break
            str = str.substring(2, str.length)
        }

        for (weapon in killCount.keys) {
            if (!str.startsWith(weapon)) continue
            val killCount = killCount[weapon] ?: continue

            val tooltip = event.toolTip
            tooltip.add(StringTextComponent(""))
            tooltip.add(StringTextComponent("§7kill数: §6$killCount"))
        }
    }
}