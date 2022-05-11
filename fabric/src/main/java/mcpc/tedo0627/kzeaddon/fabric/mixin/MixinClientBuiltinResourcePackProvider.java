package mcpc.tedo0627.kzeaddon.fabric.mixin;

import mcpc.tedo0627.kzeaddon.fabric.KZEAddon;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientBuiltinResourcePackProvider;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourcePackSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.util.concurrent.CompletableFuture;

@Mixin(ClientBuiltinResourcePackProvider.class)
public class MixinClientBuiltinResourcePackProvider {

    @Shadow
    @Nullable
    private ResourcePackProfile serverContainer;

    @Inject(at = @At("HEAD"), method = "loadServerPack(Ljava/io/File;Lnet/minecraft/resource/ResourcePackSource;)Ljava/util/concurrent/CompletableFuture;", cancellable = true)
    private void loadServerPack(File packZip, ResourcePackSource packSource, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        if (!KZEAddon.config.isDisableResourcePackReload()) return;
        if (this.serverContainer != null) {
            cir.setReturnValue(MinecraftClient.getInstance().submit(() -> new CompletableFuture<Void>()).thenCompose((p_167945_) -> p_167945_));
        }
    }

    @Inject(at = @At("HEAD"), method = "clear()V", cancellable = true)
    private void clear(CallbackInfo ci) {
        if (!KZEAddon.config.isDisableResourcePackReload()) return;
        if (this.serverContainer != null) ci.cancel();
    }
}
