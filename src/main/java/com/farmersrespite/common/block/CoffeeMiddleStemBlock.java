package com.farmersrespite.common.block;

import com.farmersrespite.common.block.state.WitherRootsUtil;
import com.farmersrespite.core.FRConfiguration;
import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRItems;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.PlantType;

public class CoffeeMiddleStemBlock extends BushBlock implements BonemealableBlock {
    public static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    public CoffeeMiddleStemBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue((Property) FACING, (Comparable) Direction.NORTH));
    }


    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }


    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(FRBlocks.COFFEE_STEM_DOUBLE.get());
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, FACING);
    }


    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        pLevel.destroyBlock(pPos.below(), true, pPlayer);
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }


    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(FRItems.COFFEE_BEANS.get());
    }


    public boolean isRandomlyTicking(BlockState state) {
        return (((Integer) state.getValue((Property) AGE)).intValue() < 2);
    }


    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        for (BlockPos neighborPos : WitherRootsUtil.randomInSquareDown(random, pos, 2)) {
            BlockState neighborState = level.getBlockState(neighborPos);
            BlockState witherRootsState = (random.nextInt(2) == 0) ? FRBlocks.WITHER_ROOTS.get().defaultBlockState() : FRBlocks.WITHER_ROOTS_PLANT.get().defaultBlockState();
            if (ForgeHooks.onCropsGrowPre(level, pos, state, (random.nextInt(2) == 0))) {
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


    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        boolean flag = (i == 2);
        if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL)
            return InteractionResult.PASS;
        if (i == 2) {
            popResource(world, pos, new ItemStack(FRItems.COFFEE_BERRIES.get(), 1));
            world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            world.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return super.use(state, world, pos, player, handIn, result);
    }


    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        return (FRConfiguration.BONE_MEAL_COFFEE.get().booleanValue() && i != 2);
    }


    public boolean isBonemealSuccess(Level level, Random rand, BlockPos pos, BlockState state) {
        return true;
    }


    public void performBonemeal(ServerLevel level, Random rand, BlockPos pos, BlockState state) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
    }
}
