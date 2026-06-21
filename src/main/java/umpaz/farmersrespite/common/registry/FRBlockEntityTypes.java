package umpaz.farmersrespite.common.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;

public class FRBlockEntityTypes {
   public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "farmersrespite");
   public static final RegistryObject<BlockEntityType<KettleBlockEntity>> KETTLE = TILES.register(
      "kettle", () -> Builder.of(KettleBlockEntity::new, new Block[]{(Block)FRBlocks.KETTLE.get()}).build(null)
   );
}
