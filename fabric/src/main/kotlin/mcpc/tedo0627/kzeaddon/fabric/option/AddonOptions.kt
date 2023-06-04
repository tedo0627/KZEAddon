package mcpc.tedo0627.kzeaddon.fabric.option

import com.mojang.datafixers.util.Either
import com.mojang.serialization.Codec
import net.minecraft.client.option.SimpleOption

object AddonOptions {

    val optionsMap: Map<String, SimpleOption<*>>
        get() {
            return mutableMapOf(
                "hidePlayerToggle" to hidePlayerToggle,
                "gamma" to gamma
            )
        }

    val options: Array<SimpleOption<*>>
        get() {
            return optionsMap.values.toTypedArray()
        }

    val hidePlayerToggle = SimpleOption(
        "kzeaddon.options.hidePlayerToggle",
        SimpleOption.emptyTooltip(),
        SimpleOption.enumValueText(),
        SimpleOption.PotentialValuesBasedCallbacks(
            HideToggleType.values().toList(),
            Codec.either(Codec.BOOL, Codec.STRING).xmap({ either ->
                either.map({ bool ->
                    if (bool) HideToggleType.SWITCH else HideToggleType.LONG_PRESS
                }, { str ->
                    if (str == "true") HideToggleType.SWITCH else HideToggleType.LONG_PRESS
                })
            }, { type ->
                Either.right(when (type) {
                    HideToggleType.SWITCH -> "true"
                    HideToggleType.LONG_PRESS -> "false"
                    else -> "true"
                })
            })
        ),
        HideToggleType.SWITCH
    ) {}

    val gamma = SimpleOption.ofBoolean(
        "kzeaddon.options.gamma",
        SimpleOption.emptyTooltip(),
        false
    ) {}
}