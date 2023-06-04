package mcpc.tedo0627.kzeaddon.forge.option

import net.minecraft.util.OptionEnum

enum class HideToggleType(private val id: Int, private val key: String) : OptionEnum {
    SWITCH(0, "kzeaddon.options.enum.switch"),
    LONG_PRESS(1, "kzeaddon.options.enum.long_press");

    override fun getId() = id

    override fun getKey() = key
}