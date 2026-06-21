package umpaz.farmersrespite.common;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import umpaz.farmersrespite.common.loot.function.FRCopyMealFunction;
import umpaz.farmersrespite.common.registry.FRAdvancments;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRItems;

@EventBusSubscriber(modid = "farmersrespite", bus = Bus.MOD, value = Dist.CLIENT)
public class FRCommonSetup {
   @SubscribeEvent
   public static void init(FMLCommonSetupEvent event) {
      event.enqueueWork(FRCommonSetup::registerCompostables);
      event.enqueueWork(FRCommonSetup::registerLootItemFunctions);
      event.enqueueWork(FRCommonSetup::registerFlowerPotPlants);
      FRAdvancments.register();
   }

   public static void registerCompostables() {
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.GREEN_TEA_LEAVES.get(), 0.3F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.YELLOW_TEA_LEAVES.get(), 0.2F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.BLACK_TEA_LEAVES.get(), 0.1F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.COFFEE_BERRIES.get(), 0.3F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.TEA_SEEDS.get(), 0.3F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.ROSE_HIPS.get(), 0.3F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.GREEN_TEA_COOKIE.get(), 0.85F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.WILD_TEA_BUSH.get(), 0.65F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.COFFEE_CAKE.get(), 1.0F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.ROSE_HIP_PIE.get(), 1.0F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.COFFEE_CAKE_SLICE.get(), 0.85F);
      ComposterBlock.COMPOSTABLES.put((ItemLike)FRItems.ROSE_HIP_PIE_SLICE.get(), 0.85F);
   }

   public static void registerLootItemFunctions() {
      LootItemFunctions.register(FRCopyMealFunction.ID.toString(), new FRCopyMealFunction.Serializer());
   }

   public static void registerFlowerPotPlants() {
      FlowerPotBlock flowerPotBlock = (FlowerPotBlock)Blocks.FLOWER_POT;
      flowerPotBlock.addPlant(FRBlocks.WILD_TEA_BUSH.getId(), FRBlocks.POTTED_WILD_TEA_BUSH);
      flowerPotBlock.addPlant(FRBlocks.WILD_COFFEE_BUSH.getId(), FRBlocks.POTTED_WILD_COFFEE_BUSH);
   }
}
