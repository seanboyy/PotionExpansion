package io.github.seanboyy.potionexpansion.utils;

import com.google.common.collect.Lists;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public final class ModPotionUtils {

    public static int getUsesLeftFromTag(@Nullable CompoundNBT tag) {
        if(tag == null) return 0;
        return tag.contains("UsesLeft", Constants.NBT.TAG_BYTE) ? tag.getByte("UsesLeft") : 0;
    }

    public static List<EffectInstance> getEffectsFromStack(ItemStack stack) {
        return getEffectsFromTag(stack.getTag());
    }


    public static List<EffectInstance> getEffectsFromTag(@Nullable CompoundNBT tag) {
        List<EffectInstance> effects = Lists.newArrayList();
        if(tag == null) return effects;
        ListNBT effectsOnTag = tag.contains("MultiEffects", Constants.NBT.TAG_COMPOUND) ? tag.getList("MultiEffects", Constants.NBT.TAG_COMPOUND) : null;
        if (effectsOnTag == null || effectsOnTag.isEmpty()) return effects;
        effectsOnTag.stream().filter(tag1 -> tag1 instanceof CompoundNBT).forEach(tag1 -> {
            if(parseTag((CompoundNBT)tag1) != null) effects.add(parseTag((CompoundNBT)tag1));
        });
        return effects;
    }

    @Nullable
    public static EffectInstance parseTag(@Nullable CompoundNBT tag) {
        if(tag == null) return null;
        Effect effect;
        int duration  = 0;
        if(tag.contains("effect")) {
            effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(tag.getString("effect")));
        }
        else return null;
        if(effect == null) return null;
        if(tag.contains("duration")) {
            duration = tag.getInt("duration");
        }
        return new EffectInstance(effect, duration);
    }

    public static void addPotionTooltip(ItemStack stack, List<ITextComponent> tooltip, float durationFactor) {
        List<EffectInstance> effectInstances = getEffectsFromStack(stack);
        List<Tuple<String, AttributeModifier>> list = Lists.newArrayList();
        if(effectInstances.isEmpty()) {
            tooltip.add((new TranslationTextComponent("effect.non")).applyTextStyle(TextFormatting.GRAY));
        } else {
            effectInstances.forEach(effectInstance -> {
                ITextComponent iTextComponent = new TranslationTextComponent(effectInstance.getEffectName());
                Effect effect = effectInstance.getPotion();
                Map<IAttribute, AttributeModifier> map = effect.getAttributeModifierMap();
                if(!map.isEmpty()) {
                    map.forEach((key, attributeModifier) -> {
                        AttributeModifier attributeModifier1 = new AttributeModifier(attributeModifier.getName(), effect.getAttributeModifierAmount(effectInstance.getAmplifier(), attributeModifier), attributeModifier.getOperation());
                        list.add(new Tuple<>(key.getName(), attributeModifier1));
                    });
                }
                if(effectInstance.getAmplifier() > 0) iTextComponent.appendText(" ").appendSibling(new TranslationTextComponent("potion.potency." + effectInstance.getAmplifier()));
                if(effectInstance.getDuration() > 20) iTextComponent.appendText(" (").appendText(EffectUtils.getPotionDurationString(effectInstance, durationFactor)).appendText(")");
                tooltip.add(iTextComponent.applyTextStyle(effect.getEffectType().getColor()));
            });
        }
        if(!list.isEmpty()) {
            tooltip.add(new StringTextComponent(""));
            tooltip.add(new TranslationTextComponent("potion.whenDrank").applyTextStyle(TextFormatting.DARK_PURPLE));
            list.forEach(tuple -> {
                AttributeModifier attributeModifier = tuple.getB();
                double d0 = attributeModifier.getAmount();
                double d1;
                if(attributeModifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributeModifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = attributeModifier.getAmount();
                } else d1 = attributeModifier.getAmount() * 100.0;
                if(d0 > 0.0) {
                    tooltip.add(new TranslationTextComponent("attribute.modifier.plus." + attributeModifier.getOperation().getId(), ItemStack.DECIMALFORMAT.format(d1), new TranslationTextComponent("attribute.name." + tuple.getA())).applyTextStyle(TextFormatting.BLUE));
                } else if(d0 < 0.0) {
                    d1 *= -1.0;
                    tooltip.add(new TranslationTextComponent("attribute.modifier.take." + attributeModifier.getOperation().getId(), ItemStack.DECIMALFORMAT.format(d1), new TranslationTextComponent("attribute.name." + tuple.getA())).applyTextStyle(TextFormatting.RED));
                }
            });
        }
    }
}
