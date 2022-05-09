package mcpc.tedo0627.kzeaddon.forge.mixin;

import mcpc.tedo0627.kzeaddon.forge.service.HidePlayerService;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.scores.Team;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(at = @At("HEAD"), method = "isInvisible", cancellable = true)
    private void isInvisible(CallbackInfoReturnable<Boolean> cir) {
        boolean bool = this.getSharedFlag(5);
        cir.setReturnValue(HidePlayerService.isInvisible(getUUID(), getTeam(), bool));
    }

    @Shadow
    protected boolean getSharedFlag(int p_20292_) {
        throw new IllegalStateException("Mixin failed to shadow getSharedFlag()");
    }

    @Shadow
    @Nullable
    public Team getTeam() {
        throw new IllegalStateException("Mixin failed to shadow getTeam()");
    }

    @Shadow
    public UUID getUUID() {
        throw new IllegalStateException("Mixin failed to shadow getUUID()");
    }
}