package mcpc.tedo0627.kzeaddon.fabric.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import mcpc.tedo0627.kzeaddon.fabric.option.AddonOptions;
import mcpc.tedo0627.kzeaddon.fabric.service.HidePlayerService;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerItemInHandLayer.class)
public abstract class MixinPlayerItemInHandLayer {

    @Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
    private void renderArmWithItem(LivingEntity livingEntity, ItemStack itemStack, ItemDisplayContext itemDisplayContext, HumanoidArm humanoidArm, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo ci) {
        if (HidePlayerService.isInvisibleItem(livingEntity) && AddonOptions.hidePlayerItem.get()) ci.cancel();
    }
}
