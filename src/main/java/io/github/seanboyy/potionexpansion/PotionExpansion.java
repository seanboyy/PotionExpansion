package io.github.seanboyy.potionexpansion;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("potionexpansion")
@Mod.EventBusSubscriber(modid = PotionExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PotionExpansion {
    public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "potionexpansion";

	public PotionExpansion() {
	    LOGGER.info("Starting up Potion Expansion");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
