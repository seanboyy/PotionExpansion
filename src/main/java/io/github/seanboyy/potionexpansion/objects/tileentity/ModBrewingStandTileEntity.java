package io.github.seanboyy.potionexpansion.objects.tileentity;

import io.github.seanboyy.potionexpansion.objects.blocks.ModBrewingStandBlock;
import io.github.seanboyy.potionexpansion.objects.containers.inventory.ModBrewingStandContainer;
import io.github.seanboyy.potionexpansion.registers.ModTileEntities;
import io.github.seanboyy.potionexpansion.util.ModBrewingRecipeRegistry;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModBrewingStandTileEntity extends LockableTileEntity implements ISidedInventory, ITickableTileEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{0, 1, 2, 4};
    private static final int[] OUTPUT_SLOTS = new int[]{0, 1, 2, 4};
    private NonNullList<ItemStack> brewingItemStacks = NonNullList.withSize(5, ItemStack.EMPTY);
    private int brewTime;
    private boolean[] filledSlots;
    private Item ingredientID;
    private int fuel;
    protected final IIntArray a = new IIntArray() {
        @Override
        public int get(int index) {
            switch(index) {
                case 0:
                    return ModBrewingStandTileEntity.this.brewTime;
                case 1:
                    return ModBrewingStandTileEntity.this.fuel;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch(index) {
                case 0:
                    ModBrewingStandTileEntity.this.brewTime = value;
                    break;
                case 1:
                    ModBrewingStandTileEntity.this.fuel = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    public ModBrewingStandTileEntity() {
        super(ModTileEntities.BREWING_STAND_TILE_ENTITY.get());
    }

    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.brewing");
    }

    @Override
    public int getSizeInventory() {
        return this.brewingItemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.brewingItemStacks) {
            if(!itemStack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public void tick() {
        ItemStack itemStack = this.brewingItemStacks.get(4);
        if(this.fuel <= 0 && itemStack.getItem() == Items.BLAZE_POWDER) {
            this.fuel = 20;
            itemStack.shrink(1);
            this.markDirty();
        }
        boolean flag = this.canBrew();
        boolean flag1 = this.brewTime > 0;
        ItemStack itemStack1 = this.brewingItemStacks.get(0);
        if(flag1) {
            --this.brewTime;
            boolean flag2 = this.brewTime == 0;
            if(flag2 && flag) {
                this.brewPotions();
                this.markDirty();
            } else if(!flag) {
                this.brewTime = 0;
                this.markDirty();
            } else if(this.ingredientID != itemStack1.getItem()) {
                this.brewTime = 0;
                this.markDirty();
            }
        } else if(flag && this.fuel > 0) {
            --this.fuel;
            this.brewTime = 400;
            this.ingredientID = itemStack1.getItem();
            this.markDirty();
        }

        if(!this.world.isRemote) {
            boolean[] aBoolean = this.createFilledSlotsArray();
            if(!Arrays.equals(aBoolean, this.filledSlots)) {
                this.filledSlots = aBoolean;
                BlockState blockState = this.world.getBlockState(this.getPos());
                if(!(blockState.getBlock() instanceof ModBrewingStandBlock)) {
                    return;
                }
                for(int i = 0; i < ModBrewingStandBlock.HAS_BOTTLE.length; ++i) {
                    blockState = blockState.with(ModBrewingStandBlock.HAS_BOTTLE[i], aBoolean[i]);
                }
                this.world.setBlockState(this.pos, blockState, 2);
            }
        }
    }

    public boolean[] createFilledSlotsArray() {
        boolean[] aBoolean = new boolean[3];
        for(int i = 0; i < 3; ++i) {
            if(!this.brewingItemStacks.get(i).isEmpty()) {
                aBoolean[i] = true;
            }
        }
        return aBoolean;
    }

    private boolean canBrew() {
        ItemStack itemStack = this.brewingItemStacks.get(3);
        if(!itemStack.isEmpty()) return ModBrewingRecipeRegistry.canBrew(brewingItemStacks, itemStack, OUTPUT_SLOTS);
        if(itemStack.isEmpty()) return false;
        else if(!PotionBrewing.isReagent(itemStack)) return false;
        else {
            for(int i = 0; i < 3; ++i) {
                ItemStack itemStack1 = this.brewingItemStacks.get(i);
                if(!itemStack1.isEmpty() && PotionBrewing.hasConversions(itemStack1, itemStack)) return true;
            }
            return false;
        }
    }

    private void brewPotions() {
        if(ForgeEventFactory.onPotionAttemptBrew(brewingItemStacks)) return;
        ItemStack itemStack = this.brewingItemStacks.get(3);
        ModBrewingRecipeRegistry.brewPotions(brewingItemStacks, itemStack, OUTPUT_SLOTS);
        itemStack.shrink(1);
        ForgeEventFactory.onPotionBrewed(brewingItemStacks);
        BlockPos blockPos = this.getPos();
        if(itemStack.hasContainerItem()) {
            ItemStack itemStack1 = itemStack.getContainerItem();
            if(itemStack.isEmpty()) itemStack = itemStack1;
            else if(!this.world.isRemote) InventoryHelper.spawnItemStack(this.world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack1);
        }
        this.brewingItemStacks.set(3, itemStack);
        this.world.playEvent(Constants.WorldEvents.BREWING_STAND_BREW_SOUND, blockPos, 0);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.brewingItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.brewingItemStacks);
        this.brewTime = compound.getShort("BrewTime");
        this.fuel = compound.getByte("Fuel");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putShort("BrewTime", (short)this.brewTime);
        ItemStackHelper.saveAllItems(compound, this.brewingItemStacks);
        compound.putByte("Fuel", (byte)this.fuel);
        return compound;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < this.brewingItemStacks.size() ? this.brewingItemStacks.get(index) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.brewingItemStacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.brewingItemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if(index >= 0 && index < this.brewingItemStacks.size()) {
            this.brewingItemStacks.set(index, stack);
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        if(this.world.getTileEntity(this.pos) != this) return false;
        else return !(player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) > 64D);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if(index == 3) {
            return ModBrewingRecipeRegistry.isValidIngredient(stack);
        } else {
            Item item = stack.getItem();
            if(index == 4) {
                return item == Items.BLAZE_POWDER;
            } else {
                return ModBrewingRecipeRegistry.isValidInput(stack) && this.getStackInSlot(index).isEmpty();
            }
        }
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if(side == Direction.UP) return SLOTS_FOR_UP;
        else return side == Direction.DOWN ? SLOTS_FOR_DOWN : OUTPUT_SLOTS;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        if(index == 3) return stack.getItem() == Items.GLASS_BOTTLE;
        else return true;
    }

    @Override
    public void clear() {
        this.brewingItemStacks.clear();
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new ModBrewingStandContainer(id, player, this, this.a);
    }

    LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if(!this.removed && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(side == Direction.UP) return handlers[0].cast();
            else if(side == Direction.DOWN) return handlers[1].cast();
            else return handlers[2].cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void remove() {
        super.remove();
        for (LazyOptional<? extends IItemHandler> handler : handlers) {
            handler.invalidate();
        }
    }
}
