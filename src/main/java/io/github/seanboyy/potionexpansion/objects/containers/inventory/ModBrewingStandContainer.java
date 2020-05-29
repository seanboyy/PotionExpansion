package io.github.seanboyy.potionexpansion.objects.containers.inventory;

import io.github.seanboyy.potionexpansion.registers.ModContainers;
import io.github.seanboyy.potionexpansion.util.ModBrewingRecipeRegistry;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.ForgeEventFactory;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModBrewingStandContainer extends Container {
    private final IInventory tileBrewingStand;
    private final IIntArray d;
    private final Slot slot;

    public ModBrewingStandContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new Inventory(5), new IntArray(2));
    }

    public ModBrewingStandContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray intArray) {
        super(ModContainers.BREWING_STAND_CONTAINER.get(), id);
        assertInventorySize(inventory, 5);
        assertIntArraySize(intArray, 2);
        this.tileBrewingStand = inventory;
        this.d = intArray;
        this.addSlot(new ModBrewingStandContainer.PotionSlot(inventory, 0, 56, 51));
        this.addSlot(new ModBrewingStandContainer.PotionSlot(inventory, 1, 79, 58));
        this.addSlot(new ModBrewingStandContainer.PotionSlot(inventory, 2, 102, 51));
        this.slot = this.addSlot(new ModBrewingStandContainer.IngredientSlot(inventory, 3, 79, 17));
        this.addSlot(new ModBrewingStandContainer.FuelSlot(inventory, 4, 17, 17));
        this.trackIntArray(intArray);
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    public ModBrewingStandContainer(int id, PlayerInventory playerInventory, PacketBuffer packetBuffer) {
        this(id, playerInventory);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            if((index < 0 || index > 2) && index != 3 && index != 4) {
                if(ModBrewingStandContainer.FuelSlot.isValidBrewingFuel(itemStack)) {
                    if(this.mergeItemStack(itemStack1, 4, 5, false) || this.slot.isItemValid(itemStack1) && !this.mergeItemStack(itemStack1, 3, 4, false)) return ItemStack.EMPTY;
                } else if(this.slot.isItemValid(itemStack1)) {
                    if(!this.mergeItemStack(itemStack1, 3, 4, false)) return ItemStack.EMPTY;
                } else if(ModBrewingStandContainer.PotionSlot.canHoldPotion(itemStack) && itemStack.getCount() == 1) {
                    if(!this.mergeItemStack(itemStack1, 0, 3, false)) return ItemStack.EMPTY;
                } else if(index >= 5 && index < 32) {
                    if(!this.mergeItemStack(itemStack1, 32, 41, false)) return ItemStack.EMPTY;
                } else if(index >= 32 && index < 41) {
                    if(!this.mergeItemStack(itemStack1, 5, 32, false)) return ItemStack.EMPTY;
                } else if (!this.mergeItemStack(itemStack1, 5, 41, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if(!this.mergeItemStack(itemStack1, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack1, itemStack);
            }
            if(itemStack1.isEmpty()) slot.putStack(ItemStack.EMPTY);
            else slot.onSlotChanged();
            if(itemStack.getCount() == itemStack.getCount()) return ItemStack.EMPTY;
            slot.onTake(playerIn, itemStack1);
        }
        return itemStack;
    }

    @OnlyIn(Dist.CLIENT)
    public int get1() {
        return this.d.get(1);
    }

    @OnlyIn(Dist.CLIENT)
    public int get0() {
        return this.d.get(0);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.tileBrewingStand.isUsableByPlayer(playerIn);
    }

    final static class FuelSlot extends Slot {
        public FuelSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return isValidBrewingFuel(stack);
        }

        public static boolean isValidBrewingFuel(ItemStack itemStack) {
            return itemStack.getItem() == Items.BLAZE_POWDER;
        }

        @Override
        public int getSlotStackLimit() {
            return 64;
        }
    }

    final static class IngredientSlot extends Slot {
        public IngredientSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return ModBrewingRecipeRegistry.isValidIngredient(stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 64;
        }
    }

    final static class PotionSlot extends Slot {
        public PotionSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return canHoldPotion(stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }

        @Override
        public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
            Potion potion = PotionUtils.getPotionFromItem(stack);
            if(thePlayer instanceof ServerPlayerEntity) {
                ForgeEventFactory.onPlayerBrewedPotion(thePlayer, stack);
                CriteriaTriggers.BREWED_POTION.trigger((ServerPlayerEntity)thePlayer, potion);
            }
            super.onTake(thePlayer, stack);
            return stack;
        }

        public static boolean canHoldPotion(ItemStack stack) {
            return ModBrewingRecipeRegistry.isValidInput(stack);
        }
    }
}
