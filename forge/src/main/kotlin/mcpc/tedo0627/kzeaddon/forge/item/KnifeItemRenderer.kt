package mcpc.tedo0627.kzeaddon.forge.item

import mcpc.tedo0627.kzeaddon.forge.KZEAddon
import net.minecraft.resources.ResourceLocation
import software.bernie.geckolib.model.DefaultedItemGeoModel
import software.bernie.geckolib.renderer.GeoItemRenderer

class KnifeItemRenderer(name: String) : GeoItemRenderer<KnifeItem>(
    DefaultedItemGeoModel(ResourceLocation(KZEAddon.MOD_ID, name))
)