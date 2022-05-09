package mcpc.tedo0627.kzeaddon.fabric.mixin;

import mcpc.tedo0627.kzeaddon.fabric.HidePlayerService;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.AbstractTeam;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(at = @At("HEAD"), method = "isInvisible", cancellable = true)
    private void isInvisible(CallbackInfoReturnable<Boolean> cir) {
        boolean bool = this.getFlag(5);
        cir.setReturnValue(HidePlayerService.isInvisible(getUuid(), getScoreboardTeam(), bool));
    }

    @Shadow
    protected boolean getFlag(int index) {
        throw new IllegalStateException("Mixin failed to shadow getFlag()");
    }

    @Shadow
    @Nullable
    public AbstractTeam getScoreboardTeam() {
        throw new IllegalStateException("Mixin failed to shadow getScoreboardTeam()");
    }

    @Shadow
    public UUID getUuid() {
        throw new IllegalStateException("Mixin failed to shadow getUuid()");
    }
}
