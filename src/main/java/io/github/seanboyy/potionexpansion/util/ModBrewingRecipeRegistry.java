package io.github.seanboyy.potionexpansion.util;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.IBrewingRecipe;

import java.util.Collections;
import java.util.List;

public class ModBrewingRecipeRegistry {
    private static List<IBrewingRecipe> recipes = Lists.newArrayList();

    public static boolean addRecipe(Ingredient input, Ingredient ingredient, ItemStack output) {
        return addRecipe(new BrewingRecipe(input, ingredient, output));
    }

    public static boolean addRecipe(IBrewingRecipe recipe) {
        return recipes.add(recipe);
    }

    public static ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if(input.isEmpty() || input.getCount() != 1) return ItemStack.EMPTY;
        if(ingredient.isEmpty()) return ItemStack.EMPTY;
        for (IBrewingRecipe recipe : recipes) {
            ItemStack output = recipe.getOutput(input, ingredient);
            if(!output.isEmpty()) return output;
        }
        return ItemStack.EMPTY;
    }

    public static boolean hasOutput(ItemStack input, ItemStack ingredient) {
        return !getOutput(input, ingredient).isEmpty();
    }

    public static boolean canBrew(NonNullList<ItemStack> inputs, ItemStack ingredient, int[] inputIndexes) {
        if(ingredient.isEmpty()) return false;
        for (int i : inputIndexes) {
            if(hasOutput(inputs.get(i), ingredient)) return true;
        }
        return false;
    }

    public static void brewPotions(NonNullList<ItemStack> inputs, ItemStack ingredient, int[] inputIndexes) {
        for (int i : inputIndexes) {
            ItemStack output = getOutput(inputs.get(i), ingredient);
            if(!output.isEmpty()) inputs.set(i, output);
        }
    }

    public static boolean isValidIngredient(ItemStack stack) {
        if(stack.isEmpty()) return false;
        for(IBrewingRecipe recipe : recipes) {
            if(recipe.isIngredient(stack)) return true;
        }
        return false;
    }

    public static boolean isValidInput(ItemStack stack) {
        if(stack.getCount() != 1) return false;
        for (IBrewingRecipe recipe : recipes) {
            if(recipe.isInput(stack)) return true;
        }
        return false;
    }

    public static List<IBrewingRecipe> getRecipes() {
        return Collections.unmodifiableList(recipes);
    }
}
