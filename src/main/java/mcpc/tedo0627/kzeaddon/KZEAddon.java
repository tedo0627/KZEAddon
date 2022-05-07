package mcpc.tedo0627.kzeaddon;

import com.mojang.blaze3d.platform.InputConstants;
import mcpc.tedo0627.kzeaddon.service.HidePlayerService;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fmlclient.registry.ClientRegistry;

@Mod(KZEAddon.MOD_ID)
public class KZEAddon {

    public static final String MOD_ID = "kzeaddon";

    private CustomConfig config;

    private KeyMapping settingKey = new KeyMapping("Open KZEAddon settings key", InputConstants.Type.KEYSYM, -1, "KZEAddon");
    private KeyMapping hidePlayerKey = new KeyMapping("Hide player toggle key", InputConstants.Type.KEYSYM, -1, "KZEAddon");

    public KZEAddon() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::complete);
    }

    private void setup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new HidePlayerService(hidePlayerKey));
        //ClientRegistry.registerKeyBinding(settingKey);
        ClientRegistry.registerKeyBinding(hidePlayerKey);
    }

    private void complete(FMLLoadCompleteEvent event) {
        //config = new CustomConfig();
    }
}
