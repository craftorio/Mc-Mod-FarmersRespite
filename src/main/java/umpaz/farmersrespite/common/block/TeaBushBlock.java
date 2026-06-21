package umpaz.farmersrespite.common.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import umpaz.farmersrespite.common.FRConfiguration;
import umpaz.farmersrespite.common.registry.FRAdvancments;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRItems;

public class TeaBushBlock extends BushBlock implements BonemealableBlock {
   public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
   public static final BooleanProperty STUNTED = BooleanProperty.create("stunted");
   public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
   private static final VoxelShape SHAPE_LOWER = Shapes.or(
      Block.box(0.0, 11.0, 0.0, 16.0, 24.0, 16.0), Block.box(6.0, 0.0, 6.0, 10.0, 11.0, 10.0)
   );
   private static final VoxelShape SHAPE_UPPER = Shapes.or(
      Block.box(0.0, -5.0, 0.0, 16.0, 8.0, 16.0), Block.box(6.0, -16.0, 6.0, 10.0, -5.0, 10.0)
   );

   public TeaBushBlock(Properties properties) {
      super(properties);
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(AGE, 0)).setValue(STUNTED, false))
            .setValue(HALF, DoubleBlockHalf.LOWER)
      );
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return state.getValue(HALF) == DoubleBlockHalf.UPPER ? SHAPE_UPPER : SHAPE_LOWER;
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(BlockTags.DIRT);
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockpos = pos.below();
      return state.getValue(HALF) == DoubleBlockHalf.LOWER ? this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos) : state.is(this);
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{AGE, STUNTED, HALF});
   }

   public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
      return new ItemStack((ItemLike)FRItems.TEA_SEEDS.get());
   }

   public boolean isRandomlyTicking(BlockState state) {
      return (Integer)state.getValue(AGE) < 3 && state.getValue(HALF) == DoubleBlockHalf.LOWER && !(Boolean)state.getValue(STUNTED);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
      DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf)state.getValue(HALF);
      if (facing.getAxis() == Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
         return facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf
            ? (BlockState)((BlockState)state.setValue(AGE, (Integer)facingState.getValue(AGE))).setValue(STUNTED, (Boolean)facingState.getValue(STUNTED))
            : Blocks.AIR.defaultBlockState();
      } else {
         return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(world, pos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(state, facing, facingState, world, pos, facingPos);
      }
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (state.getValue(HALF) == DoubleBlockHalf.LOWER
         && !(Boolean)state.getValue(STUNTED)
         && ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(12) == 0)
         && (Integer)state.getValue(AGE) < 3) {
         this.performBonemeal(level, random, pos, state);
         ForgeHooks.onCropsGrowPost(level, pos, state);
      }
   }

   public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
      if (!pLevel.isClientSide && pPlayer.isCreative()) {
         preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
      }

      super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
   }

   @Nullable
   public BlockState getStateForPlacement(BlockPlaceContext pContext) {
      BlockPos blockpos = pContext.getClickedPos();
      return blockpos.getY() < 255 && pContext.getLevel().getBlockState(blockpos.above()).canBeReplaced(pContext)
         ? (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(AGE, 0)).setValue(STUNTED, false)).setValue(HALF, DoubleBlockHalf.LOWER)
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

   public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
      int i = (Integer)state.getValue(AGE);
      ItemStack heldStack = player.getItemInHand(handIn);
      Item item = heldStack.getItem();
      if (item == Items.SHEARS) {
         int j = world.random.nextInt(2);
         int k = 2 + world.random.nextInt(2);
         int l = world.random.nextInt(2);
         if (i == 0) {
            popResource(world, pos, new ItemStack((ItemLike)FRItems.GREEN_TEA_LEAVES.get(), 2 + j));
         }

         if (i == 1) {
            popResource(world, pos, new ItemStack((ItemLike)FRItems.YELLOW_TEA_LEAVES.get(), 2 + j));
         }

         if (i == 2) {
            popResource(world, pos, new ItemStack((ItemLike)FRItems.YELLOW_TEA_LEAVES.get(), 1 + j));
            popResource(world, pos, new ItemStack((ItemLike)FRItems.BLACK_TEA_LEAVES.get(), 1 + l));
         }

         if (i == 3) {
            popResource(world, pos, new ItemStack((ItemLike)FRItems.BLACK_TEA_LEAVES.get(), 2 + j));
         }

         if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            world.setBlockAndUpdate(pos, ((Block)FRBlocks.SMALL_TEA_BUSH.get()).defaultBlockState());
         }

         if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            world.setBlockAndUpdate(pos.below(), ((Block)FRBlocks.SMALL_TEA_BUSH.get()).defaultBlockState());
         }

         world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
         popResource(world, pos, new ItemStack(Items.STICK, k));
         heldStack.hurtAndBreak(1, player, p_226874_1_ -> player.broadcastBreakEvent(handIn));
         return InteractionResult.sidedSuccess(world.isClientSide);
      } else if (item instanceof AxeItem && !(Boolean)state.getValue(STUNTED)) {
         world.setBlockAndUpdate(pos, (BlockState)state.setValue(STUNTED, true));
         world.playSound(player, pos, SoundEvents.AXE_STRIP, SoundSource.BLOCKS, 1.0F, 1.0F);
         heldStack.hurtAndBreak(1, player, p_226874_1_ -> player.broadcastBreakEvent(handIn));
         if (player instanceof ServerPlayer) {
            FRAdvancments.STUNT_TEA_BUSH.trigger((ServerPlayer)player);
         }

         return InteractionResult.sidedSuccess(world.isClientSide);
      } else {
         return super.use(state, world, pos, player, handIn, result);
      }
   }

   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state, boolean isClient) {
      int i = (Integer)state.getValue(AGE);
      return i != 3 ? (Boolean)FRConfiguration.BONE_MEAL_TEA.get() : false;
   }

   public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
      int i = (Integer)state.getValue(AGE);
      level.setBlockAndUpdate(pos, (BlockState)state.setValue(AGE, i + 1));
   }

   public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
      return 60;
   }

   public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
      return 30;
   }
}
