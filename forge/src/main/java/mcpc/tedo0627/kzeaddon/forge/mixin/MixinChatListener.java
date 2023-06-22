package mcpc.tedo0627.kzeaddon.forge.mixin;

import mcpc.tedo0627.kzeaddon.forge.service.KillLogService;
import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatListener.class)
public abstract class MixinChatListener {

    @Inject(method = "handleSystemMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/ChatComponent;addMessage(Lnet/minecraft/network/chat/Component;)V"), cancellable = true)
    private void handleSystemMessage(Component p_240522_, boolean p_240642_, CallbackInfo ci) {
        if (!KillLogService.receiveChat(p_240522_)) ci.cancel();
    }
}
