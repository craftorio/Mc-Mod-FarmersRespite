package com.farmersrespite.common.block;

import java.util.Random;

import com.farmersrespite.core.FRConfiguration;
import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRItems;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

	public class SmallTeaBushBlock extends BushBlock implements BonemealableBlock {
		   public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 11.0D, 12.0D);

		public SmallTeaBushBlock(Properties properties) {
			super(properties);
		}

		   @Override
		public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			 return SHAPE;
		 }

		 @Override
		public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
			 return new ItemStack(FRItems.TEA_SEEDS.get());
		 }

		 @Override
		public boolean isRandomlyTicking(BlockState state) {
			 return true;
		 }

		 @Override
		public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
			 if (net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(50) == 0)) {
				 performBonemeal(level, random, pos, state);
				 net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
			 }
		 }

		@Override
		public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
			return FRConfiguration.BONE_MEAL_TEA.get();
		}

		@Override
		public boolean isBonemealSuccess(Level world, Random rand, BlockPos pos, BlockState state) {
			return true;
		}

		@Override
		public void performBonemeal(ServerLevel world, Random rand, BlockPos pos, BlockState state) {
		      if (world.isEmptyBlock(pos.above())) {
			world.setBlockAndUpdate(pos, FRBlocks.TEA_BUSH.get().defaultBlockState());
			world.setBlockAndUpdate(pos.above(), FRBlocks.TEA_BUSH.get().defaultBlockState().setValue(TeaBushBlock.HALF, DoubleBlockHalf.UPPER));
		      }
		}
}
