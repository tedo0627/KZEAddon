package mcpc.tedo0627.kzeaddon.fabric.option

import net.minecraft.util.TranslatableOption

enum class HideToggleType(private val id: Int, private val key: String) : TranslatableOption {
    SWITCH(0, "kzeaddon.options.hideToggleType.switch"),
    LONG_PRESS(1, "kzeaddon.options.hideToggleType.long_press");

    override fun getId() = id

    override fun getTranslationKey() = key
}