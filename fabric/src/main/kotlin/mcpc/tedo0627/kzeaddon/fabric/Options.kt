package mcpc.tedo0627.kzeaddon.fabric

import com.terraformersmc.modmenu.config.option.BooleanConfigOption
import com.terraformersmc.modmenu.config.option.EnumConfigOption
import com.terraformersmc.modmenu.config.option.OptionConvertable
import mcpc.tedo0627.kzeaddon.fabric.service.HidePlayerService

object Options {

    val hidePlayer = EnumConfigOption("hidePlayerToggle", HidePlayerService.ToggleType.CLICK)

    val gamma = BooleanConfigOption("gammaMax", false)

    private val values: Array<OptionConvertable>
        get() = mutableListOf(hidePlayer, gamma).toTypedArray()

    init {
        values.forEach {
            when (it) {
                is BooleanConfigOption -> {
                    it.value = CustomConfig.getBoolean(it.key, it.defaultValue)
                }
                is EnumConfigOption<*> -> {
                    it.value = CustomConfig.getEnum(it.key, it.defaultValue)
                }
                else -> throw IllegalStateException()
            }
        }
    }

    fun toSimpleOptions() = values.map { it.asOption() }.toTypedArray()

    fun save() {
        values.forEach {
            when (it) {
                is BooleanConfigOption -> CustomConfig.set(it.key, it.value)
                is EnumConfigOption<*> -> CustomConfig.set(it.key, it.value)
                else -> throw IllegalStateException()
            }
        }

        CustomConfig.save()
    }
}