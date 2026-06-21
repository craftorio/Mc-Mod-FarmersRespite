package umpaz.farmersrespite.data.worldgen.features;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRWorldgenFeatures;
import vectorwing.farmersdelight.common.registry.ModBiomeFeatures;
import vectorwing.farmersdelight.common.world.configuration.WildCropConfiguration;

public class FRFeatures {
   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_TEA_BUSH = ResourceKey.create(
      Registries.CONFIGURED_FEATURE, new ResourceLocation("farmersrespite", "patch_wild_tea_bush")
   );
   public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_COFFEE_BUSH = ResourceKey.create(
      Registries.CONFIGURED_FEATURE, new ResourceLocation("farmersrespite", "patch_wild_coffee_bush")
   );
   private static final BlockPos BLOCK_BELOW = new BlockPos(0, -1, 0);

   public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
      FeatureUtils.register(
         context,
         PATCH_WILD_TEA_BUSH,
         (Feature)ModBiomeFeatures.WILD_CROP.get(),
         teaBushConfig((Block)FRBlocks.WILD_TEA_BUSH.get(), Blocks.FERN, BlockPredicate.matchesTag(BLOCK_BELOW, BlockTags.DIRT))
      );
      FeatureUtils.register(
         context, PATCH_COFFEE_BUSH, new ConfiguredFeature((Feature)FRWorldgenFeatures.COFFEE.get(), new NoneFeatureConfiguration()).feature()
      );
   }

   public static Holder<PlacedFeature> plantBlockConfig(Block block, BlockPredicate plantedOn) {
      return PlacementUtils.filtered(
         Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block)), BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, plantedOn)
      );
   }

   public static WildCropConfiguration teaBushConfig(Block primaryBlock, Block secondaryBlock, BlockPredicate plantedOn) {
      return new WildCropConfiguration(3, 5, 2, plantBlockConfig(primaryBlock, plantedOn), plantBlockConfig(secondaryBlock, plantedOn), null);
   }
}
