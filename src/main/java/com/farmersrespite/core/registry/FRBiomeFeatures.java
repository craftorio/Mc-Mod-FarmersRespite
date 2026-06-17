package com.farmersrespite.core.registry;

import com.farmersrespite.common.levelgen.feature.CoffeeBushFeature;
import com.farmersrespite.common.levelgen.feature.WildTeaBushFeature;
import com.farmersrespite.core.FRConfiguration;

import java.util.List;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class FRBiomeFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, "farmersrespite");

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> WILD_TEA_BUSH = FEATURES.register("wild_tea_bush", () -> new WildTeaBushFeature(SimpleBlockConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> COFFEE_BUSH = FEATURES.register("coffee_bush", () -> new CoffeeBushFeature(NoneFeatureConfiguration.CODEC));

    public static final class FarmersRespiteConfiguredFeatures {
        public static final Holder<ConfiguredFeature<SimpleBlockConfiguration, ?>> PATCH_WILD_TEA_BUSH = register(new ResourceLocation("farmersrespite", "patch_wild_tea_bush"), Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                BlockStateProvider.simple(FRBlocks.WILD_TEA_BUSH.get().defaultBlockState())));
        public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> PATCH_COFFEE_BUSH = register(new ResourceLocation("farmersrespite", "patch_coffee_bush"), FRBiomeFeatures.COFFEE_BUSH
                .get(), FeatureConfiguration.NONE);

        static Holder<PlacedFeature> registerPlacement(ResourceLocation id, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
            return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, id, new PlacedFeature(Holder.hackyErase(feature), List.of(modifiers)));
        }

        private static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(ResourceLocation id, F feature, FC featureConfig) {
            return register(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature(feature, featureConfig));
        }

        private static <V extends T, T> Holder<V> register(Registry<T> registry, ResourceLocation id, V value) {
            return (Holder<V>) BuiltinRegistries.register(registry, id, value);
        }

        public static final class FarmersRespitePlacedFeatures {
            public static final Holder<PlacedFeature> PATCH_WILD_TEA_BUSH = FRBiomeFeatures.FarmersRespiteConfiguredFeatures.registerPlacement(new ResourceLocation("farmersrespite", "patch_wild_tea_bush"), FRBiomeFeatures.FarmersRespiteConfiguredFeatures.PATCH_WILD_TEA_BUSH, RarityFilter.onAverageOnceEvery(20 - FRConfiguration.CHANCE_TEA_BUSH.get().intValue()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
            public static final Holder<PlacedFeature> PATCH_COFFEE_BUSH = FRBiomeFeatures.FarmersRespiteConfiguredFeatures.registerPlacement(new ResourceLocation("farmersrespite", "patch_coffee_bush"), FRBiomeFeatures.FarmersRespiteConfiguredFeatures.PATCH_COFFEE_BUSH, CountPlacement.of((int) (Math.sqrt(FRConfiguration.CHANCE_COFFEE_BUSH.get().intValue()) * 3.0D - 4.0D)), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
        }
    }

    public static final class FarmersRespitePlacedFeatures {
        public static final Holder<PlacedFeature> PATCH_COFFEE_BUSH = FRBiomeFeatures.FarmersRespiteConfiguredFeatures.registerPlacement(new ResourceLocation("farmersrespite", "patch_coffee_bush"), FRBiomeFeatures.FarmersRespiteConfiguredFeatures.PATCH_COFFEE_BUSH, CountPlacement.of((int) (Math.sqrt(FRConfiguration.CHANCE_COFFEE_BUSH.get().intValue()) * 3.0D - 4.0D)), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());
        public static final Holder<PlacedFeature> PATCH_WILD_TEA_BUSH = FRBiomeFeatures.FarmersRespiteConfiguredFeatures.registerPlacement(new ResourceLocation("farmersrespite", "patch_wild_tea_bush"), FRBiomeFeatures.FarmersRespiteConfiguredFeatures.PATCH_WILD_TEA_BUSH, RarityFilter.onAverageOnceEvery(20 - FRConfiguration.CHANCE_TEA_BUSH.get().intValue()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

}