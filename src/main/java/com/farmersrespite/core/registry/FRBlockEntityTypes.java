package com.farmersrespite.core.registry;

import com.farmersrespite.common.block.entity.KettleBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FRBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, "farmersrespite");

    public static final RegistryObject<BlockEntityType<KettleBlockEntity>> KETTLE = TILES.register("kettle", () -> BlockEntityType.Builder.of(KettleBlockEntity::new, new Block[]{FRBlocks.KETTLE.get()}).build(null));
}
