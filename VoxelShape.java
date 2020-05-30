Stream.of(
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
).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});