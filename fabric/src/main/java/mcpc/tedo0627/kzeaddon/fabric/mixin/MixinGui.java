package mcpc.tedo0627.kzeaddon.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mcpc.tedo0627.kzeaddon.fabric.event.TitleUpdateCallback;
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class MixinGui {

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void renderCrosshairHead(PoseStack poseStack, CallbackInfo ci) {
        if (AddonOptions.crosshair.get()) ci.cancel();
    }

    @Inject(method = "setTitle", at = @At("HEAD"))
    private void setTitle(Component component, CallbackInfo ci) {
        TitleUpdateCallback.TITLE.invoker().callback(component);
    }

    @Inject(method = "setSubtitle", at = @At("HEAD"))
    private void setSubtitle(Component component, CallbackInfo ci) {
        TitleUpdateCallback.SUBTITLE.invoker().callback(component);
    }
}
