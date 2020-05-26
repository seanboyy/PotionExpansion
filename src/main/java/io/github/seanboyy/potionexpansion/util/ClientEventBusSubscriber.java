package io.github.seanboyy.potionexpansion.util;

import io.github.seanboyy.potionexpansion.PotionExpansion;
import io.github.seanboyy.potionexpansion.registers.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

@Mod.EventBusSubscriber(modid = PotionExpansion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onRegisterItems(final RegistryEvent.Register<Item> event){
    }

    @SubscribeEvent
    public static void onLoadComplete(final FMLLoadCompleteEvent event) {
        Minecraft.getInstance().getItemColors().register((itemStack, color) -> color > 0 ? -1 : PotionUtils.getColor(itemStack), ModItems.MULTI_USE_POTION_2.get(), ModItems.MULTI_USE_POTION_3.get(), ModItems.MULTI_USE_POTION_4.get());
    }
}
