package mcpc.tedo0627.kzeaddon.forge.mixin;

import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.effect.MobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LightTexture.class)
public class MixinLightTexture {

    @ModifyArg(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F", ordinal = 2), index = 1)
    private float updateLightTexture(float value) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return value;

        if (AddonOptions.darknessRemoveGamma.get() && player.hasEffect(MobEffects.DARKNESS)) return value;
        return AddonOptions.gamma.get() ? 10000f : value;
    }
}
