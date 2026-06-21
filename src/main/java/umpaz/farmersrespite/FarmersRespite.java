package umpaz.farmersrespite;

import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import umpaz.farmersrespite.common.FRConfiguration;
import umpaz.farmersrespite.common.registry.FRBlockEntityTypes;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRCreativeTab;
import umpaz.farmersrespite.common.registry.FREffects;
import umpaz.farmersrespite.common.registry.FRFluids;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.registry.FRMenuTypes;
import umpaz.farmersrespite.common.registry.FRRecipeSerializers;
import umpaz.farmersrespite.common.registry.FRRecipeTypes;
import umpaz.farmersrespite.common.registry.FRSounds;
import umpaz.farmersrespite.common.registry.FRWorldgenFeatures;

@Mod("farmersrespite")
public class FarmersRespite {
   public static final String MODID = "farmersrespite";
   public static final Logger LOGGER = LogManager.getLogger();
   public static final RecipeBookType RECIPE_TYPE_BREWING = RecipeBookType.create("BREWING");

   public FarmersRespite() {
      IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
      ModLoadingContext.get().registerConfig(Type.COMMON, FRConfiguration.COMMON_CONFIG);
      FREffects.EFFECTS.register(modEventBus);
      FRItems.ITEMS.register(modEventBus);
      FRBlocks.BLOCKS.register(modEventBus);
      FRFluids.FLUIDS.register(modEventBus);
      FRFluids.FLUID_TYPES.register(modEventBus);
      FRWorldgenFeatures.FEATURES.register(modEventBus);
      FRBlockEntityTypes.TILES.register(modEventBus);
      FRMenuTypes.MENU_TYPES.register(modEventBus);
      FRRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
      FRRecipeTypes.RECIPE_TYPES.register(modEventBus);
      FRSounds.SOUNDS.register(modEventBus);
      FRCreativeTab.TABS.register(modEventBus);
      MinecraftForge.EVENT_BUS.register(this);
   }
}
