package mcpc.tedo0627.kzeaddon.forge.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import mcpc.tedo0627.kzeaddon.forge.event.SubtitleUpdateEvent;
import mcpc.tedo0627.kzeaddon.forge.event.TitleUpdateEvent;
import mcpc.tedo0627.kzeaddon.forge.option.AddonOptions;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.MinecraftForge;
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
        TitleUpdateEvent event = new TitleUpdateEvent(component);
        MinecraftForge.EVENT_BUS.post(event);
    }

    @Inject(method = "setSubtitle", at = @At("HEAD"))
    private void setSubtitle(Component component, CallbackInfo ci) {
        SubtitleUpdateEvent event = new SubtitleUpdateEvent(component);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
