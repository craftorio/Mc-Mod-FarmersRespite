package umpaz.farmersrespite.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import umpaz.farmersrespite.common.FRConfiguration;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRItems;

public class SmallTeaBushBlock extends BushBlock implements BonemealableBlock {
   public static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 11.0, 12.0);

   public SmallTeaBushBlock(Properties properties) {
      super(properties);
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
      return new ItemStack((ItemLike)FRItems.TEA_SEEDS.get());
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(BlockTags.DIRT);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockpos = pos.below();
      return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
   }

   public boolean isRandomlyTicking(BlockState state) {
      return true;
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(20) == 0)) {
         this.performBonemeal(level, random, pos, state);
         ForgeHooks.onCropsGrowPost(level, pos, state);
      }
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
      return (Boolean)FRConfiguration.BONE_MEAL_TEA.get();
   }

   public boolean isBonemealSuccess(Level world, RandomSource rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel world, RandomSource rand, BlockPos pos, BlockState state) {
      if (world.isEmptyBlock(pos.above())) {
         world.setBlockAndUpdate(pos, ((Block)FRBlocks.TEA_BUSH.get()).defaultBlockState());
         world.setBlockAndUpdate(pos.above(), (BlockState)((Block)FRBlocks.TEA_BUSH.get()).defaultBlockState().setValue(TeaBushBlock.HALF, DoubleBlockHalf.UPPER));
      }
   }

   public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
      return 60;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
      return 30;
   }
}
