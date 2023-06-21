package mcpc.tedo0627.kzeaddon.fabric.mixin;

import mcpc.tedo0627.kzeaddon.fabric.service.HidePlayerService;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Inject(method = "isInvisible()Z", at = @At("HEAD"), cancellable = true)
    private void isInvisibleInject(@NotNull CallbackInfoReturnable<Boolean> cir) {
        boolean invisibleFlag = this.getFlag(5);
        cir.setReturnValue(HidePlayerService.isInvisible(getUuid(), getScoreboardTeam(), invisibleFlag));
    }

    @Shadow
    protected abstract boolean getFlag(int index);

    @Shadow
    @Nullable
    public abstract AbstractTeam getScoreboardTeam();

    @Shadow
    public abstract UUID getUuid();

    @Inject(method = "isInvisibleTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getScoreboardTeam()Lnet/minecraft/scoreboard/AbstractTeam;"), cancellable = true)
    private void isInvisibleTo(PlayerEntity playerEntity, CallbackInfoReturnable<Boolean> cir) {
        if (HidePlayerService.isOverrideIsInvisibleToFunc()) {
            cir.setReturnValue(isInvisible());
            cir.cancel();
        }
    }

    @Shadow
    public abstract boolean isInvisible();
}
