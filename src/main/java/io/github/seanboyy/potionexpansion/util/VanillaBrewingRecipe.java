package io.github.seanboyy.potionexpansion.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionBrewing;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class VanillaBrewingRecipe implements IBrewingRecipe {
    @Override
    public boolean isInput(ItemStack input) {
        Item item = input.getItem();
        CompoundNBT tag = input.getTag();
        if(tag == null) return false;
        if(tag.contains("CustomPotionEffects")) return false;
        return item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE;
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return PotionBrewing.isReagent(ingredient);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if(isInput(input) && !input.isEmpty() && !ingredient.isEmpty() && isIngredient(ingredient)) {
            ItemStack result = PotionBrewing.doReaction(ingredient, input);
            if(result != input) return result;
            return ItemStack.EMPTY;
        }
        return ItemStack.EMPTY;
    }
}
