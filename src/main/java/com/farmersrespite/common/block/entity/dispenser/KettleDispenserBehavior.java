package com.farmersrespite.common.block.entity.dispenser;

import java.util.HashMap;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KettleDispenserBehavior
        extends OptionalDispenseItemBehavior {
    private static final HashMap<Item, DispenseItemBehavior> DISPENSE_ITEM_BEHAVIOR_HASH_MAP = new HashMap<>();

    public static void registerBehaviour(Item item, KettleDispenserBehavior behavior) {
        DISPENSE_ITEM_BEHAVIOR_HASH_MAP.put(item, DispenserBlock.DISPENSER_REGISTRY.get(item));
        DispenserBlock.registerBehavior(item, behavior);
    }


    public final ItemStack dispense(BlockSource source, ItemStack stack) {
        if (tryDispenseInKettle(source, stack)) {
            playSound(source);
            playAnimation(source, (Direction) source.getBlockState().getValue((Property) DispenserBlock.FACING));
            return stack;
        }
        return DISPENSE_ITEM_BEHAVIOR_HASH_MAP.get(stack.getItem()).dispense(source, stack);
    }

    public boolean tryDispenseInKettle(BlockSource source, ItemStack stack) {
        setSuccess(false);
        ServerLevel serverLevel = source.getLevel();
        BlockPos blockpos = source.getPos().relative((Direction) source.getBlockState().getValue((Property) DispenserBlock.FACING));
        BlockState blockstate = serverLevel.getBlockState(blockpos);
        Block block = blockstate.getBlock();
        BlockEntity te = serverLevel.getBlockEntity(blockpos);
        if (block instanceof com.farmersrespite.common.block.KettleBlock && te instanceof com.farmersrespite.common.block.entity.KettleBlockEntity) {
            if (stack == new ItemStack(Items.WATER_BUCKET)) {

                setSuccess(true);
                return true;
            }
            if (stack == new ItemStack(Items.POTION)) {

                setSuccess(true);
                return true;
            }
            setSuccess(true);
            return true;
        }
        return false;
    }
}
