package umpaz.farmersrespite.common.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;
import umpaz.farmersrespite.common.registry.FRBlockEntityTypes;
import vectorwing.farmersdelight.common.block.state.CookingPotSupport;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class KettleBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
   public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
   public static final EnumProperty<CookingPotSupport> SUPPORT = EnumProperty.create("support", CookingPotSupport.class);
   public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
   public static final BooleanProperty LID = BooleanProperty.create("lid");
   protected static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 7.0, 13.0);
   protected static final VoxelShape SHAPE_WITH_TRAY = Shapes.or(SHAPE, Block.box(0.0, -1.0, 0.0, 16.0, 0.0, 16.0));

   public KettleBlock() {
      super(Properties.of().mapColor(MapColor.METAL).strength(0.5F, 6.0F).sound(SoundType.LANTERN));
      this.registerDefaultState(
         (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(FACING, Direction.NORTH))
                  .setValue(SUPPORT, CookingPotSupport.NONE))
               .setValue(WATERLOGGED, false))
            .setValue(LID, true)
      );
   }

   public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
      ItemStack heldStack = player.getItemInHand(hand);
      if (!level.isClientSide && heldStack.isEmpty() && player.isShiftKeyDown()) {
         if ((Boolean)state.getValue(LID)) {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(LID, false));
         } else {
            level.setBlockAndUpdate(pos, (BlockState)state.setValue(LID, true));
         }

         level.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
         return InteractionResult.SUCCESS;
      } else {
         if (level.getBlockEntity(pos) instanceof KettleBlockEntity kettleBE) {
            FluidTank kettleTank = kettleBE.getFluidTank();
            ItemStack itm = kettleBE.fluidExtract(kettleBE, heldStack, player.getSlot(player.getInventory().getFreeSlot()).get());
            if (!itm.isEmpty()) {
               if (heldStack.isEmpty()) {
                  player.setItemInHand(hand, itm);
               } else if (!player.getInventory().add(itm)) {
                  player.drop(itm, false);
               }

               return InteractionResult.SUCCESS;
            }

            if (!level.isClientSide()) {
               NetworkHooks.openScreen((ServerPlayer)player, kettleBE, pos);
            }
         }

         return InteractionResult.SUCCESS;
      }
   }

   static InteractionResult emptyContainer(
      Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack stack, BlockState state, SoundEvent sound, Item container
   ) {
      if (!level.isClientSide) {
         Item item = stack.getItem();
         if (!player.isCreative()) {
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(container)));
         }

         player.awardStat(Stats.ITEM_USED.get(item));
         level.playSound((Player)null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
         level.gameEvent((Entity)null, GameEvent.FLUID_PLACE, pos);
      }

      return InteractionResult.sidedSuccess(level.isClientSide);
   }

   public RenderShape getRenderShape(BlockState pState) {
      return RenderShape.MODEL;
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return ((CookingPotSupport)state.getValue(SUPPORT)).equals(CookingPotSupport.TRAY) ? SHAPE_WITH_TRAY : SHAPE;
   }

   public BlockState getStateForPlacement(BlockPlaceContext context) {
      BlockPos pos = context.getClickedPos();
      Level level = context.getLevel();
      FluidState fluid = level.getFluidState(context.getClickedPos());
      BlockState state = (BlockState)((BlockState)this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()))
         .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
      return context.getClickedFace().equals(Direction.DOWN)
         ? (BlockState)state.setValue(SUPPORT, CookingPotSupport.HANDLE)
         : (BlockState)state.setValue(SUPPORT, this.getTrayState(level, pos));
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
      if ((Boolean)state.getValue(WATERLOGGED)) {
         level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
      }

      return facing.getAxis().equals(Axis.Y) && !((CookingPotSupport)state.getValue(SUPPORT)).equals(CookingPotSupport.HANDLE)
         ? (BlockState)state.setValue(SUPPORT, this.getTrayState(level, currentPos))
         : state;
   }

   private CookingPotSupport getTrayState(LevelAccessor level, BlockPos pos) {
      return level.getBlockState(pos.below()).is(ModTags.TRAY_HEAT_SOURCES) ? CookingPotSupport.TRAY : CookingPotSupport.NONE;
   }

   public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
      ItemStack stack = super.getCloneItemStack(level, pos, state);
      KettleBlockEntity kettleEntity = (KettleBlockEntity)level.getBlockEntity(pos);
      if (kettleEntity != null) {
         CompoundTag nbt = kettleEntity.writeMeal(new CompoundTag());
         if (!nbt.isEmpty()) {
            stack.addTagElement("BlockEntityTag", nbt);
         }

         if (kettleEntity.hasCustomName()) {
            stack.setHoverName(kettleEntity.getCustomName());
         }
      }

      return stack;
   }

   public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
      if (state.getBlock() != newState.getBlock()) {
         if (level.getBlockEntity(pos) instanceof KettleBlockEntity kettleEntity) {
            Containers.dropContents(level, pos, kettleEntity.getDroppableInventory());
            kettleEntity.getUsedRecipesAndPopExperience(level, Vec3.atCenterOf(pos));
            level.updateNeighbourForOutputSignal(pos, this);
         }

         super.onRemove(state, level, pos, newState, isMoving);
      }
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{FACING, SUPPORT, WATERLOGGED, LID});
   }

   public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
      if (stack.hasCustomHoverName()) {
         BlockEntity tileEntity = level.getBlockEntity(pos);
         if (tileEntity instanceof KettleBlockEntity) {
            ((KettleBlockEntity)tileEntity).setCustomName(stack.getHoverName());
         }
      }
   }

   public boolean hasAnalogOutputSignal(BlockState state) {
      return true;
   }

   public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
      BlockEntity tileEntity = level.getBlockEntity(pos);
      if (tileEntity instanceof KettleBlockEntity) {
         ItemStackHandler inventory = ((KettleBlockEntity)tileEntity).getInventory();
         return MathUtils.calcRedstoneFromItemHandler(inventory);
      } else {
         return 0;
      }
   }

   public FluidState getFluidState(BlockState state) {
      return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
   }

   @Nullable
   public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
      return ((BlockEntityType)FRBlockEntityTypes.KETTLE.get()).create(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
      return level.isClientSide
         ? createTickerHelper(blockEntity, (BlockEntityType)FRBlockEntityTypes.KETTLE.get(), KettleBlockEntity::animationTick)
         : createTickerHelper(blockEntity, (BlockEntityType)FRBlockEntityTypes.KETTLE.get(), KettleBlockEntity::brewingTick);
   }
}
