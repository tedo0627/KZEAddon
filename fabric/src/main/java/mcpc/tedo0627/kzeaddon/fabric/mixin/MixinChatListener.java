package mcpc.tedo0627.kzeaddon.fabric.mixin;

import mcpc.tedo0627.kzeaddon.fabric.event.ChatReceiveCallback;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatListener.class)
public abstract class MixinChatListener {

    @Inject(method = "handleSystemMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;)V"), cancellable = true)
    private void onGameMessage(Component component, boolean bl, CallbackInfo ci) {
        if (!ChatReceiveCallback.EVENT.invoker().callback(component)) ci.cancel();
    }
}
