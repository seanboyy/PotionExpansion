package io.github.seanboyy.potionexpansion.registers;

import io.github.seanboyy.potionexpansion.PotionExpansion;
import io.github.seanboyy.potionexpansion.objects.items.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, PotionExpansion.MOD_ID);

    public static final RegistryObject<Item> MULTI_USE_POTION_4 = ITEMS.register("multi_use_potion_4", () -> new MultiUsePotionItem(new Item.Properties().group(ItemGroup.BREWING).maxStackSize(1), 4));
    public static final RegistryObject<Item> MULTI_USE_POTION_3 = ITEMS.register("multi_use_potion_3", () -> new MultiUsePotionItem(new Item.Properties().group(ItemGroup.BREWING).maxStackSize(1), 3));
    public static final RegistryObject<Item> MULTI_USE_POTION_2 = ITEMS.register("multi_use_potion_2", () -> new MultiUsePotionItem(new Item.Properties().group(ItemGroup.BREWING).maxStackSize(1), 2));

}
