package io.github.seanboyy.potionexpansion.objects.tileentity;

import io.github.seanboyy.potionexpansion.PotionExpansion;
import io.github.seanboyy.potionexpansion.objects.blocks.PotionMixerBlock;
import io.github.seanboyy.potionexpansion.objects.containers.inventory.PotionMixerContainer;
import io.github.seanboyy.potionexpansion.registers.ModPotions;
import io.github.seanboyy.potionexpansion.registers.ModTileEntities;
import io.github.seanboyy.potionexpansion.util.ModPotionUtils;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
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
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PotionMixerTileEntity extends LockableTileEntity implements ISidedInventory, ITickableTileEntity {
    //FUEL = 0
    //INPUTS = 1, 2
    //BOTTLES = 3
    //OUTPUT = 4
    private static final int[] SLOTS_FOR_UP = new int[]{0, 3};
    private static final int[] INPUT_SLOTS = new int[]{1, 2};
    private static final int[] OUTPUT_SLOTS = new int[]{4};
    private int mixTime;
    private int fuel;
    private boolean[] filledSlots = new boolean[]{false, false};
    protected final IIntArray passableData = new IIntArray() {
        @Override
        public int get(int index) {
            switch(index) {
                case 0:
                    return PotionMixerTileEntity.this.mixTime;
                case 1:
                    return PotionMixerTileEntity.this.fuel;
                default:
                    return 0;
            }
        }

        @Override
        public void set(int index, int value) {
            switch(index) {
                case 0:
                    PotionMixerTileEntity.this.mixTime = value;
                    break;
                case 1:
                    PotionMixerTileEntity.this.fuel = value;
            }
        }

        @Override
        public int size() {
            return 2;
        }
    };

    private NonNullList<ItemStack> mixingItemStacks = NonNullList.withSize(5, ItemStack.EMPTY);
    public PotionMixerTileEntity() {
        super(ModTileEntities.POTION_MIXER.get());
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        if(side == Direction.UP) return SLOTS_FOR_UP;
        if(side == Direction.DOWN) return OUTPUT_SLOTS;
        else return INPUT_SLOTS;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
        return index == 4;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent(PotionExpansion.MOD_ID + ".container.potion_mixer");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new PotionMixerContainer(id, player, this, this.passableData);
    }

    @Override
    public int getSizeInventory() {
        return this.mixingItemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.mixingItemStacks) {
            if(!itemStack.isEmpty()) return false;
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return index >= 0 && index < this.mixingItemStacks.size() ? this.mixingItemStacks.get(index) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int index, int count) {
        return ItemStackHelper.getAndSplit(this.mixingItemStacks, index, count);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.mixingItemStacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        if(index >= 0 && index < this.mixingItemStacks.size()) this.mixingItemStacks.set(index, stack);
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity player) {
        assert this.world != null;
        if(this.world.getTileEntity(this.pos) != this) return false;
        else return !(player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) > 64D);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        if(index == 1 || index == 2) {
            return stack.getItem() == Items.POTION;
        } else if(index == 3) {
            return stack.getItem() == Items.GLASS_BOTTLE;
        } else {
                Item item = stack.getItem();
                if(index == 0) return item == Items.BLAZE_POWDER;
                else return false;
        }
    }

    @Override
    public void clear() {
        this.mixingItemStacks.clear();
    }

    @Override
    public void tick() {
        ItemStack fuelItem = this.mixingItemStacks.get(0);
        if(this.fuel <= 0 && fuelItem.getItem() == Items.BLAZE_POWDER) {
            this.fuel = 20;
            fuelItem.shrink(1);
            this.markDirty();
        }
        boolean flag = this.canMix();
        boolean flag1 = this.mixTime > 0;
        if(flag1) {
            --this.mixTime;
            boolean flag2 = this.mixTime == 0;
            if(flag2 && flag) {
                this.mixPotions();
                this.markDirty();
            } else if (!flag) {
                this.mixTime = 0;
                this.markDirty();
            }
        } else if(flag && this.fuel > 0) {
            --this.fuel;
            this.mixTime = 400;
            this.markDirty();
        }

        assert this.world != null;
        if(!this.world.isRemote) {
            boolean[] filledSlots = this.createFilledSlotsArray();
            if(!Arrays.equals(this.filledSlots, filledSlots)) {
                this.filledSlots = filledSlots;
                BlockState blockState = this.world.getBlockState(this.getPos());
                if(!(blockState.getBlock() instanceof PotionMixerBlock)) {
                    return;
                }
                for(int i = 0; i < PotionMixerBlock.HAS_BOTTLE.length; ++i) {
                    blockState = blockState.with(BrewingStandBlock.HAS_BOTTLE[i], filledSlots[i]);
                }
                this.world.setBlockState(this.pos, blockState, 2);
            }
        }
    }

    private boolean canMix() {
        for(int i = 1; i < 3; ++i) {
            ItemStack itemStack1 = this.mixingItemStacks.get(i);
            if(itemStack1.isEmpty() || !ModPotionUtils.itemHasPotionEffect(itemStack1)) return false;
        }
        ItemStack itemStack2 = this.mixingItemStacks.get(4);
        if(!itemStack2.isEmpty() || itemStack2.getCount() > 0) return false;
        ItemStack itemStack = this.mixingItemStacks.get(3);
        if(itemStack.isEmpty() || itemStack.getCount() < 1) return false;
        else if(itemStack.getItem() == Items.GLASS_BOTTLE) return true;
        return this.mixingItemStacks.get(4).isEmpty() || itemStack.getCount() < 1;
    }

    private void mixPotions() {
        ItemStack bottles = this.mixingItemStacks.get(3);
        ItemStack mix1 = this.mixingItemStacks.get(1);
        ItemStack mix2 = this.mixingItemStacks.get(2);
        bottles.shrink(1);
        if(mix1.getTag() == null) return;
        if(mix2.getTag() == null) return;
        CompoundNBT mix1Tag = mix1.getTag();
        CompoundNBT mix2Tag = mix2.getTag();
        ItemStack output = new ItemStack(Items.POTION);
        PotionUtils.addPotionToItemStack(output, ModPotions.MULTI_EFFECT.get());
        CompoundNBT appendedTag = output.getTag();
        assert appendedTag != null;
        appendedTag.put("CustomPotionEffects", ModPotionUtils.createAndMergeCustomPotionEffects(mix1Tag, mix2Tag));
        output.setTag(appendedTag);
        BlockPos blockPos = this.getPos();
        this.mixingItemStacks.set(1, new ItemStack(Items.GLASS_BOTTLE));
        this.mixingItemStacks.set(2, new ItemStack(Items.GLASS_BOTTLE));
        this.mixingItemStacks.set(4, output);
        assert this.world != null;
        this.world.playEvent(Constants.WorldEvents.BREWING_STAND_BREW_SOUND, blockPos, 0);
    }

    public boolean[] createFilledSlotsArray() {
        boolean[] booleans = new boolean[2];
        for(int i = 1; i < 3; ++i) {
            if(!this.mixingItemStacks.get(i).isEmpty()) {
                booleans[i - 1] = true;
            }
        }
        return booleans;
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.mixingItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.mixingItemStacks);
        this.mixTime = compound.getShort("MixTime");
        this.fuel = compound.getByte("Fuel");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.putShort("MixTime", (short)this.mixTime);
        ItemStackHelper.saveAllItems(compound, this.mixingItemStacks);
        compound.putByte("Fuel", (byte)this.fuel);
        return compound;
    }

    LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Nullable
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if(!this.removed && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if(side == Direction.UP) return handlers[0].cast();
            else if(side == Direction.DOWN) return handlers[0].cast();
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
