package umpaz.farmersrespite.data.worldgen.placement;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import umpaz.farmersrespite.data.worldgen.features.FRFeatures;

public class FRPlacements {
   public static final ResourceKey<PlacedFeature> PATCH_WILD_TEA_BUSH = ResourceKey.create(
      Registries.PLACED_FEATURE, new ResourceLocation("farmersrespite", "patch_wild_tea_bush")
   );
   public static final ResourceKey<PlacedFeature> PATCH_COFFEE_BUSH = ResourceKey.create(
      Registries.PLACED_FEATURE, new ResourceLocation("farmersrespite", "patch_wild_coffee_bush")
   );

   public static void bootstrap(BootstapContext<PlacedFeature> context) {
      HolderGetter<ConfiguredFeature<?, ?>> configuredFeatureLookup = context.lookup(Registries.CONFIGURED_FEATURE);
      Holder<ConfiguredFeature<?, ?>> patchWildTeaBushConfiguredFeature = configuredFeatureLookup.getOrThrow(FRFeatures.PATCH_WILD_TEA_BUSH);
      Holder<ConfiguredFeature<?, ?>> patchCoffeeBushConfiguredFeature = configuredFeatureLookup.getOrThrow(FRFeatures.PATCH_COFFEE_BUSH);
      context.register(
         PATCH_WILD_TEA_BUSH,
         new PlacedFeature(
            patchWildTeaBushConfiguredFeature,
            List.of(RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
         )
      );
      context.register(
         PATCH_COFFEE_BUSH,
         new PlacedFeature(
            patchCoffeeBushConfiguredFeature,
            List.of(RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome())
         )
      );
   }
}
