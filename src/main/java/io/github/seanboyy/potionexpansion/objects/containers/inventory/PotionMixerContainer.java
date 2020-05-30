package io.github.seanboyy.potionexpansion.objects.containers.inventory;

import io.github.seanboyy.potionexpansion.registers.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
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
        //add slots
        /*
        TODO: Add slots
        this.addSlot(new Slot(inventory, 0, ?, ?));
        this.addSlot(new Slot(inventory, 1, ?, ?));
        this.addSlot(new Slot(inventory, 2, ?, ?));
        this.addSlot(new Slot(inventory, 3, ?, ?));
        this.addSlot(new Slot(inventory, 4, ?, ?));
         */
        this.trackIntArray(data);
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
}
