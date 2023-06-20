package mcpc.tedo0627.kzeaddon.forge.mixin;

import mcpc.tedo0627.kzeaddon.forge.service.HidePlayerService;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.Team;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void isInvisibleFlag(@NotNull CallbackInfoReturnable<Boolean> cir) {
        boolean invisibleFlag = this.getSharedFlag(5);
        cir.setReturnValue(HidePlayerService.isInvisible(getUUID(), getTeam(), invisibleFlag));
    }

    @Shadow
    protected abstract boolean getSharedFlag(int p_20292_);

    @Shadow
    public abstract UUID getUUID();

    @Shadow
    @Nullable
    public abstract Team getTeam();

    @Inject(method = "isInvisibleTo", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getTeam()Lnet/minecraft/world/scores/Team;"), cancellable = true)
    private void isInvisibleTo(Player p_20178_, CallbackInfoReturnable<Boolean> cir) {
        if (HidePlayerService.isOverrideIsInvisibleToFunc()) {
            cir.setReturnValue(isInvisible());
            cir.cancel();
        }
    }

    @Shadow
    public abstract boolean isInvisible();
}
