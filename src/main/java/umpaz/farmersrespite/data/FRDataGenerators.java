package umpaz.farmersrespite.data;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = "farmersrespite", bus = Bus.MOD)
public class FRDataGenerators {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = generator.getPackOutput();
      CompletableFuture<Provider> registries = event.getLookupProvider();
      ExistingFileHelper fileHelper = event.getExistingFileHelper();
      generator.addProvider(event.includeServer(), new DatapackRegistryProvider(output, event.getLookupProvider()));
      FRBlockTags blockTagsProvider = (FRBlockTags)generator.addProvider(event.includeServer(), new FRBlockTags(output, registries, fileHelper));
      FRItemTags itemTagsProvider = (FRItemTags)generator.addProvider(
         event.includeServer(), new FRItemTags(output, registries, blockTagsProvider.contentsGetter(), fileHelper)
      );
      generator.addProvider(event.includeServer(), new FRRecipes(output));
      generator.addProvider(event.includeClient(), new FRLang(output));
   }
}
