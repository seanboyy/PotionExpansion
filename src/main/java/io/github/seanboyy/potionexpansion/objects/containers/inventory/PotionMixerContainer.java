package io.github.seanboyy.potionexpansion.objects.containers.inventory;

import io.github.seanboyy.potionexpansion.registers.ModContainers;
import io.github.seanboyy.potionexpansion.util.ModPotionUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PotionMixerContainer extends Container {
    private final IInventory tilePotionMixer;
    private final IIntArray data;

    public PotionMixerContainer(final int windowId, final PlayerInventory playerInventoryIn) {
        this(windowId, playerInventoryIn, new Inventory(5), new IntArray(2));
    }

    public PotionMixerContainer(final int id, final PlayerInventory playerInventory, final IInventory inventory, final IIntArray intArray) {
        super(ModContainers.POTION_MIXER_CONTAINER.get(), id);
        assertInventorySize(inventory,5);
        assertIntArraySize(intArray, 2);
        this.tilePotionMixer = inventory;
        this.data = intArray;
        this.addSlot(new FuelSlot(inventory, 0, 36, 41));
        this.addSlot(new MixSlot(inventory, 1, 93, 17));
        this.addSlot(new MixSlot(inventory, 2, 121, 17));
        this.addSlot(new BottleSlot(inventory, 3, 36, 17));
        this.addSlot(new OutputSlot(inventory, 4, 107, 56));
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

    public PotionMixerContainer(final int id, final PlayerInventory playerInventory, final PacketBuffer packetBuffer) {
        this(id, playerInventory);
    }

    @OnlyIn(Dist.CLIENT)
    public int getMixTime() {
        return this.data.get(0);
    }

    @OnlyIn(Dist.CLIENT)
    public int getFuel() {
        return this.data.get(1);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.tilePotionMixer.isUsableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemStack = itemStack1.copy();
            if(index < 5) {
                //merge from container
                if(!this.mergeItemStack(itemStack1, 5, this.inventorySlots.size(), true)) return ItemStack.EMPTY;
            } else {
                //merge fuel
                if(FuelSlot.isValidBrewingFuel(itemStack)){
                    if(!this.mergeItemStack(itemStack1, 0, 1, false)) return ItemStack.EMPTY;
                }
                //merge bottle
                else if(BottleSlot.isItemBottle(itemStack)) {
                    if(!this.mergeItemStack(itemStack1, 3, 4, false)) return ItemStack.EMPTY;
                }
                //merge potions
                else if(ModPotionUtils.itemHasPotionEffect(itemStack)) {
                    if(!this.mergeItemStack(itemStack1, 1, 3, false)) return ItemStack.EMPTY;
                }
            }
        }
        return itemStack;
    }

    static class FuelSlot extends Slot {
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

    static class MixSlot extends Slot {
        public MixSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return ModPotionUtils.itemHasPotionEffect(stack);
        }

        @Override
        public int getSlotStackLimit() {
            return 1;
        }
    }

    static class OutputSlot extends MixSlot {
        public OutputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return false;
        }
    }

    static class BottleSlot extends Slot {
        public BottleSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean isItemValid(ItemStack stack) {
            return isItemBottle(stack);
        }

        public static boolean isItemBottle(ItemStack itemStack) {
            return itemStack.getItem() == Items.GLASS_BOTTLE;
        }

        @Override
        public int getSlotStackLimit() {
            return 64;
        }
    }
}
