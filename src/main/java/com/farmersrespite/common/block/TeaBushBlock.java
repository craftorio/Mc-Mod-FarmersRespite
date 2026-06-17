package com.farmersrespite.common.block;

import com.farmersrespite.core.FRConfiguration;
import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRItems;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class TeaBushBlock extends BushBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final BooleanProperty WAXED = BooleanProperty.create("waxed");
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    private static final VoxelShape SHAPE_LOWER = Shapes.or(Block.box(0.0D, 11.0D, 0.0D, 16.0D, 24.0D, 16.0D), Block.box(6.0D, 0.0D, 6.0D, 10.0D, 11.0D, 10.0D));
    private static final VoxelShape SHAPE_UPPER = Shapes.or(Block.box(0.0D, -5.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(6.0D, -16.0D, 6.0D, 10.0D, -5.0D, 10.0D));

    public TeaBushBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(WAXED, Boolean.valueOf(false)).setValue((Property) HALF, (Comparable) DoubleBlockHalf.LOWER));
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, WAXED, HALF);
    }

    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(FRItems.TEA_SEEDS.get());
    }

    public boolean isRandomlyTicking(BlockState state) {
        return (((Integer) state.getValue((Property) AGE)).intValue() < 3 && state.getValue((Property) HALF) == DoubleBlockHalf.LOWER && !((Boolean) state.getValue((Property) WAXED)).booleanValue());
    }

    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
        DoubleBlockHalf doubleblockhalf = (DoubleBlockHalf) state.getValue((Property) HALF);
        if (facing.getAxis() == Direction.Axis.Y)
            if ((doubleblockhalf == DoubleBlockHalf.LOWER) == (facing == Direction.UP)) {
                return (facingState.is(this) && facingState.getValue((Property) HALF) != doubleblockhalf) ? state.setValue((Property) AGE, facingState.getValue((Property) AGE)) : Blocks.AIR.defaultBlockState();
            }
        return (doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(world, pos)) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (state.getValue((Property) HALF) == DoubleBlockHalf.LOWER && ForgeHooks.onCropsGrowPre(level, pos, state, (random.nextInt(15) == 0)) && ((Integer) state.getValue((Property) AGE)).intValue() < 3) {
            performBonemeal(level, random, pos, state);
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && pPlayer.isCreative()) {
            preventCreativeDropFromBottomPart(pLevel, pPos, pState, pPlayer);
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        if (blockpos.getY() < 255 && pContext.getLevel().getBlockState(blockpos.above()).canBeReplaced(pContext)) {
            return defaultBlockState().setValue(AGE, Integer.valueOf(0)).setValue(WAXED, Boolean.valueOf(false)).setValue((Property) HALF, (Comparable) DoubleBlockHalf.LOWER);
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


    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return (state.is(BlockTags.DIRT) || state.is(Blocks.GRASS));
    }


    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        ItemStack heldStack = player.getItemInHand(handIn);
        Item item = heldStack.getItem();

        if (item == Items.SHEARS) {
            int j = world.random.nextInt(2);
            int k = 2 + world.random.nextInt(2);
            int l = world.random.nextInt(2);

            if (i == 0) {
                popResource(world, pos, new ItemStack(FRItems.GREEN_TEA_LEAVES.get(), 2 + j));
            }
            if (i == 1) {
                popResource(world, pos, new ItemStack(FRItems.YELLOW_TEA_LEAVES.get(), 2 + j));
            }
            if (i == 2) {
                popResource(world, pos, new ItemStack(FRItems.YELLOW_TEA_LEAVES.get(), 1 + j));
                popResource(world, pos, new ItemStack(FRItems.BLACK_TEA_LEAVES.get(), 1 + l));
            }
            if (i == 3) {
                popResource(world, pos, new ItemStack(FRItems.BLACK_TEA_LEAVES.get(), 2 + j));
            }
            if (state.getValue((Property) HALF) == DoubleBlockHalf.LOWER) {
                world.setBlockAndUpdate(pos, FRBlocks.SMALL_TEA_BUSH.get().defaultBlockState());
            }
            if (state.getValue((Property) HALF) == DoubleBlockHalf.UPPER) {
                world.setBlockAndUpdate(pos.below(), FRBlocks.SMALL_TEA_BUSH.get().defaultBlockState());
            }
            world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            popResource(world, pos, new ItemStack(Items.STICK, k));
            heldStack.hurtAndBreak(1, (LivingEntity) player, p_226874_1_ -> player.broadcastBreakEvent(handIn));


            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        if (item == Items.HONEYCOMB && !((Boolean) state.getValue((Property) WAXED)).booleanValue()) {
            if (state.getValue((Property) HALF) == DoubleBlockHalf.LOWER) {
                world.setBlockAndUpdate(pos, state.setValue(WAXED, Boolean.valueOf(true)).setValue((Property) HALF, (Comparable) DoubleBlockHalf.LOWER));
                world.setBlockAndUpdate(pos.above(), state.setValue(WAXED, Boolean.valueOf(true)).setValue((Property) HALF, (Comparable) DoubleBlockHalf.UPPER));
                ParticleUtils.spawnParticlesOnBlockFaces(world, pos, ParticleTypes.WAX_ON, UniformInt.of(3, 5));
                ParticleUtils.spawnParticlesOnBlockFaces(world, pos.above(), ParticleTypes.WAX_ON, UniformInt.of(3, 5));
            }
            if (state.getValue((Property) HALF) == DoubleBlockHalf.UPPER) {
                world.setBlockAndUpdate(pos, state.setValue(WAXED, Boolean.valueOf(true)).setValue((Property) HALF, (Comparable) DoubleBlockHalf.UPPER));
                world.setBlockAndUpdate(pos.below(), state.setValue(WAXED, Boolean.valueOf(true)).setValue((Property) HALF, (Comparable) DoubleBlockHalf.LOWER));
                ParticleUtils.spawnParticlesOnBlockFaces(world, pos, ParticleTypes.WAX_ON, UniformInt.of(3, 5));
                ParticleUtils.spawnParticlesOnBlockFaces(world, pos.below(), ParticleTypes.WAX_ON, UniformInt.of(3, 5));
            }
            world.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (!(player.getAbilities()).instabuild) {
                heldStack.shrink(1);
            }
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.use(state, world, pos, player, handIn, result);
    }


    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        if (i != 3 && !((Boolean) state.getValue((Property) WAXED)).booleanValue()) {
            return FRConfiguration.BONE_MEAL_TEA.get().booleanValue();
        }
        return false;
    }


    public boolean isBonemealSuccess(Level level, Random rand, BlockPos pos, BlockState state) {
        return true;
    }


    public void performBonemeal(ServerLevel level, Random rand, BlockPos pos, BlockState state) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
    }
}
