package com.farmersrespite.common.block;

import com.farmersrespite.common.block.state.WitherRootsUtil;
import com.farmersrespite.core.FRConfiguration;
import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRItems;

import java.util.Random;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

public class CoffeeBushTopBlock extends BushBlock implements BonemealableBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape SHAPE_LOWER = Shapes.or(Block.box(0.0D, 6.0D, 0.0D, 16.0D, 18.0D, 16.0D), Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D));
    private static final VoxelShape SHAPE_UPPER = Shapes.or(Block.box(0.0D, -10.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(5.0D, -16.0D, 5.0D, 11.0D, -10.0D, 11.0D));


    public CoffeeBushTopBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue((Property) HALF, (Comparable) DoubleBlockHalf.LOWER));
    }

    protected static void preventCreativeDropFromBottomPart(Level world, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf) state.getValue((Property) HALF);
        if (doubleblockhalf == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = world.getBlockState(blockpos);
            if (blockstate.getBlock() == state.getBlock() && blockstate.getValue((Property) HALF) == DoubleBlockHalf.LOWER) {
                world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
            }
        }
    }

    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue((Property) HALF) == DoubleBlockHalf.UPPER) {
            return SHAPE_UPPER;
        }
        return SHAPE_LOWER;
    }

    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }

    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return (pState.is(FRBlocks.COFFEE_STEM.get()) || pState.is(FRBlocks.COFFEE_STEM_MIDDLE.get()) || pState.is(FRBlocks.COFFEE_BUSH_TOP.get()));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(FRItems.COFFEE_BEANS.get());
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf) state.getValue((Property) HALF);
        if (facing.getAxis() == Direction.Axis.Y)
            if ((doubleblockhalf == DoubleBlockHalf.LOWER) == (facing == Direction.UP) && (!facingState.is(this) || facingState.getValue((Property) HALF) == doubleblockhalf)) {
                return Blocks.AIR.defaultBlockState();
            }
        return (doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(world, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        for (BlockPos neighborPos : WitherRootsUtil.randomInSquareDown(random, pos, 2)) {
            BlockState neighborState = level.getBlockState(neighborPos);
            BlockState witherRootsState = (random.nextInt(2) == 0) ? FRBlocks.WITHER_ROOTS.get().defaultBlockState() : FRBlocks.WITHER_ROOTS_PLANT.get().defaultBlockState();
            if (state.getValue((Property) HALF) == DoubleBlockHalf.LOWER && level.isEmptyBlock(pos.above().above()) && ForgeHooks.onCropsGrowPre(level, pos, state, (random.nextInt(2) == 0))) {
                if (neighborState.getBlock() instanceof net.minecraft.world.level.block.CropBlock) {
                    level.setBlockAndUpdate(neighborPos, witherRootsState);
                    performBonemeal(level, random, pos, state);
                } else if (level.dimensionType().ultraWarm()) {
                    performBonemeal(level, random, pos, state);
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
        if (blockstate.getBlock().equals(FRBlocks.COFFEE_STEM.get()) && pState.getValue((Property) HALF) == DoubleBlockHalf.LOWER) {
            pLevel.destroyBlock(pPos.below(), true, player);
        }
        if (blockstate1.getBlock().equals(FRBlocks.COFFEE_STEM.get()) && pState.getValue((Property) HALF) == DoubleBlockHalf.UPPER) {
            pLevel.destroyBlock(pPos.below().below(), true, player);
        }
        if (blockstate.getBlock().equals(FRBlocks.COFFEE_STEM_MIDDLE.get()) && pState.getValue((Property) HALF) == DoubleBlockHalf.LOWER) {
            pLevel.destroyBlock(pPos.below(), true, player);
            pLevel.destroyBlock(pPos.below().below(), true, player);
        }
        if (blockstate1.getBlock().equals(FRBlocks.COFFEE_STEM_MIDDLE.get()) && pState.getValue((Property) HALF) == DoubleBlockHalf.UPPER) {
            pLevel.destroyBlock(pPos.below().below(), true, player);
            pLevel.destroyBlock(pPos.below().below().below(), true, player);
        }
        super.playerWillDestroy(pLevel, pPos, pState, player);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        if (blockpos.getY() < 255 && pContext.getLevel().getBlockState(blockpos.above()).canBeReplaced(pContext)) {
            return defaultBlockState().setValue((Property) HALF, (Comparable) DoubleBlockHalf.LOWER);
        }
        return null;
    }

    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue((Property) HALF, (Comparable) DoubleBlockHalf.UPPER), 3);
    }

    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockpos = pos.below();
        BlockState blockstate = world.getBlockState(blockpos);
        return (state.getValue((Property) HALF) == DoubleBlockHalf.LOWER) ? mayPlaceOn(world.getBlockState(blockpos), world, blockpos) : blockstate.is(this);
    }


    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        if (state.getValue((Property) HALF) == DoubleBlockHalf.LOWER) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            return (FRConfiguration.BONE_MEAL_COFFEE.get().booleanValue() && blockstate.getBlock().equals(FRBlocks.COFFEE_STEM.get()));
        }
        if (state.getValue((Property) HALF) == DoubleBlockHalf.UPPER) {
            BlockPos blockpos = pos.below().below();
            BlockState blockstate = level.getBlockState(blockpos);
            return (FRConfiguration.BONE_MEAL_COFFEE.get().booleanValue() && blockstate.getBlock().equals(FRBlocks.COFFEE_STEM.get()));
        }
        return false;
    }


    public boolean isBonemealSuccess(Level level, Random rand, BlockPos pos, BlockState state) {
        return FRConfiguration.BONE_MEAL_COFFEE.get().booleanValue();
    }

    public Direction getDirection(Random rand) {
        int i = rand.nextInt(4);
        if (i == 0) {
            return Direction.NORTH;
        }
        if (i == 1) {
            return Direction.SOUTH;
        }
        if (i == 2) {
            return Direction.EAST;
        }
        if (i == 3) {
            return Direction.WEST;
        }
        return null;
    }


    public void performBonemeal(ServerLevel world, Random rand, BlockPos pos, BlockState state) {
        BlockState belowState = world.getBlockState(pos.below());
        BlockState belowBelowState = world.getBlockState(pos.below().below());
        if (state.getValue((Property) HALF) == DoubleBlockHalf.LOWER && world.isEmptyBlock(pos.above().above()) && belowState.getBlock() instanceof CoffeeStemBlock) {
            Direction direction = (Direction) world.getBlockState(pos.below()).getValue((Property) CoffeeStemBlock.FACING);
            int berry = ((Integer) world.getBlockState(pos.below()).getValue((Property) CoffeeStemBlock.AGE)).intValue();
            world.setBlockAndUpdate(pos.below(), FRBlocks.COFFEE_STEM_DOUBLE.get().defaultBlockState().setValue((Property) CoffeeDoubleStemBlock.FACING, (Comparable) direction).setValue(CoffeeDoubleStemBlock.AGE, Integer.valueOf(berry)));
            world.setBlockAndUpdate(pos, FRBlocks.COFFEE_STEM_MIDDLE.get().defaultBlockState().setValue((Property) CoffeeStemBlock.FACING, (Comparable) getDirection(rand)));
            world.setBlockAndUpdate(pos.above(), FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState());
            world.setBlockAndUpdate(pos.above().above(), FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState().setValue((Property) HALF, (Comparable) DoubleBlockHalf.UPPER));
        }

        if (state.getValue((Property) HALF) == DoubleBlockHalf.UPPER && world.isEmptyBlock(pos.above()) && belowBelowState.getBlock() instanceof CoffeeStemBlock) {
            Direction direction = (Direction) world.getBlockState(pos.below().below()).getValue((Property) CoffeeStemBlock.FACING);
            int berry = ((Integer) world.getBlockState(pos.below().below()).getValue((Property) CoffeeStemBlock.AGE)).intValue();
            world.setBlockAndUpdate(pos.below().below(), FRBlocks.COFFEE_STEM_DOUBLE.get().defaultBlockState().setValue((Property) CoffeeDoubleStemBlock.FACING, (Comparable) direction).setValue(CoffeeDoubleStemBlock.AGE, Integer.valueOf(berry)));
            world.setBlockAndUpdate(pos.below(), FRBlocks.COFFEE_STEM_MIDDLE.get().defaultBlockState().setValue((Property) CoffeeStemBlock.FACING, (Comparable) getDirection(rand)));
            world.setBlockAndUpdate(pos, FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState());
            world.setBlockAndUpdate(pos.above(), FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState().setValue((Property) HALF, (Comparable) DoubleBlockHalf.UPPER));
        }
    }
}
