package io.github.seanboyy.potionexpansion.objects.items;

import io.github.seanboyy.potionexpansion.utils.ModPotionUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MultiEffectPotionItem extends Item {
    public MultiEffectPotionItem(Properties builder) {
        super(builder);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack getDefaultInstance() {
        return PotionUtils.addPotionToItemStack(super.getDefaultInstance(), Potions.WATER);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        PlayerEntity playerEntity = entityLiving instanceof PlayerEntity ? (PlayerEntity)entityLiving : null;
        if(playerEntity instanceof ServerPlayerEntity) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }
        if(!worldIn.isRemote) {
            ModPotionUtils.getEffectsFromStack(stack).forEach(effectInstance -> {
                if(effectInstance.getPotion().isInstant()) effectInstance.getPotion().affectEntity(playerEntity, playerEntity, entityLiving, effectInstance.getAmplifier(), 1.0);
                else entityLiving.addPotionEffect(new EffectInstance(effectInstance));
            });
        }

        if(playerEntity != null) {
            if(!playerEntity.abilities.isCreativeMode) stack.shrink(1);
        }
        if(playerEntity == null || !playerEntity.abilities.isCreativeMode) {
            if(stack.isEmpty()) return new ItemStack(Items.GLASS_BOTTLE);
            if(playerEntity != null) playerEntity.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
        }
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        playerIn.setActiveHand(handIn);
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        ModPotionUtils.addPotionTooltip(stack, tooltip, 1.0F);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return super.hasEffect(stack) || !ModPotionUtils.getEffectsFromStack(stack).isEmpty();
    }

    /*
    @Override
    public void fillItemGroup(ItemGroup group, NonNullLust<ItemStack> items) {
    TODO: Implement this
    }
     */
}
