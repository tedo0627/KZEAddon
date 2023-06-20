package mcpc.tedo0627.kzeaddon.fabric.option

import net.minecraft.util.TranslatableOption

enum class InvisibleType(private val id: Int, private val key: String) : TranslatableOption {
    TRANSLUCENT(0, "kzeaddon.options.invisibleType.translucent"),
    INVISIBLE(1, "kzeaddon.options.invisibleType.invisible"),
    TOGGLE(2, "kzeaddon.options.invisibleType.toggle");

    override fun getId() = id

    override fun getTranslationKey() = key
}