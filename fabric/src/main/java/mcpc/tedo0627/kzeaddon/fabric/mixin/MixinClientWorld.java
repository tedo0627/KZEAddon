package mcpc.tedo0627.kzeaddon.fabric.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.world.entity.EntityLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ClientWorld.class)
public class MixinClientWorld {

    @Inject(at = @At("HEAD"), method = "getEntities", cancellable = true)
    private void getEntities(CallbackInfoReturnable<Iterable<Entity>> cir) {
        List<Entity> list = new ArrayList<>();
        for (Entity entity : getEntityLookup().iterate()) list.add(entity);

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        list.sort((o1, o2) -> {
            double d1 = o1.squaredDistanceTo(player);
            double d2 = o2.squaredDistanceTo(player);
            return Double.compare(d2, d1);
        });
        cir.setReturnValue(list);
    }

    @Shadow
    protected EntityLookup<Entity> getEntityLookup() {
        throw new IllegalStateException("Mixin failed to shadow getEntityLookup()");
    }
}