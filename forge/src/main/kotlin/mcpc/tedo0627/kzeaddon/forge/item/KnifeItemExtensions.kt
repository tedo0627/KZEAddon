package mcpc.tedo0627.kzeaddon.forge.item

import net.minecraftforge.client.extensions.common.IClientItemExtensions

class KnifeItemExtensions(name: String) : IClientItemExtensions {

    var renderer = KnifeItemRenderer(name)

    override fun getCustomRenderer() = renderer
}