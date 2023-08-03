package mcpc.tedo0627.kzeaddon.fabric.mixin;

import mcpc.tedo0627.kzeaddon.fabric.event.InventoryUpdateCallback;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Inventory.class)
public abstract class MixinPlayerInventory {

    @Inject(method = "setItem", at = @At("HEAD"), cancellable = true)
    private void setItem(int slot, ItemStack itemStack, CallbackInfo ci) {
        if (!InventoryUpdateCallback.EVENT.invoker().callback(slot, itemStack)) ci.cancel();
    }
}
