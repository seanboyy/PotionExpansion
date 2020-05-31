package io.github.seanboyy.potionexpansion.util;

import io.github.seanboyy.potionexpansion.PotionExpansion;
import io.github.seanboyy.potionexpansion.client.gui.ModBrewingStandScreen;
import io.github.seanboyy.potionexpansion.client.gui.PotionMixerScreen;
import io.github.seanboyy.potionexpansion.registers.ModBlocks;
import io.github.seanboyy.potionexpansion.registers.ModContainers;
import io.github.seanboyy.potionexpansion.registers.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(modid = PotionExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ModContainers.BREWING_STAND_CONTAINER.get(), ModBrewingStandScreen::new);
        ScreenManager.registerFactory(ModContainers.POTION_MIXER_CONTAINER.get(), PotionMixerScreen::new);
        RenderTypeLookup.setRenderLayer(ModBlocks.POTION_MIXER.get(), RenderType.getCutout());
    }

    @SubscribeEvent
    public static void onLoadComplete(final FMLLoadCompleteEvent event) {
        Minecraft.getInstance().getItemColors().register((itemStack, color) -> color > 0 ? -1 : PotionUtils.getColor(itemStack), ModItems.MULTI_USE_POTION_2.get(), ModItems.MULTI_USE_POTION_3.get(), ModItems.MULTI_USE_POTION_4.get());
    }
}
