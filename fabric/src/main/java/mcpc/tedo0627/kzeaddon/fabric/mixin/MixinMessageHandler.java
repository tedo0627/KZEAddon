package mcpc.tedo0627.kzeaddon.fabric.mixin;

import mcpc.tedo0627.kzeaddon.fabric.service.KillLogService;
import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MessageHandler.class)
public abstract class MixinMessageHandler {

    @Inject(method = "onGameMessage", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;addMessage(Lnet/minecraft/text/Text;)V"), cancellable = true)
    private void onGameMessage(Text text, boolean bl, CallbackInfo ci) {
        if (!KillLogService.receiveChat(text)) ci.cancel();
    }
}
