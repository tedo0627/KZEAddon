package mcpc.tedo0627.kzeaddon.fabric

import com.terraformersmc.modmenu.config.option.EnumConfigOption
import com.terraformersmc.modmenu.config.option.OptionConvertable
import mcpc.tedo0627.kzeaddon.fabric.service.HidePlayerService

object Options {

    val hidePlayer = EnumConfigOption("hidePlayerToggle", HidePlayerService.ToggleType.CLICK)

    val values: List<OptionConvertable>
        get() = mutableListOf<OptionConvertable>(hidePlayer)
}