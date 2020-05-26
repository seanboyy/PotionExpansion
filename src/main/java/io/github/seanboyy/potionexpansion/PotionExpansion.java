package io.github.seanboyy.potionexpansion;

import io.github.seanboyy.potionexpansion.registers.ModItems;
import io.github.seanboyy.potionexpansion.util.SizeIncreaseBrewingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

@Mod("potionexpansion")
@Mod.EventBusSubscriber(modid = PotionExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PotionExpansion {
    public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "potionexpansion";

	public PotionExpansion() {
	    LOGGER.info("Starting up Potion Expansion");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onLoadComplete(final FMLLoadCompleteEvent event) {
	    if(!BrewingRecipeRegistry.addRecipe(new SizeIncreaseBrewingRecipe())) {
	        LOGGER.fatal("Unable to register size increasing potion recipes");
	        throw new RuntimeException();
        }
    }

    @SubscribeEvent
    public static void onRegisterPotions(final RegistryEvent.Register<Potion> event){
    }
}
