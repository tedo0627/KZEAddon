package mcpc.tedo0627.kzeaddon.fabric.mixin;

import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer {

    @Inject(method = "getRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getItemEntityTranslucentCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void getRenderLayer(LivingEntity livingEntity, boolean bl, boolean bl2, boolean bl3, CallbackInfoReturnable<@Nullable RenderLayer> cir, Identifier identifier) {
        if (AddonOptions.hidePlayerOverlay.getValue()) {
            cir.setReturnValue(RenderLayer.getEntityTranslucentEmissive(identifier));
            cir.cancel();
        }
    }
}
