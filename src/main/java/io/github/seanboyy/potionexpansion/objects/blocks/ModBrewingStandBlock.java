package io.github.seanboyy.potionexpansion.objects.blocks;

import io.github.seanboyy.potionexpansion.objects.tileentity.ModBrewingStandTileEntity;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ModBrewingStandBlock extends BrewingStandBlock {

    public ModBrewingStandBlock(Properties properties) {
        super(properties);
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new ModBrewingStandTileEntity();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if(!worldIn.isRemote) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof ModBrewingStandTileEntity) {
                player.openContainer((ModBrewingStandTileEntity)tileEntity);
                player.addStat(Stats.INTERACT_WITH_BREWINGSTAND);
            }
        }
        return ActionResultType.SUCCESS;
    }
}
