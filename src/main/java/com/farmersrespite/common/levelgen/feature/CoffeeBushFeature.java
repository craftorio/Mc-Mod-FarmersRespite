package com.farmersrespite.common.levelgen.feature;

import com.farmersrespite.common.block.CoffeeBushBlock;
import com.farmersrespite.common.block.CoffeeBushTopBlock;
import com.farmersrespite.common.block.CoffeeStemBlock;
import com.farmersrespite.core.registry.FRBlocks;
import com.mojang.serialization.Codec;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CoffeeBushFeature extends Feature<NoneFeatureConfiguration> {
    public static final Direction[] DIRECTIONS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

    public CoffeeBushFeature(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    public static boolean canGrowCoffee(BlockState state) {
        return (state.is(Blocks.BASALT) || state.is(Blocks.POLISHED_BASALT) || state.is(Blocks.SMOOTH_BASALT) || state.is(Blocks.MAGMA_BLOCK));
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        Random rand = level.getRandom();
        BlockState coffeeBushBottom = FRBlocks.COFFEE_BUSH.get().defaultBlockState();
        BlockState coffeeBushTop = FRBlocks.COFFEE_BUSH.get().defaultBlockState().setValue((Property) CoffeeBushBlock.HALF, (Comparable) DoubleBlockHalf.UPPER);
        BlockState coffeeStem = FRBlocks.COFFEE_STEM.get().defaultBlockState();
        BlockState coffeeBushTopBottom = FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState();
        BlockState coffeeBushTopTop = FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState().setValue((Property) CoffeeBushTopBlock.HALF, (Comparable) DoubleBlockHalf.UPPER);

        HashMap<BlockPos, BlockState> blocks = new HashMap<>();
        int i = 0;
        if (rand.nextInt(4) > 2) {
            for (int x = -1; x <= 1; x++) {
                for (int z = -2; z <= 2; z++) {
                    if (Math.abs(x) < 2 || Math.abs(z) < 2) {
                        for (int y = -1; y <= 1; y++) {
                            BlockPos blockpos = pos.offset(x, y, z);
                            BlockPos below = blockpos.below();
                            BlockState belowState = level.getBlockState(below);
                            if (canGrowCoffee(belowState)) {
                                BlockPos above = blockpos.above();
                                BlockPos evenMoreAbove = blockpos.above(2);
                                if (level.isEmptyBlock(blockpos) && !level.isOutsideBuildHeight(above) && level.isEmptyBlock(above)) {
                                    if (rand.nextInt(5) < 3) {
                                        blocks.put(blockpos, coffeeBushBottom);
                                        blocks.put(above, coffeeBushTop);
                                    } else if (level.isEmptyBlock(evenMoreAbove)) {
                                        blocks.put(blockpos, coffeeStem.setValue((Property) CoffeeStemBlock.FACING, (Comparable) DIRECTIONS[rand.nextInt(4)]).setValue(CoffeeStemBlock.AGE, Integer.valueOf(rand.nextInt(3))));
                                        blocks.put(above, coffeeBushTopBottom);
                                        blocks.put(evenMoreAbove, coffeeBushTopTop);
                                    }
                                    i++;
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Map.Entry<BlockPos, BlockState> entry : blocks.entrySet()) {
            BlockPos entryPos = entry.getKey();
            BlockState entryState = entry.getValue();
            level.setBlock(entryPos, entryState, 19);
        }
        return (i > 0);
    }
}
