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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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

public class CoffeeDoubleStemBlock extends BushBlock implements BonemealableBlock {
    public static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public static final IntegerProperty AGE1 = IntegerProperty.create("age1", 0, 2);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;


    public CoffeeDoubleStemBlock(BlockBehaviour.Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(AGE1, Integer.valueOf(0)).setValue((Property) FACING, (Comparable) Direction.NORTH));
    }


    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }


    protected boolean mayPlaceOn(BlockState state, BlockGetter pLevel, BlockPos pPos) {
        return (state.is(Blocks.BASALT) || state.is(Blocks.POLISHED_BASALT) || state.is(Blocks.SMOOTH_BASALT) || state.is(Blocks.MAGMA_BLOCK));
    }


    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, AGE1, FACING);
    }


    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(FRItems.COFFEE_BEANS.get());
    }


    public boolean isRandomlyTicking(BlockState state) {
        return (((Integer) state.getValue((Property) AGE)).intValue() < 2 || ((Integer) state.getValue((Property) AGE1)).intValue() < 2);
    }


    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        int j = ((Integer) state.getValue((Property) AGE1)).intValue();
        int rand = random.nextInt(2);
        for (BlockPos neighborPos : WitherRootsUtil.randomInSquare(random, pos, 2)) {
            BlockState neighborState = level.getBlockState(neighborPos);
            BlockState witherRootsState = (random.nextInt(2) == 0) ? FRBlocks.WITHER_ROOTS.get().defaultBlockState() : FRBlocks.WITHER_ROOTS_PLANT.get().defaultBlockState();
            if (ForgeHooks.onCropsGrowPre(level, pos, state, (random.nextInt(2) == 0))) {
                if (neighborState.getBlock() instanceof net.minecraft.world.level.block.CropBlock) {
                    level.setBlockAndUpdate(neighborPos, witherRootsState);
                    if (rand == 0) {
                        if (i < 2) {
                            level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
                        } else if (j < 2) {
                            level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
                        }
                    } else if (rand == 1 && j < 2) {
                        level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
                    } else if (rand == 1 && i < 2) {
                        level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
                    }
                } else if (level.dimensionType().ultraWarm()) {
                    if (rand == 0) {
                        if (i < 2) {
                            level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
                        } else if (j < 2) {
                            level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
                        }
                    } else if (rand == 1 && j < 2) {
                        level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
                    } else if (rand == 1 && i < 2) {
                        level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
                    }
                }
            }
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }


    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult result) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        int j = ((Integer) state.getValue((Property) AGE1)).intValue();
        boolean flag = (i == 2 || j == 2);
        if (!flag && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL)
            return InteractionResult.PASS;
        if (flag) {
            if (i == 2) {
                world.setBlock(pos, state.setValue(AGE, Integer.valueOf(0)), 2);
            }
            if (j == 2) {
                world.setBlock(pos, state.setValue(AGE1, Integer.valueOf(0)), 2);
            }
            popResource(world, pos, new ItemStack(FRItems.COFFEE_BERRIES.get(), 1));
            world.playSound(player, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + world.random.nextFloat() * 0.4F);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return super.use(state, world, pos, player, handIn, result);
    }


    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        int j = ((Integer) state.getValue((Property) AGE1)).intValue();
        return (FRConfiguration.BONE_MEAL_COFFEE.get().booleanValue() && (i != 2 || j != 2));
    }


    public boolean isBonemealSuccess(Level level, Random rand, BlockPos pos, BlockState state) {
        return true;
    }


    public void performBonemeal(ServerLevel level, Random rand, BlockPos pos, BlockState state) {
        int i = ((Integer) state.getValue((Property) AGE)).intValue();
        int j = ((Integer) state.getValue((Property) AGE1)).intValue();
        int random = rand.nextInt(2);
        if (random == 0) {
            if (i < 2) {
                level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
            } else if (j < 2) {
                level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
            }
        }
        if (random == 1)
            if (j < 2) {
                level.setBlockAndUpdate(pos, state.setValue(AGE1, Integer.valueOf(j + 1)));
            } else if (i < 2) {
                level.setBlockAndUpdate(pos, state.setValue(AGE, Integer.valueOf(i + 1)));
            }
    }
}
