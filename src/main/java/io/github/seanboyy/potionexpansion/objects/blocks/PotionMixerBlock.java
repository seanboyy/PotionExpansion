package io.github.seanboyy.potionexpansion.objects.blocks;

import io.github.seanboyy.potionexpansion.objects.tileentity.PotionMixerTileEntity;
import io.github.seanboyy.potionexpansion.registers.ModTileEntities;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static io.github.seanboyy.potionexpansion.util.PotionMixerModels.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class PotionMixerBlock extends ContainerBlock {
    public static final DirectionProperty HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty[] HAS_BOTTLE = new BooleanProperty[]{BlockStateProperties.HAS_BOTTLE_0, BlockStateProperties.HAS_BOTTLE_1};

    public PotionMixerBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(HAS_BOTTLE[0], false).with(HAS_BOTTLE[1], false));
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return ModTileEntities.POTION_MIXER.get().create();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new PotionMixerTileEntity();
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(HORIZONTAL_FACING)) {
            case SOUTH:
                if(state.get(HAS_BOTTLE[0]) && state.get(HAS_BOTTLE[1])) return SHAPE_S_BOTH;
                else if(state.get(HAS_BOTTLE[0]) && !state.get(HAS_BOTTLE[1])) return SHAPE_S_0;
                else if(!state.get(HAS_BOTTLE[0]) && state.get(HAS_BOTTLE[1])) return SHAPE_S_1;
                else if(!state.get(HAS_BOTTLE[0]) && !state.get(HAS_BOTTLE[1])) return SHAPE_S_NONE;
            case EAST:
                if(state.get(HAS_BOTTLE[0]) && state.get(HAS_BOTTLE[1])) return SHAPE_E_BOTH;
                else if(state.get(HAS_BOTTLE[0]) && !state.get(HAS_BOTTLE[1])) return SHAPE_E_0;
                else if(!state.get(HAS_BOTTLE[0]) && state.get(HAS_BOTTLE[1])) return SHAPE_E_1;
                else if(!state.get(HAS_BOTTLE[0]) && !state.get(HAS_BOTTLE[1])) return SHAPE_E_NONE;
            case WEST:
                if(state.get(HAS_BOTTLE[0]) && state.get(HAS_BOTTLE[1])) return SHAPE_W_BOTH;
                else if(state.get(HAS_BOTTLE[0]) && !state.get(HAS_BOTTLE[1])) return SHAPE_W_0;
                else if(!state.get(HAS_BOTTLE[0]) && state.get(HAS_BOTTLE[1])) return SHAPE_W_1;
                else if(!state.get(HAS_BOTTLE[0]) && !state.get(HAS_BOTTLE[1])) return SHAPE_W_NONE;
            case NORTH:
                if(state.get(HAS_BOTTLE[0]) && state.get(HAS_BOTTLE[1])) return SHAPE_N_BOTH;
                else if(state.get(HAS_BOTTLE[0]) && !state.get(HAS_BOTTLE[1])) return SHAPE_N_0;
                else if(!state.get(HAS_BOTTLE[0]) && state.get(HAS_BOTTLE[1])) return SHAPE_N_1;
                else if(!state.get(HAS_BOTTLE[0]) && !state.get(HAS_BOTTLE[1])) return SHAPE_N_NONE;
            default:
                return SHAPE_N_NONE;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.with(HORIZONTAL_FACING, rot.rotate(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(HORIZONTAL_FACING)));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, HAS_BOTTLE[0], HAS_BOTTLE[1]);
    }



    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof PotionMixerTileEntity) {
                player.openContainer((PotionMixerTileEntity)tileEntity);
            }
        }
        return ActionResultType.SUCCESS;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof PotionMixerTileEntity) {
                InventoryHelper.dropInventoryItems(worldIn, pos, (PotionMixerTileEntity)tileEntity);
            }
            super.onReplaced(state, worldIn, pos, newState, isMoving);
        }
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}
