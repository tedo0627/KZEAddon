package mcpc.tedo0627.kzeaddon.forge;

import mcpc.tedo0627.kzeaddon.forge.service.DeleteMessageService;
import mcpc.tedo0627.kzeaddon.forge.service.HidePlayerService;
import mcpc.tedo0627.kzeaddon.forge.service.OpenScreenService;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KZEAddon.MOD_ID)
public class KZEAddon {

    public static final String MOD_ID = "kzeaddon";

    public KZEAddon() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::complete);
    }

    private void complete(FMLLoadCompleteEvent event) {
        CustomConfig config = new CustomConfig();
        MinecraftForge.EVENT_BUS.register(new DeleteMessageService(config));
        MinecraftForge.EVENT_BUS.register(new HidePlayerService());
        MinecraftForge.EVENT_BUS.register(new OpenScreenService(config));
    }
}
