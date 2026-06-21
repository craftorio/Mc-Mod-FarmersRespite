package umpaz.farmersrespite.data;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import umpaz.farmersrespite.data.worldgen.biome.FRBiomeModifiers;
import umpaz.farmersrespite.data.worldgen.features.FRFeatures;
import umpaz.farmersrespite.data.worldgen.placement.FRPlacements;

public class DatapackRegistryProvider extends DatapackBuiltinEntriesProvider {
   public static final RegistrySetBuilder REGISTRIES = new RegistrySetBuilder()
      .add(Registries.DAMAGE_TYPE, FRDamageTypes::bootstrap)
      .add(Registries.CONFIGURED_FEATURE, FRFeatures::bootstrap)
      .add(Registries.PLACED_FEATURE, FRPlacements::bootstrap)
      .add(Keys.BIOME_MODIFIERS, FRBiomeModifiers::bootstrap);

   public DatapackRegistryProvider(PackOutput output, CompletableFuture<Provider> provider) {
      super(output, provider, REGISTRIES, Set.of("minecraft", "farmersrespite"));
   }
}
