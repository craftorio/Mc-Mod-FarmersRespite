package umpaz.farmersrespite.common.registry;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import umpaz.farmersrespite.data.worldgen.features.CoffeeBushFeature;

public class FRWorldgenFeatures {
   public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Keys.FEATURES, "farmersrespite");
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> COFFEE = FEATURES.register(
      "coffee_bush", () -> new CoffeeBushFeature(NoneFeatureConfiguration.CODEC)
   );
}
