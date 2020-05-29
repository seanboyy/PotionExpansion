package io.github.seanboyy.potionexpansion.objects.items;

import io.github.seanboyy.potionexpansion.registers.ModItems;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class MultiUsePotionItem extends PotionItem {
    private final int uses;

    public MultiUsePotionItem(Properties builder, int uses) {
        super(builder);
        this.uses = uses;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerEntity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;
        if(playerEntity instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }
        if(!worldIn.isRemote) {
            PotionUtils.getEffectsFromStack(stack).forEach(effectInstance -> {
                if(effectInstance.getPotion().isInstant()) effectInstance.getPotion().affectEntity(playerEntity, playerEntity, entityLiving, effectInstance.getAmplifier(), 1.0);
                else entityLiving.addPotionEffect(new EffectInstance(effectInstance));
            });
        }
        if(playerEntity != null) {
            if(!playerEntity.abilities.isCreativeMode) stack.shrink(1);
        }
        if(playerEntity == null || !playerEntity.abilities.isCreativeMode) {
            CompoundNBT newTag = stack.getTag();
            ItemStack newStack;
            if(stack.isEmpty()) {
                switch(uses) {
                    case 4:
                        newStack = new ItemStack(ModItems.MULTI_USE_POTION_3.get());
                        newStack.setTag(newTag);
                        return newStack;
                    case 3:
                        newStack = new ItemStack(ModItems.MULTI_USE_POTION_2.get());
                        newStack.setTag(newTag);
                        return newStack;
                    case 2:
                        newStack = new ItemStack(Items.POTION);
                        newStack.setTag(newTag);
                        return newStack;
                }
            }
        }
        return stack;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        ResourceLocation potionId = new ResourceLocation(stack.serializeNBT().getCompound("tag").getString("Potion"));
        Potion tempPotion = ForgeRegistries.POTION_TYPES.getValue(potionId);
        ResourceLocation dummyLocation = tempPotion.baseName != null ? new ResourceLocation(potionId.getNamespace(), tempPotion.baseName) : potionId;
        if(dummyLocation.getPath().equals("")) return "item.minecraft.potion.effect.empty";
        return "item.minecraft.potion.effect." + dummyLocation.getPath();
    }

    @Override
    public ITextComponent getDisplayName(ItemStack stack) {
        return new TranslationTextComponent("potionexpansion.large").appendText(" ").appendSibling(new TranslationTextComponent(getTranslationKey(stack)));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new StringTextComponent("Uses left: " + uses));
    }

    public int getUses() {
        return uses;
    }
}
