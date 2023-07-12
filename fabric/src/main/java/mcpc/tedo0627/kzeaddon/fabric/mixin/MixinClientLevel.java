package mcpc.tedo0627.kzeaddon.fabric.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.LevelEntityGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientLevel.class)
public abstract class MixinClientLevel {

    @Inject(method = "entitiesForRendering", at = @At("HEAD"), cancellable = true)
    private void getEntities(CallbackInfoReturnable<Iterable<Entity>> cir) {
        List<Entity> list = new ArrayList<>();
        for (Entity entity : getEntities().getAll()) list.add(entity);

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        list.sort((o1, o2) -> {
            double d1 = o1.distanceToSqr(player);
            double d2 = o2.distanceToSqr(player);
            return Double.compare(d2, d1);
        });
        cir.setReturnValue(list);
    }

    @Shadow
    protected abstract LevelEntityGetter<Entity> getEntities();
}