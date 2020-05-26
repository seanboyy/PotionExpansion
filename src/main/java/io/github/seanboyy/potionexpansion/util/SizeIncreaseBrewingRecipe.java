package io.github.seanboyy.potionexpansion.util;

import io.github.seanboyy.potionexpansion.objects.items.MultiUsePotionItem;
import io.github.seanboyy.potionexpansion.registers.ModItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class SizeIncreaseBrewingRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input) {
        Item item = input.getItem();
        return item == ModItems.MULTI_USE_POTION_2.get() || item == ModItems.MULTI_USE_POTION_3.get() || item == ModItems.MULTI_USE_POTION_4.get();
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return ingredient.getItem() == Items.DIAMOND;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if(!input.isEmpty() && !ingredient.isEmpty() && isIngredient(ingredient)) {
            if(input.getItem() instanceof MultiUsePotionItem) {
                MultiUsePotionItem currentSize = ((MultiUsePotionItem) input.getItem());
                Potion potionOnStack = PotionUtils.getPotionFromItem(input);
                switch(currentSize.getUses()){
                    case 2:
                        return PotionUtils.addPotionToItemStack(new ItemStack(ModItems.MULTI_USE_POTION_3.get()), potionOnStack);
                    case 3:
                        return PotionUtils.addPotionToItemStack(new ItemStack(ModItems.MULTI_USE_POTION_4.get()), potionOnStack);
                }
            } else if(input.getItem() instanceof PotionItem) {
                Potion potionOnStack = PotionUtils.getPotionFromItem(input);
                return PotionUtils.addPotionToItemStack(new ItemStack(ModItems.MULTI_USE_POTION_2.get()), potionOnStack);
            }
        }
        return ItemStack.EMPTY;
    }
}