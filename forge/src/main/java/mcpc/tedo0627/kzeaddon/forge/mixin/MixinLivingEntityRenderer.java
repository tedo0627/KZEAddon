package mcpc.tedo0627.kzeaddon.forge.mixin;

import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer {

    @Inject(method = "getRenderType", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;itemEntityTranslucentCull(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/renderer/RenderType;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void getRenderType(LivingEntity p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_, CallbackInfoReturnable<RenderType> cir, ResourceLocation resourceLocation) {
        if (AddonOptions.hidePlayerOverlay.get()) {
            cir.setReturnValue(RenderType.entityTranslucentEmissive(resourceLocation));
            cir.cancel();
        }
    }
}
