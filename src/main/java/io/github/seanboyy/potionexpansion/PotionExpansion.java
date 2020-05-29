package io.github.seanboyy.potionexpansion;

import io.github.seanboyy.potionexpansion.registers.ModBlocks;
import io.github.seanboyy.potionexpansion.registers.ModContainers;
import io.github.seanboyy.potionexpansion.registers.ModItems;
import io.github.seanboyy.potionexpansion.registers.ModTileEntities;
import io.github.seanboyy.potionexpansion.util.ModBrewingRecipeRegistry;
import io.github.seanboyy.potionexpansion.util.MultiEffectBrewingRecipe;
import io.github.seanboyy.potionexpansion.util.SizeIncreaseBrewingRecipe;
import io.github.seanboyy.potionexpansion.util.VanillaBrewingRecipe;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Mod("potionexpansion")
@Mod.EventBusSubscriber(modid = PotionExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class PotionExpansion {
    public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "potionexpansion";

	public PotionExpansion() {
	    LOGGER.info("Starting up Potion Expansion");
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.OVERRIDE_BLOCKS.register(modEventBus);
        ModTileEntities.TILE_ENTITIES.register(modEventBus);
        ModContainers.CONTAINERS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
           final Item.Properties properties = new Item.Properties().group(ItemGroup.BREWING);
           final BlockItem blockItem = new BlockItem(block, properties);
           blockItem.setRegistryName(block.getRegistryName());
           registry.register(blockItem);
        });
        ModBlocks.OVERRIDE_BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            final Item.Properties properties = new Item.Properties().group(ItemGroup.BREWING);
            final BlockItem blockItem = new BlockItem(block, properties);
            blockItem.setRegistryName(block.getRegistryName());
            registry.register(blockItem);
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLoadComplete(final FMLLoadCompleteEvent event) {
	    if(!ModBrewingRecipeRegistry.addRecipe(new SizeIncreaseBrewingRecipe())) {
	        LOGGER.fatal("Unable to register size increasing potion recipes");
	        throw new RuntimeException();
        }
	    if(!ModBrewingRecipeRegistry.addRecipe(new MultiEffectBrewingRecipe())) {
	        LOGGER.fatal("Unable to register multi effect potion recipes");
	        throw new RuntimeException();
        }
	    if(!ModBrewingRecipeRegistry.addRecipe(new VanillaBrewingRecipe())) {
	        LOGGER.fatal("Unable to register vanilla potion recipes");
	        throw new RuntimeException();
        }
        List<IBrewingRecipe> otherRecipes = BrewingRecipeRegistry.getRecipes();
	    for(int i = 1; i < otherRecipes.size(); ++i) {
            if(!ModBrewingRecipeRegistry.addRecipe(otherRecipes.get(i))) {
                LOGGER.fatal("Unable to register modded potion recipes");
                throw new RuntimeException();
            }
        }
    }
}
