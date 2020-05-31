Stream.of(
Block.makeCuboidShape(3.5, 12, 2.5, 4.5, 14, 3.5),
Block.makeCuboidShape(3.5, 12, 12.5, 4.5, 14, 13.5),
Block.makeCuboidShape(11.5, 7, 7.5, 12.5, 14, 8.5),
Block.makeCuboidShape(3.5, 14, 3.5, 4.5, 15, 12.5),
Block.makeCuboidShape(2, 14, 7.5, 3.5, 15, 8.5),
Block.makeCuboidShape(4.5, 14, 7.5, 11.5, 15, 8.5),
Block.makeCuboidShape(10.5, 2.5, 6.5, 13.5, 5.5, 9.5),
Block.makeCuboidShape(11.5, 5.5, 7.5, 12.5, 6, 8.5),
Block.makeCuboidShape(11, 6, 7, 13, 7, 9),
Block.makeCuboidShape(10.75, 2.75, 6.75, 13.25, 5.25, 9.25),
Block.makeCuboidShape(11, 2, 7, 13, 2.5, 9),
Block.makeCuboidShape(1, 0, 1, 15, 2, 15),
Block.makeCuboidShape(3, 2, 2, 5, 7, 4),
Block.makeCuboidShape(3, 2, 12, 5, 7, 14),
Block.makeCuboidShape(3, 5, 4, 5, 7, 12),
Block.makeCuboidShape(1, 2, 7.5, 2, 16, 8.5)
).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);});