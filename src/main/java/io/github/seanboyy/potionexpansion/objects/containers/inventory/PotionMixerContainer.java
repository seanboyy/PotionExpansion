package io.github.seanboyy.potionexpansion.objects.containers.inventory;

import io.github.seanboyy.potionexpansion.registers.ModBlocks;
import io.github.seanboyy.potionexpansion.registers.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;

public class PotionMixerContainer extends Container {
    private final IWorldPosCallable worldPosCallable;

    public PotionMixerContainer(final int windowId, final PlayerInventory playerInventoryIn, final IWorldPosCallable worldPosCallable) {
        super(ModContainers.MIXER_CONTAINER.get(), windowId);
        this.worldPosCallable = worldPosCallable;
    }

    public PotionMixerContainer(final int id, final PlayerInventory playerInventory, final PacketBuffer packetBuffer) {
        this(id, playerInventory, IWorldPosCallable.of(playerInventory.player.world, playerInventory.player.getPosition()));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.worldPosCallable, playerIn, ModBlocks.MIXER.get());
    }
}
