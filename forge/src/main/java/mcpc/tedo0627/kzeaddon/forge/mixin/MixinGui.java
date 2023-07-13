package mcpc.tedo0627.kzeaddon.forge.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void renderCrosshairHead(PoseStack poseStack, CallbackInfo ci) {
        if (AddonOptions.crosshair.get()) ci.cancel();
    }
}
