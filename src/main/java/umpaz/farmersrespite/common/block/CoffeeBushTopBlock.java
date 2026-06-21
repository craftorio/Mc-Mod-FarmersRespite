package umpaz.farmersrespite.common.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;
import umpaz.farmersrespite.common.FRConfiguration;
import umpaz.farmersrespite.common.block.state.WitherRootsUtil;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRItems;

public class CoffeeBushTopBlock extends BushBlock implements BonemealableBlock {
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
   private static final VoxelShape SHAPE_LOWER = Shapes.or(
      Block.box(0.0, 6.0, 0.0, 16.0, 18.0, 16.0), Block.box(5.0, 0.0, 5.0, 11.0, 6.0, 11.0)
   );
   private static final VoxelShape SHAPE_UPPER = Shapes.or(
      Block.box(0.0, -10.0, 0.0, 16.0, 2.0, 16.0), Block.box(5.0, -16.0, 5.0, 11.0, -10.0, 11.0)
   );

   public CoffeeBushTopBlock(Properties properties) {
      super(properties);
      this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(HALF, DoubleBlockHalf.LOWER));
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return state.getValue(HALF) == DoubleBlockHalf.UPPER ? SHAPE_UPPER : SHAPE_LOWER;
   }

   public PlantType getPlantType(BlockGetter world, BlockPos pos) {
      return PlantType.NETHER;
   }

   protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
      return pState.is((Block)FRBlocks.COFFEE_STEM.get())
         || pState.is((Block)FRBlocks.COFFEE_STEM_MIDDLE.get())
         || pState.is((Block)FRBlocks.COFFEE_BUSH_TOP.get());
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{HALF});
   }

   public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
      return new ItemStack((ItemLike)FRItems.COFFEE_BEANS.get());
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
      DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)state.getValue(HALF);
      if (facing.getAxis() != Axis.Y
         || doubleblockhalf == DoubleBlockHalf.LOWER != (facing == Direction.UP)
         || facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf) {
         return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(world, pos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, facing, facingState, world, pos, facingPos);
      } else {
         return Blocks.AIR.defaultBlockState();
      }
   }

   public boolean isRandomlyTicking(BlockState state) {
      return true;
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      for (BlockPos neighborPos : WitherRootsUtil.randomInSquareDown(random, pos, 2)) {
         BlockState neighborState = level.getBlockState(neighborPos);
         int witherRootRandom = random.nextInt(5);
         BlockState witherRootsState;
         if (witherRootRandom < 2) {
            witherRootsState = ((Block)FRBlocks.WITHER_ROOTS.get()).defaultBlockState();
         } else if (witherRootRandom < 4) {
            witherRootsState = ((Block)FRBlocks.WITHER_ROOTS_PLANT.get()).defaultBlockState();
         } else {
            witherRootsState = ((Block)FRBlocks.WILD_COFFEE_BUSH.get()).defaultBlockState();
         }

         if (state.getValue(HALF) == DoubleBlockHalf.LOWER
            && level.isEmptyBlock(pos.above().above())
            && ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(2) == 0)) {
            if (neighborState.getBlock() instanceof CropBlock) {
               level.setBlockAndUpdate(neighborPos, witherRootsState);
               this.performBonemeal(level, random, pos, state);
            } else if (level.dimensionType().ultraWarm()) {
               this.performBonemeal(level, random, pos, state);
            }
         }

         ForgeHooks.onCropsGrowPost(level, pos, state);
      }
   }

   public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player player) {
      if (!pLevel.isClientSide && player.isCreative()) {
         preventCreativeDropFromBottomPart(pLevel, pPos, pState, player);
      }

      BlockPos blockpos = pPos.below();
      BlockState blockstate = pLevel.getBlockState(blockpos);
      BlockPos blockpos1 = pPos.below().below();
      BlockState blockstate1 = pLevel.getBlockState(blockpos1);
      if (blockstate.getBlock().equals(FRBlocks.COFFEE_STEM.get()) && pState.getValue(HALF) == DoubleBlockHalf.LOWER) {
         pLevel.destroyBlock(pPos.below(), true, player);
      }

      if (blockstate1.getBlock().equals(FRBlocks.COFFEE_STEM.get()) && pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
         pLevel.destroyBlock(pPos.below().below(), true, player);
      }

      if (blockstate.getBlock().equals(FRBlocks.COFFEE_STEM_MIDDLE.get()) && pState.getValue(HALF) == DoubleBlockHalf.LOWER) {
         pLevel.destroyBlock(pPos.below(), true, player);
         pLevel.destroyBlock(pPos.below().below(), true, player);
      }

      if (blockstate1.getBlock().equals(FRBlocks.COFFEE_STEM_MIDDLE.get()) && pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
         pLevel.destroyBlock(pPos.below().below(), true, player);
         pLevel.destroyBlock(pPos.below().below().below(), true, player);
      }

      super.playerWillDestroy(pLevel, pPos, pState, player);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
      BlockPos blockpos = pContext.getClickedPos();
      return blockpos.getY() < 255 && pContext.getLevel().getBlockState(blockpos.above()).canBeReplaced(pContext)
         ? (BlockState)this.defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER)
         : null;
   }

   public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
      pLevel.setBlock(pPos.above(), (BlockState)pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
   }

   protected static void preventCreativeDropFromBottomPart(Level world, BlockPos pos, BlockState state, Player player) {
      DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)state.getValue(HALF);
      if (doubleblockhalf == DoubleBlockHalf.UPPER) {
         BlockPos blockpos = pos.below();
         BlockState blockstate = world.getBlockState(blockpos);
         if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
            world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
            world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
         }
      }
   }

   public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
      BlockPos blockpos = pos.below();
      BlockState blockstate = world.getBlockState(blockpos);
      return state.getValue(HALF) == DoubleBlockHalf.LOWER ? this.mayPlaceOn(world.getBlockState(blockpos), world, blockpos) : blockstate.is(this);
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
      if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
         BlockPos blockpos = pos.below();
         BlockState blockstate = level.getBlockState(blockpos);
         return (Boolean)FRConfiguration.BONE_MEAL_COFFEE.get() && blockstate.getBlock().equals(FRBlocks.COFFEE_STEM.get());
      }

      if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
         return false;
      }

      BlockPos blockpos = pos.below().below();
      BlockState blockstate = level.getBlockState(blockpos);
      return (Boolean)FRConfiguration.BONE_MEAL_COFFEE.get() && blockstate.getBlock().equals(FRBlocks.COFFEE_STEM.get());
   }

   public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
      return (Boolean)FRConfiguration.BONE_MEAL_COFFEE.get();
   }

   public Direction getDirection(RandomSource rand) {
      int i = rand.nextInt(4);
      if (i == 0) {
         return Direction.NORTH;
      } else if (i == 1) {
         return Direction.SOUTH;
      } else if (i == 2) {
         return Direction.EAST;
      } else {
         return i == 3 ? Direction.WEST : null;
      }
   }

   public void performBonemeal(ServerLevel world, RandomSource rand, BlockPos pos, BlockState state) {
      BlockState belowState = world.getBlockState(pos.below());
      BlockState belowBelowState = world.getBlockState(pos.below().below());
      if (state.getValue(HALF) == DoubleBlockHalf.LOWER && world.isEmptyBlock(pos.above().above()) && belowState.getBlock() instanceof CoffeeStemBlock) {
         Direction direction = (Direction)world.getBlockState(pos.below()).getValue(CoffeeStemBlock.FACING);
         int berry = (Integer)world.getBlockState(pos.below()).getValue(CoffeeStemBlock.AGE);
         world.setBlockAndUpdate(
            pos.below(),
            (BlockState)((BlockState)((Block)FRBlocks.COFFEE_STEM_DOUBLE.get()).defaultBlockState().setValue(CoffeeDoubleStemBlock.FACING, direction))
               .setValue(CoffeeDoubleStemBlock.AGE, berry)
         );
         world.setBlockAndUpdate(pos, (BlockState)((Block)FRBlocks.COFFEE_STEM_MIDDLE.get()).defaultBlockState().setValue(CoffeeStemBlock.FACING, this.getDirection(rand)));
         world.setBlockAndUpdate(pos.above(), ((Block)FRBlocks.COFFEE_BUSH_TOP.get()).defaultBlockState());
         world.setBlockAndUpdate(pos.above().above(), (BlockState)((Block)FRBlocks.COFFEE_BUSH_TOP.get()).defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
      }

      if (state.getValue(HALF) == DoubleBlockHalf.UPPER && world.isEmptyBlock(pos.above()) && belowBelowState.getBlock() instanceof CoffeeStemBlock) {
         Direction direction = (Direction)world.getBlockState(pos.below().below()).getValue(CoffeeStemBlock.FACING);
         int berry = (Integer)world.getBlockState(pos.below().below()).getValue(CoffeeStemBlock.AGE);
         world.setBlockAndUpdate(
            pos.below().below(),
            (BlockState)((BlockState)((Block)FRBlocks.COFFEE_STEM_DOUBLE.get()).defaultBlockState().setValue(CoffeeDoubleStemBlock.FACING, direction))
               .setValue(CoffeeDoubleStemBlock.AGE, berry)
         );
         world.setBlockAndUpdate(
            pos.below(), (BlockState)((Block)FRBlocks.COFFEE_STEM_MIDDLE.get()).defaultBlockState().setValue(CoffeeStemBlock.FACING, this.getDirection(rand))
         );
         world.setBlockAndUpdate(pos, ((Block)FRBlocks.COFFEE_BUSH_TOP.get()).defaultBlockState());
         world.setBlockAndUpdate(pos.above(), (BlockState)((Block)FRBlocks.COFFEE_BUSH_TOP.get()).defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
      }
   }
}
