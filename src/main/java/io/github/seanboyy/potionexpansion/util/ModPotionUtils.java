package io.github.seanboyy.potionexpansion.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;

@ParametersAreNonnullByDefault
public class ModPotionUtils {
    public static boolean itemHasPotionEffect(ItemStack itemStack) {
        if(itemStack.isEmpty()) return false;
        if(!itemStack.hasTag()) return false;
        if(!(itemStack.getItem() instanceof PotionItem)) return false;
        CompoundNBT stackTag = itemStack.getTag();
        if(stackTag == null) return false;
        if(!stackTag.contains("Potion")) return false;
        String potionId = stackTag.getString("Potion");
        Potion potion = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(potionId));
        if(potion == null) return false;
        return stackTag.contains("CustomPotionEffects")
                || !potion.getEffects().isEmpty();
    }

    public static ListNBT createAndMergeCustomPotionEffects(CompoundNBT compound1, CompoundNBT compound2) {
        List<EffectInstance> effects = Lists.newArrayList();
        //grab effects from input 1
        if(compound1.contains("CustomPotionEffects")) {
            compound1.getList("CustomPotionEffects", Constants.NBT.TAG_COMPOUND).stream()
                    .filter(tag -> tag instanceof CompoundNBT)
                    .forEach(tag -> {
                        CompoundNBT compoundTag = (CompoundNBT)tag;
                        effects.add(EffectInstance.read(compoundTag));
                    });
        }
        //grab effects from input 2
        if(compound2.contains("CustomPotionEffects")) {
            compound2.getList("CustomPotionEffects", Constants.NBT.TAG_COMPOUND).stream()
                    .filter(tag -> tag instanceof CompoundNBT)
                    .forEach(tag -> {
                        CompoundNBT compoundTag = (CompoundNBT)tag;
                        effects.add(EffectInstance.read(compoundTag));
                    });
        }
        Potion potion1 = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(compound1.getString("Potion")));
        Potion potion2 = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(compound2.getString("Potion")));
        //grab effects written in bottle 1
        if(potion1 != null && !potion1.getEffects().isEmpty()) {
            effects.addAll(potion1.getEffects());
        }
        //grab effects written in bottle 2
        if(potion2 != null && !potion2.getEffects().isEmpty()) {
            effects.addAll(potion2.getEffects());
        }
        //pare down list to make sure all effects are unique
        Map<Effect, Pair<Integer, Integer>> uniqueEffectsInList = Maps.newHashMap();
        for (EffectInstance effect : effects) {
            Effect effect1 = effect.getPotion();
            int level = effect.getAmplifier();
            int duration = effect.getDuration();
            if(uniqueEffectsInList.get(effect1) == null) uniqueEffectsInList.put(effect1, Pair.of(duration, level));
            else {
                int oldDuration = uniqueEffectsInList.get(effect1).getLeft();
                int oldLevel = uniqueEffectsInList.get(effect1).getRight();
                if(level != oldLevel || duration != oldDuration) {
                    EffectInstance oldInstance = new EffectInstance(effect1, oldDuration, oldLevel);
                    EffectInstance newInstance = new EffectInstance(effect1, duration, level);
                    newInstance.combine(oldInstance);
                    uniqueEffectsInList.put(effect1, Pair.of(newInstance.getDuration(), newInstance.getAmplifier()));
                }
            }
        }
        List<CompoundNBT> effects2 = Lists.newArrayList();
        uniqueEffectsInList.forEach((effect, modifiers) -> {
            EffectInstance effectInstance = new EffectInstance(effect, modifiers.getLeft(), modifiers.getRight());
            effects2.add(effectInstance.write(new CompoundNBT()));
        });
        ListNBT finalEffectList = new ListNBT();
        finalEffectList.addAll(effects2);
        return finalEffectList;
    }
}
