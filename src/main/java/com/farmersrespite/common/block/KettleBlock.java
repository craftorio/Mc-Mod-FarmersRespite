package com.farmersrespite.common.block;

import com.farmersrespite.common.block.entity.KettleBlockEntity;
import com.farmersrespite.core.registry.FRBlockEntityTypes;
import com.farmersrespite.core.registry.FRSounds;
import com.farmersrespite.core.utility.FRTextUtils;

import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import vectorwing.farmersdelight.common.block.state.CookingPotSupport;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.MathUtils;

public class KettleBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<CookingPotSupport> SUPPORT = EnumProperty.create("support", CookingPotSupport.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty WATER_LEVEL = IntegerProperty.create("water", 0, 3);
    public static final BooleanProperty LID = BooleanProperty.create("lid");

    protected static final VoxelShape SHAPE = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 13.0D);
    protected static final VoxelShape SHAPE_WITH_TRAY = Shapes.or(SHAPE, Block.box(0.0D, -1.0D, 0.0D, 16.0D, 0.0D, 16.0D));

    public KettleBlock() {
        super(BlockBehaviour.Properties.of(Material.METAL)
                .strength(0.5F, 6.0F)
                .sound(SoundType.LANTERN));
        registerDefaultState(this.stateDefinition.any().setValue((Property) FACING, (Comparable) Direction.NORTH).setValue((Property) SUPPORT, (Comparable) CookingPotSupport.NONE).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(WATER_LEVEL, Integer.valueOf(0)).setValue(LID, Boolean.valueOf(true)));
    }


    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
        ItemStack heldStack = player.getItemInHand(handIn);
        Item item = heldStack.getItem();
        BlockEntity tileEntity = world.getBlockEntity(pos);
        int i = ((Integer) state.getValue((Property) WATER_LEVEL)).intValue();
        if (!world.isClientSide) {
            if (heldStack.isEmpty() && player.isShiftKeyDown()) {
                if (((Boolean) state.getValue((Property) LID)).booleanValue()) {
                    world.setBlockAndUpdate(pos, state.setValue(LID, Boolean.valueOf(false)));
                }
                if (!((Boolean) state.getValue((Property) LID)).booleanValue()) {
                    world.setBlockAndUpdate(pos, state.setValue(LID, Boolean.valueOf(true)));
                }
                world.playSound(null, pos, SoundEvents.LANTERN_PLACE, SoundSource.BLOCKS, 0.7F, 1.0F);
            } else if (i < 3 && item == Items.WATER_BUCKET) {
                if (!(player.getAbilities()).instabuild) {
                    player.setItemInHand(handIn, new ItemStack(Items.BUCKET));
                }
                if (i == 0) {
                    world.setBlockAndUpdate(pos, state.setValue(WATER_LEVEL, Integer.valueOf(i + 3)));
                }
                if (i == 1) {
                    world.setBlockAndUpdate(pos, state.setValue(WATER_LEVEL, Integer.valueOf(i + 2)));
                }
                if (i == 2) {
                    world.setBlockAndUpdate(pos, state.setValue(WATER_LEVEL, Integer.valueOf(i + 1)));
                }
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BUCKET_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else if (i < 3 && item == Items.POTION && PotionUtils.getPotion(heldStack) == Potions.WATER) {
                if (!(player.getAbilities()).instabuild) {
                    heldStack.setCount(heldStack.getCount() - 1);
                    player.setItemInHand(handIn, heldStack);
                    player.addItem(new ItemStack(Items.GLASS_BOTTLE));
                }
                world.setBlockAndUpdate(pos, state.setValue(WATER_LEVEL, Integer.valueOf(i + 1)));
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_EMPTY, SoundSource.NEUTRAL, 1.0F, 1.0F);
            } else if (tileEntity instanceof KettleBlockEntity kettleEntity) {
                ItemStack servingStack = kettleEntity.useHeldItemOnMeal(heldStack);
                if (servingStack != ItemStack.EMPTY) {
                    if (!player.getInventory().add(servingStack)) {
                        player.drop(servingStack, false);
                    }
                    world.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    NetworkHooks.openGui((ServerPlayer) player, kettleEntity, pos);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }


    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }


    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    public VoxelShape getCollisionShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return state.getValue((Property) SUPPORT).equals(CookingPotSupport.TRAY) ? SHAPE_WITH_TRAY : SHAPE;
    }


    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        FluidState fluid = world.getFluidState(context.getClickedPos());


        BlockState state = defaultBlockState().setValue((Property) FACING, (Comparable) context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, Boolean.valueOf((fluid.getType() == Fluids.WATER)));

        if (context.getClickedFace().equals(Direction.DOWN)) {
            return state.setValue((Property) SUPPORT, (Comparable) CookingPotSupport.HANDLE);
        }
        return state.setValue((Property) SUPPORT, (Comparable) getTrayState(world, pos));
    }


    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos currentPos, BlockPos facingPos) {
        if (((Boolean) state.getValue((Property) WATERLOGGED)).booleanValue()) {
            world.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        if (facing.getAxis().equals(Direction.Axis.Y) && !state.getValue((Property) SUPPORT).equals(CookingPotSupport.HANDLE)) {
            return state.setValue((Property) SUPPORT, (Comparable) getTrayState(world, currentPos));
        }
        return state;
    }

    private CookingPotSupport getTrayState(LevelAccessor world, BlockPos pos) {
        if (world.getBlockState(pos.below()).is(ModTags.TRAY_HEAT_SOURCES)) {
            return CookingPotSupport.TRAY;
        }
        return CookingPotSupport.NONE;
    }


    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        ItemStack stack = super.getCloneItemStack(worldIn, pos, state);
        KettleBlockEntity kettleEntity = (KettleBlockEntity) worldIn.getBlockEntity(pos);
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


    public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof KettleBlockEntity kettleEntity) {
                Containers.dropContents(worldIn, pos, kettleEntity.getDroppableInventory());
                kettleEntity.grantStoredRecipeExperience(worldIn, Vec3.atCenterOf(pos));
                worldIn.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, worldIn, pos, newState, isMoving);
        }
    }


    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        CompoundTag nbt = stack.getTagElement("BlockEntityTag");
        if (nbt != null) {
            CompoundTag inventoryTag = nbt.getCompound("Inventory");
            if (inventoryTag.contains("Items", 9)) {
                ItemStackHandler handler = new ItemStackHandler();
                handler.deserializeNBT(inventoryTag);
                ItemStack mealStack = handler.getStackInSlot(2);
                if (!mealStack.isEmpty()) {


                    MutableComponent textServingsOf = (mealStack.getCount() == 1) ? FRTextUtils.getTranslation("tooltip.kettle.single_serving") : FRTextUtils.getTranslation("tooltip.kettle.many_servings", Integer.valueOf(mealStack.getCount()));
                    tooltip.add(textServingsOf.withStyle(ChatFormatting.GRAY));
                    MutableComponent textMealName = mealStack.getHoverName().copy();
                    tooltip.add(textMealName.withStyle((mealStack.getRarity()).color));
                }
            }
        } else {
            MutableComponent textEmpty = FRTextUtils.getTranslation("tooltip.kettle.empty");
            tooltip.add(textEmpty.withStyle(ChatFormatting.GRAY));
        }
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, SUPPORT, WATERLOGGED, WATER_LEVEL, LID);
    }


    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            BlockEntity tileEntity = worldIn.getBlockEntity(pos);
            if (tileEntity instanceof KettleBlockEntity) {
                ((KettleBlockEntity) tileEntity).setCustomName(stack.getHoverName());
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof KettleBlockEntity && ((KettleBlockEntity) tileEntity).isHeated() && ((KettleBlockEntity) tileEntity).isDrinkEmpty() && ((Boolean) stateIn.getValue((Property) LID)).booleanValue()) {
            double x = pos.getX() + 0.5D;
            double y = pos.getY();
            double z = pos.getZ() + 0.5D;
            if (rand.nextInt(20) == 0) {
                worldIn.playLocalSound(x, y, z, FRSounds.BLOCK_KETTLE_WHISTLE.get(), SoundSource.BLOCKS, 0.07F, rand.nextFloat() * 0.2F + 0.9F, false);
            }
        }
    }


    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }


    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
        BlockEntity tileEntity = worldIn.getBlockEntity(pos);
        if (tileEntity instanceof KettleBlockEntity) {
            ItemStackHandler inventory = ((KettleBlockEntity) tileEntity).getInventory();
            return MathUtils.calcRedstoneFromItemHandler(inventory);
        }
        return 0;
    }


    public FluidState getFluidState(BlockState state) {
        return ((Boolean) state.getValue((Property) WATERLOGGED)).booleanValue() ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ((BlockEntityType) FRBlockEntityTypes.KETTLE.get()).create(pos, state);
    }


    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntity) {
        if (level.isClientSide) {
            return createTickerHelper(blockEntity, FRBlockEntityTypes.KETTLE.get(), KettleBlockEntity::animationTick);
        }
        return createTickerHelper(blockEntity, FRBlockEntityTypes.KETTLE.get(), KettleBlockEntity::brewingTick);
    }
}
