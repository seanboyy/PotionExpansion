package io.github.seanboyy.potionexpansion.util;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.common.util.Constants;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultiEffectBrewingRecipe implements IBrewingRecipe {

    @Override
    public boolean isInput(ItemStack input) {
        CompoundNBT tag = input.getTag();
        if(tag == null) return false;
        return tag.contains("CustomPotionEffects") && !tag.getList("CustomPotionEffects", Constants.NBT.TAG_COMPOUND).isEmpty();
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        Item item = ingredient.getItem();
        return item == Items.GUNPOWDER || item == Items.DRAGON_BREATH;
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if(!input.isEmpty() && !ingredient.isEmpty() && isIngredient(ingredient)) {
            Item ingredientItem = ingredient.getItem();
            CompoundNBT newTag = input.getTag();
            ItemStack newStack;
            if (ingredientItem == Items.GUNPOWDER) {
                if (input.getItem() == Items.POTION) {
                    newStack = new ItemStack(Items.SPLASH_POTION);
                    newStack.setTag(newTag);
                    newStack.setDisplayName(new TranslationTextComponent("potionexpansion.splash").appendText(" ").appendSibling(input.getDisplayName()));
                    return newStack;
                }
            } else if (ingredientItem == Items.DRAGON_BREATH) {
                if (input.getItem() == Items.SPLASH_POTION) {
                    newStack = new ItemStack(Items.LINGERING_POTION);
                    newStack.setTag(newTag);
                    newStack.setDisplayName(new TranslationTextComponent("potionexpansion.lingering").appendText(" ").appendSibling(input.getDisplayName()));
                    return newStack;
                }
            }
        }
        return ItemStack.EMPTY;
    }
}
