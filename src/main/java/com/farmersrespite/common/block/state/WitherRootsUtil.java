package com.farmersrespite.common.block.state;

import java.util.Random;

import net.minecraft.core.BlockPos;


public class WitherRootsUtil {
    public static Iterable<BlockPos> randomInSquare(Random rand, BlockPos pos, int size) {
        return BlockPos.randomBetweenClosed(rand, 1, pos.getX() - size, pos.getY(), pos.getZ() - size, pos.getX() + size, pos.getY(), pos.getZ() + size);
    }

    public static Iterable<BlockPos> randomInSquareDown(Random rand, BlockPos pos, int size) {
        return BlockPos.randomBetweenClosed(rand, 1, pos.getX() - size, pos.getY() - 1, pos.getZ() - size, pos.getX() + size, pos.getY() - 1, pos.getZ() + size);
    }

    public static Iterable<BlockPos> randomInSquareDownDown(Random rand, BlockPos pos, int size) {
        return BlockPos.randomBetweenClosed(rand, 1, pos.getX() - size, pos.getY() - 2, pos.getZ() - size, pos.getX() + size, pos.getY() - 2, pos.getZ() + size);
    }
}
