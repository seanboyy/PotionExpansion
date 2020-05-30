package io.github.seanboyy.potionexpansion.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import java.util.stream.Stream;

public class PotionMixerModels {
    public static final VoxelShape SHAPE_N_BOTH = Stream.of(
            Block.makeCuboidShape(7, 1, 4, 9, 1.5, 6),
            Block.makeCuboidShape(6.5, 1.5, 3.5, 9.5, 4.5, 6.5),
            Block.makeCuboidShape(7.5, 4.5, 4.5, 8.5, 5, 5.5),
            Block.makeCuboidShape(7, 5, 4, 9, 6, 6),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(2, 1, 12, 4, 6, 14),
            Block.makeCuboidShape(12, 1, 12, 14, 6, 14),
            Block.makeCuboidShape(4, 4, 12, 12, 6, 14),
            Block.makeCuboidShape(7.5, 1, 15, 8.5, 16, 16),
            Block.makeCuboidShape(2, 6, 12, 4, 6.5, 14),
            Block.makeCuboidShape(1.5, 6.5, 11.5, 4.5, 9.5, 14.5),
            Block.makeCuboidShape(2.5, 9.5, 12.5, 3.5, 10, 13.5),
            Block.makeCuboidShape(2, 10, 12, 4, 11, 14),
            Block.makeCuboidShape(2.5, 11, 12.5, 3.5, 13, 13.5),
            Block.makeCuboidShape(12.5, 11, 12.5, 13.5, 13, 13.5),
            Block.makeCuboidShape(7.5, 6, 4.5, 8.5, 14, 5.5),
            Block.makeCuboidShape(3.5, 13, 12.5, 12.5, 14, 13.5),
            Block.makeCuboidShape(7.5, 13, 13.5, 8.5, 14, 15),
            Block.makeCuboidShape(7.5, 13, 5.5, 8.5, 14, 12.5),
            Block.makeCuboidShape(12, 6, 12, 14, 6.5, 14),
            Block.makeCuboidShape(11.5, 6.5, 11.5, 14.5, 9.5, 14.5),
            Block.makeCuboidShape(12.5, 9.5, 12.5, 13.5, 10, 13.5),
            Block.makeCuboidShape(12, 10, 12, 14, 11, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_N_0 = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(7, 5, 4, 9, 6, 6),
            Block.makeCuboidShape(7, 1, 4, 9, 1.5, 6),
            Block.makeCuboidShape(6.5, 1.5, 3.5, 9.5, 4.5, 6.5),
            Block.makeCuboidShape(7.5, 4.5, 4.5, 8.5, 5, 5.5),
            Block.makeCuboidShape(2, 1, 12, 4, 6, 14),
            Block.makeCuboidShape(12, 1, 12, 14, 6, 14),
            Block.makeCuboidShape(4, 4, 12, 12, 6, 14),
            Block.makeCuboidShape(7.5, 1, 15, 8.5, 16, 16),
            Block.makeCuboidShape(2.5, 11, 12.5, 3.5, 13, 13.5),
            Block.makeCuboidShape(12.5, 11, 12.5, 13.5, 13, 13.5),
            Block.makeCuboidShape(7.5, 6, 4.5, 8.5, 14, 5.5),
            Block.makeCuboidShape(3.5, 13, 12.5, 12.5, 14, 13.5),
            Block.makeCuboidShape(7.5, 13, 13.5, 8.5, 14, 15),
            Block.makeCuboidShape(7.5, 13, 5.5, 8.5, 14, 12.5),
            Block.makeCuboidShape(12, 6, 12, 14, 6.5, 14),
            Block.makeCuboidShape(11.5, 6.5, 11.5, 14.5, 9.5, 14.5),
            Block.makeCuboidShape(12.5, 9.5, 12.5, 13.5, 10, 13.5),
            Block.makeCuboidShape(12, 10, 12, 14, 11, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_N_1 = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(7, 5, 4, 9, 6, 6),
            Block.makeCuboidShape(7, 1, 4, 9, 1.5, 6),
            Block.makeCuboidShape(6.5, 1.5, 3.5, 9.5, 4.5, 6.5),
            Block.makeCuboidShape(7.5, 4.5, 4.5, 8.5, 5, 5.5),
            Block.makeCuboidShape(2, 1, 12, 4, 6, 14),
            Block.makeCuboidShape(12, 1, 12, 14, 6, 14),
            Block.makeCuboidShape(4, 4, 12, 12, 6, 14),
            Block.makeCuboidShape(7.5, 1, 15, 8.5, 16, 16),
            Block.makeCuboidShape(2, 6, 12, 4, 6.5, 14),
            Block.makeCuboidShape(1.5, 6.5, 11.5, 4.5, 9.5, 14.5),
            Block.makeCuboidShape(2.5, 9.5, 12.5, 3.5, 10, 13.5),
            Block.makeCuboidShape(2, 10, 12, 4, 11, 14),
            Block.makeCuboidShape(2.5, 11, 12.5, 3.5, 13, 13.5),
            Block.makeCuboidShape(12.5, 11, 12.5, 13.5, 13, 13.5),
            Block.makeCuboidShape(7.5, 6, 4.5, 8.5, 14, 5.5),
            Block.makeCuboidShape(3.5, 13, 12.5, 12.5, 14, 13.5),
            Block.makeCuboidShape(7.5, 13, 13.5, 8.5, 14, 15),
            Block.makeCuboidShape(7.5, 13, 5.5, 8.5, 14, 12.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_N_NONE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(7, 5, 4, 9, 6, 6),
            Block.makeCuboidShape(7, 1, 4, 9, 1.5, 6),
            Block.makeCuboidShape(6.5, 1.5, 3.5, 9.5, 4.5, 6.5),
            Block.makeCuboidShape(7.5, 4.5, 4.5, 8.5, 5, 5.5),
            Block.makeCuboidShape(2, 1, 12, 4, 6, 14),
            Block.makeCuboidShape(12, 1, 12, 14, 6, 14),
            Block.makeCuboidShape(4, 4, 12, 12, 6, 14),
            Block.makeCuboidShape(7.5, 1, 15, 8.5, 16, 16),
            Block.makeCuboidShape(2.5, 11, 12.5, 3.5, 13, 13.5),
            Block.makeCuboidShape(12.5, 11, 12.5, 13.5, 13, 13.5),
            Block.makeCuboidShape(7.5, 6, 4.5, 8.5, 14, 5.5),
            Block.makeCuboidShape(3.5, 13, 12.5, 12.5, 14, 13.5),
            Block.makeCuboidShape(7.5, 13, 13.5, 8.5, 14, 15),
            Block.makeCuboidShape(7.5, 13, 5.5, 8.5, 14, 12.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_W_BOTH = Stream.of(
            Block.makeCuboidShape(4, 1, 7, 6, 1.5, 9),
            Block.makeCuboidShape(3.5, 1.5, 6.5, 6.5, 4.5, 9.5),
            Block.makeCuboidShape(4.5, 4.5, 7.5, 5.5, 5, 8.5),
            Block.makeCuboidShape(4, 5, 7, 6, 6, 9),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(12, 1, 12, 14, 6, 14),
            Block.makeCuboidShape(12, 1, 2, 14, 6, 4),
            Block.makeCuboidShape(12, 4, 4, 14, 6, 12),
            Block.makeCuboidShape(15, 1, 7.5, 16, 16, 8.5),
            Block.makeCuboidShape(12, 6, 12, 14, 6.5, 14),
            Block.makeCuboidShape(11.5, 6.5, 11.5, 14.5, 9.5, 14.5),
            Block.makeCuboidShape(12.5, 9.5, 12.5, 13.5, 10, 13.5),
            Block.makeCuboidShape(12, 10, 12, 14, 11, 14),
            Block.makeCuboidShape(12.5, 11, 12.5, 13.5, 13, 13.5),
            Block.makeCuboidShape(12.5, 11, 2.5, 13.5, 13, 3.5),
            Block.makeCuboidShape(4.5, 6, 7.5, 5.5, 14, 8.5),
            Block.makeCuboidShape(12.5, 13, 3.5, 13.5, 14, 12.5),
            Block.makeCuboidShape(13.5, 13, 7.5, 15, 14, 8.5),
            Block.makeCuboidShape(5.5, 13, 7.5, 12.5, 14, 8.5),
            Block.makeCuboidShape(12, 6, 2, 14, 6.5, 4),
            Block.makeCuboidShape(11.5, 6.5, 1.5, 14.5, 9.5, 4.5),
            Block.makeCuboidShape(12.5, 9.5, 2.5, 13.5, 10, 3.5),
            Block.makeCuboidShape(12, 10, 2, 14, 11, 4)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_W_0 = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(4, 5, 7, 6, 6, 9),
            Block.makeCuboidShape(4, 1, 7, 6, 1.5, 9),
            Block.makeCuboidShape(3.5, 1.5, 6.5, 6.5, 4.5, 9.5),
            Block.makeCuboidShape(4.5, 4.5, 7.5, 5.5, 5, 8.5),
            Block.makeCuboidShape(12, 1, 12, 14, 6, 14),
            Block.makeCuboidShape(12, 1, 2, 14, 6, 4),
            Block.makeCuboidShape(12, 4, 4, 14, 6, 12),
            Block.makeCuboidShape(15, 1, 7.5, 16, 16, 8.5),
            Block.makeCuboidShape(12.5, 11, 12.5, 13.5, 13, 13.5),
            Block.makeCuboidShape(12.5, 11, 2.5, 13.5, 13, 3.5),
            Block.makeCuboidShape(4.5, 6, 7.5, 5.5, 14, 8.5),
            Block.makeCuboidShape(12.5, 13, 3.5, 13.5, 14, 12.5),
            Block.makeCuboidShape(13.5, 13, 7.5, 15, 14, 8.5),
            Block.makeCuboidShape(5.5, 13, 7.5, 12.5, 14, 8.5),
            Block.makeCuboidShape(12, 6, 2, 14, 6.5, 4),
            Block.makeCuboidShape(11.5, 6.5, 1.5, 14.5, 9.5, 4.5),
            Block.makeCuboidShape(12.5, 9.5, 2.5, 13.5, 10, 3.5),
            Block.makeCuboidShape(12, 10, 2, 14, 11, 4)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_W_1 = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(4, 5, 7, 6, 6, 9),
            Block.makeCuboidShape(4, 1, 7, 6, 1.5, 9),
            Block.makeCuboidShape(3.5, 1.5, 6.5, 6.5, 4.5, 9.5),
            Block.makeCuboidShape(4.5, 4.5, 7.5, 5.5, 5, 8.5),
            Block.makeCuboidShape(12, 1, 12, 14, 6, 14),
            Block.makeCuboidShape(12, 1, 2, 14, 6, 4),
            Block.makeCuboidShape(12, 4, 4, 14, 6, 12),
            Block.makeCuboidShape(15, 1, 7.5, 16, 16, 8.5),
            Block.makeCuboidShape(12.5, 11, 12.5, 13.5, 13, 13.5),
            Block.makeCuboidShape(12.5, 11, 2.5, 13.5, 13, 3.5),
            Block.makeCuboidShape(4.5, 6, 7.5, 5.5, 14, 8.5),
            Block.makeCuboidShape(12.5, 13, 3.5, 13.5, 14, 12.5),
            Block.makeCuboidShape(13.5, 13, 7.5, 15, 14, 8.5),
            Block.makeCuboidShape(5.5, 13, 7.5, 12.5, 14, 8.5),
            Block.makeCuboidShape(12, 10, 12, 14, 11, 14),
            Block.makeCuboidShape(12.5, 9.5, 12.5, 13.5, 10, 13.5),
            Block.makeCuboidShape(12, 6, 12, 14, 6.5, 14),
            Block.makeCuboidShape(11.5, 6.5, 11.5, 14.5, 9.5, 14.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_W_NONE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(4, 5, 7, 6, 6, 9),
            Block.makeCuboidShape(4, 1, 7, 6, 1.5, 9),
            Block.makeCuboidShape(3.5, 1.5, 6.5, 6.5, 4.5, 9.5),
            Block.makeCuboidShape(4.5, 4.5, 7.5, 5.5, 5, 8.5),
            Block.makeCuboidShape(12, 1, 12, 14, 6, 14),
            Block.makeCuboidShape(12, 1, 2, 14, 6, 4),
            Block.makeCuboidShape(12, 4, 4, 14, 6, 12),
            Block.makeCuboidShape(15, 1, 7.5, 16, 16, 8.5),
            Block.makeCuboidShape(12.5, 11, 12.5, 13.5, 13, 13.5),
            Block.makeCuboidShape(12.5, 11, 2.5, 13.5, 13, 3.5),
            Block.makeCuboidShape(4.5, 6, 7.5, 5.5, 14, 8.5),
            Block.makeCuboidShape(12.5, 13, 3.5, 13.5, 14, 12.5),
            Block.makeCuboidShape(13.5, 13, 7.5, 15, 14, 8.5),
            Block.makeCuboidShape(5.5, 13, 7.5, 12.5, 14, 8.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_S_BOTH = Stream.of(
            Block.makeCuboidShape(7, 1, 10, 9, 1.5, 12),
            Block.makeCuboidShape(6.5, 1.5, 9.5, 9.5, 4.5, 12.5),
            Block.makeCuboidShape(7.5, 4.5, 10.5, 8.5, 5, 11.5),
            Block.makeCuboidShape(7, 5, 10, 9, 6, 12),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(12, 1, 2, 14, 6, 4),
            Block.makeCuboidShape(2, 1, 2, 4, 6, 4),
            Block.makeCuboidShape(4, 4, 2, 12, 6, 4),
            Block.makeCuboidShape(7.5, 1, 0, 8.5, 16, 1),
            Block.makeCuboidShape(12, 6, 2, 14, 6.5, 4),
            Block.makeCuboidShape(11.5, 6.5, 1.5, 14.5, 9.5, 4.5),
            Block.makeCuboidShape(12.5, 9.5, 2.5, 13.5, 10, 3.5),
            Block.makeCuboidShape(12, 10, 2, 14, 11, 4),
            Block.makeCuboidShape(12.5, 11, 2.5, 13.5, 13, 3.5),
            Block.makeCuboidShape(2.5, 11, 2.5, 3.5, 13, 3.5),
            Block.makeCuboidShape(7.5, 6, 10.5, 8.5, 14, 11.5),
            Block.makeCuboidShape(3.5, 13, 2.5, 12.5, 14, 3.5),
            Block.makeCuboidShape(7.5, 13, 1, 8.5, 14, 2.5),
            Block.makeCuboidShape(7.5, 13, 3.5, 8.5, 14, 10.5),
            Block.makeCuboidShape(2, 6, 2, 4, 6.5, 4),
            Block.makeCuboidShape(1.5, 6.5, 1.5, 4.5, 9.5, 4.5),
            Block.makeCuboidShape(2.5, 9.5, 2.5, 3.5, 10, 3.5),
            Block.makeCuboidShape(2, 10, 2, 4, 11, 4)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_S_0 = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(7, 5, 10, 9, 6, 12),
            Block.makeCuboidShape(7, 1, 10, 9, 1.5, 12),
            Block.makeCuboidShape(6.5, 1.5, 9.5, 9.5, 4.5, 12.5),
            Block.makeCuboidShape(7.5, 4.5, 10.5, 8.5, 5, 11.5),
            Block.makeCuboidShape(12, 1, 2, 14, 6, 4),
            Block.makeCuboidShape(2, 1, 2, 4, 6, 4),
            Block.makeCuboidShape(4, 4, 2, 12, 6, 4),
            Block.makeCuboidShape(7.5, 1, 0, 8.5, 16, 1),
            Block.makeCuboidShape(12.5, 11, 2.5, 13.5, 13, 3.5),
            Block.makeCuboidShape(2.5, 11, 2.5, 3.5, 13, 3.5),
            Block.makeCuboidShape(7.5, 6, 10.5, 8.5, 14, 11.5),
            Block.makeCuboidShape(3.5, 13, 2.5, 12.5, 14, 3.5),
            Block.makeCuboidShape(7.5, 13, 1, 8.5, 14, 2.5),
            Block.makeCuboidShape(7.5, 13, 3.5, 8.5, 14, 10.5),
            Block.makeCuboidShape(2, 6, 2, 4, 6.5, 4),
            Block.makeCuboidShape(1.5, 6.5, 1.5, 4.5, 9.5, 4.5),
            Block.makeCuboidShape(2.5, 9.5, 2.5, 3.5, 10, 3.5),
            Block.makeCuboidShape(2, 10, 2, 4, 11, 4)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_S_1 = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(7, 5, 10, 9, 6, 12),
            Block.makeCuboidShape(7, 1, 10, 9, 1.5, 12),
            Block.makeCuboidShape(6.5, 1.5, 9.5, 9.5, 4.5, 12.5),
            Block.makeCuboidShape(7.5, 4.5, 10.5, 8.5, 5, 11.5),
            Block.makeCuboidShape(12, 1, 2, 14, 6, 4),
            Block.makeCuboidShape(2, 1, 2, 4, 6, 4),
            Block.makeCuboidShape(4, 4, 2, 12, 6, 4),
            Block.makeCuboidShape(7.5, 1, 0, 8.5, 16, 1),
            Block.makeCuboidShape(12.5, 11, 2.5, 13.5, 13, 3.5),
            Block.makeCuboidShape(2.5, 11, 2.5, 3.5, 13, 3.5),
            Block.makeCuboidShape(7.5, 6, 10.5, 8.5, 14, 11.5),
            Block.makeCuboidShape(3.5, 13, 2.5, 12.5, 14, 3.5),
            Block.makeCuboidShape(7.5, 13, 1, 8.5, 14, 2.5),
            Block.makeCuboidShape(7.5, 13, 3.5, 8.5, 14, 10.5),
            Block.makeCuboidShape(12, 10, 2, 14, 11, 4),
            Block.makeCuboidShape(12.5, 9.5, 2.5, 13.5, 10, 3.5),
            Block.makeCuboidShape(12, 6, 2, 14, 6.5, 4),
            Block.makeCuboidShape(11.5, 6.5, 1.5, 14.5, 9.5, 4.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_S_NONE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(7, 5, 10, 9, 6, 12),
            Block.makeCuboidShape(7, 1, 10, 9, 1.5, 12),
            Block.makeCuboidShape(6.5, 1.5, 9.5, 9.5, 4.5, 12.5),
            Block.makeCuboidShape(7.5, 4.5, 10.5, 8.5, 5, 11.5),
            Block.makeCuboidShape(12, 1, 2, 14, 6, 4),
            Block.makeCuboidShape(2, 1, 2, 4, 6, 4),
            Block.makeCuboidShape(4, 4, 2, 12, 6, 4),
            Block.makeCuboidShape(7.5, 1, 0, 8.5, 16, 1),
            Block.makeCuboidShape(12.5, 11, 2.5, 13.5, 13, 3.5),
            Block.makeCuboidShape(2.5, 11, 2.5, 3.5, 13, 3.5),
            Block.makeCuboidShape(7.5, 6, 10.5, 8.5, 14, 11.5),
            Block.makeCuboidShape(3.5, 13, 2.5, 12.5, 14, 3.5),
            Block.makeCuboidShape(7.5, 13, 1, 8.5, 14, 2.5),
            Block.makeCuboidShape(7.5, 13, 3.5, 8.5, 14, 10.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_E_BOTH = Stream.of(
            Block.makeCuboidShape(10, 1, 7, 12, 1.5, 9),
            Block.makeCuboidShape(9.5, 1.5, 6.5, 12.5, 4.5, 9.5),
            Block.makeCuboidShape(10.5, 4.5, 7.5, 11.5, 5, 8.5),
            Block.makeCuboidShape(10, 5, 7, 12, 6, 9),
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(2, 1, 2, 4, 6, 4),
            Block.makeCuboidShape(2, 1, 12, 4, 6, 14),
            Block.makeCuboidShape(2, 4, 4, 4, 6, 12),
            Block.makeCuboidShape(0, 1, 7.5, 1, 16, 8.5),
            Block.makeCuboidShape(2, 6, 2, 4, 6.5, 4),
            Block.makeCuboidShape(1.5, 6.5, 1.5, 4.5, 9.5, 4.5),
            Block.makeCuboidShape(2.5, 9.5, 2.5, 3.5, 10, 3.5),
            Block.makeCuboidShape(2, 10, 2, 4, 11, 4),
            Block.makeCuboidShape(2.5, 11, 2.5, 3.5, 13, 3.5),
            Block.makeCuboidShape(2.5, 11, 12.5, 3.5, 13, 13.5),
            Block.makeCuboidShape(10.5, 6, 7.5, 11.5, 14, 8.5),
            Block.makeCuboidShape(2.5, 13, 3.5, 3.5, 14, 12.5),
            Block.makeCuboidShape(1, 13, 7.5, 2.5, 14, 8.5),
            Block.makeCuboidShape(3.5, 13, 7.5, 10.5, 14, 8.5),
            Block.makeCuboidShape(2, 6, 12, 4, 6.5, 14),
            Block.makeCuboidShape(1.5, 6.5, 11.5, 4.5, 9.5, 14.5),
            Block.makeCuboidShape(2.5, 9.5, 12.5, 3.5, 10, 13.5),
            Block.makeCuboidShape(2, 10, 12, 4, 11, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_E_0 = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(10, 5, 7, 12, 6, 9),
            Block.makeCuboidShape(10, 1, 7, 12, 1.5, 9),
            Block.makeCuboidShape(9.5, 1.5, 6.5, 12.5, 4.5, 9.5),
            Block.makeCuboidShape(10.5, 4.5, 7.5, 11.5, 5, 8.5),
            Block.makeCuboidShape(2, 1, 2, 4, 6, 4),
            Block.makeCuboidShape(2, 1, 12, 4, 6, 14),
            Block.makeCuboidShape(2, 4, 4, 4, 6, 12),
            Block.makeCuboidShape(0, 1, 7.5, 1, 16, 8.5),
            Block.makeCuboidShape(2.5, 11, 2.5, 3.5, 13, 3.5),
            Block.makeCuboidShape(2.5, 11, 12.5, 3.5, 13, 13.5),
            Block.makeCuboidShape(10.5, 6, 7.5, 11.5, 14, 8.5),
            Block.makeCuboidShape(2.5, 13, 3.5, 3.5, 14, 12.5),
            Block.makeCuboidShape(1, 13, 7.5, 2.5, 14, 8.5),
            Block.makeCuboidShape(3.5, 13, 7.5, 10.5, 14, 8.5),
            Block.makeCuboidShape(2, 6, 12, 4, 6.5, 14),
            Block.makeCuboidShape(1.5, 6.5, 11.5, 4.5, 9.5, 14.5),
            Block.makeCuboidShape(2.5, 9.5, 12.5, 3.5, 10, 13.5),
            Block.makeCuboidShape(2, 10, 12, 4, 11, 14)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_E_1 = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(10, 5, 7, 12, 6, 9),
            Block.makeCuboidShape(10, 1, 7, 12, 1.5, 9),
            Block.makeCuboidShape(9.5, 1.5, 6.5, 12.5, 4.5, 9.5),
            Block.makeCuboidShape(10.5, 4.5, 7.5, 11.5, 5, 8.5),
            Block.makeCuboidShape(2, 1, 2, 4, 6, 4),
            Block.makeCuboidShape(2, 1, 12, 4, 6, 14),
            Block.makeCuboidShape(2, 4, 4, 4, 6, 12),
            Block.makeCuboidShape(0, 1, 7.5, 1, 16, 8.5),
            Block.makeCuboidShape(2.5, 11, 2.5, 3.5, 13, 3.5),
            Block.makeCuboidShape(2.5, 11, 12.5, 3.5, 13, 13.5),
            Block.makeCuboidShape(10.5, 6, 7.5, 11.5, 14, 8.5),
            Block.makeCuboidShape(2.5, 13, 3.5, 3.5, 14, 12.5),
            Block.makeCuboidShape(1, 13, 7.5, 2.5, 14, 8.5),
            Block.makeCuboidShape(3.5, 13, 7.5, 10.5, 14, 8.5),
            Block.makeCuboidShape(2, 10, 2, 4, 11, 4),
            Block.makeCuboidShape(2.5, 9.5, 2.5, 3.5, 10, 3.5),
            Block.makeCuboidShape(2, 6, 2, 4, 6.5, 4),
            Block.makeCuboidShape(1.5, 6.5, 1.5, 4.5, 9.5, 4.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_E_NONE = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 16, 1, 16),
            Block.makeCuboidShape(10, 5, 7, 12, 6, 9),
            Block.makeCuboidShape(10, 1, 7, 12, 1.5, 9),
            Block.makeCuboidShape(9.5, 1.5, 6.5, 12.5, 4.5, 9.5),
            Block.makeCuboidShape(10.5, 4.5, 7.5, 11.5, 5, 8.5),
            Block.makeCuboidShape(2, 1, 2, 4, 6, 4),
            Block.makeCuboidShape(2, 1, 12, 4, 6, 14),
            Block.makeCuboidShape(2, 4, 4, 4, 6, 12),
            Block.makeCuboidShape(0, 1, 7.5, 1, 16, 8.5),
            Block.makeCuboidShape(2.5, 11, 2.5, 3.5, 13, 3.5),
            Block.makeCuboidShape(2.5, 11, 12.5, 3.5, 13, 13.5),
            Block.makeCuboidShape(10.5, 6, 7.5, 11.5, 14, 8.5),
            Block.makeCuboidShape(2.5, 13, 3.5, 3.5, 14, 12.5),
            Block.makeCuboidShape(1, 13, 7.5, 2.5, 14, 8.5),
            Block.makeCuboidShape(3.5, 13, 7.5, 10.5, 14, 8.5)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
}
