package mcpc.tedo0627.kzeaddon.forge.mixin;

import mcpc.tedo0627.kzeaddon.forge.KZEAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.ClientPackSource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.io.File;
import java.util.concurrent.CompletableFuture;

@Mixin(ClientPackSource.class)
public class MixinClientPackSource {

    @Shadow
    @Nullable
    private Pack serverPack;

    @Inject(at = @At("HEAD"), method = "setServerPack", cancellable = true)
    private void setServerPack(File file, PackSource pack, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (!KZEAddon.config.isDisableResourcePackReload()) return;
        if (this.serverPack != null) {
            cir.setReturnValue(Minecraft.getInstance().submit(() -> new CompletableFuture<Void>()).thenCompose((p_167945_) -> p_167945_));
        }
    }

    @Inject(at = @At("HEAD"), method = "clearServerPack", cancellable = true)
    private void clearServerPack(CallbackInfo ci) {
        if (!KZEAddon.config.isDisableResourcePackReload()) return;
        if (this.serverPack != null) ci.cancel();
    }
}
