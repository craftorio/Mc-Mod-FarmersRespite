package com.farmersrespite.common.block;

import com.farmersrespite.core.FRConfiguration;
import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRItems;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class SmallTeaBushBlock extends BushBlock implements BonemealableBlock {
    public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);

    public SmallTeaBushBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }


    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.DIRT);
    }


    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(FRItems.TEA_SEEDS.get());
    }


    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }


    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (ForgeHooks.onCropsGrowPre(level, pos, state, (random.nextInt(50) == 0))) {
            performBonemeal(level, random, pos, state);
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }


    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return FRConfiguration.BONE_MEAL_TEA.get().booleanValue();
    }


    public boolean isBonemealSuccess(Level world, Random rand, BlockPos pos, BlockState state) {
        return true;
    }


    public void performBonemeal(ServerLevel world, Random rand, BlockPos pos, BlockState state) {
        if (world.isEmptyBlock(pos.above())) {
            world.setBlockAndUpdate(pos, FRBlocks.TEA_BUSH.get().defaultBlockState());
            world.setBlockAndUpdate(pos.above(), (FRBlocks.TEA_BUSH.get()).defaultBlockState().setValue(TeaBushBlock.HALF, DoubleBlockHalf.UPPER));
        }
    }
}
