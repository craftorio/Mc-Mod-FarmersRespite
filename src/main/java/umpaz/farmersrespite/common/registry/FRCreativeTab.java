package umpaz.farmersrespite.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.utility.FRTextUtils;

@EventBusSubscriber(modid = "farmersrespite", bus = Bus.MOD)
public class FRCreativeTab {
   public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "farmersrespite");
   public static final RegistryObject<CreativeModeTab> FARMERS_RESPITE_TAB = TABS.register(
      "main",
      () -> CreativeModeTab.builder().title(FRTextUtils.getTranslation("itemGroup.main")).icon(((Item)FRItems.KETTLE.get())::getDefaultInstance).build()
   );

   @SubscribeEvent
   public static void buildContents(BuildCreativeModeTabContentsEvent event) {
      buildMainTabContents(event);
      buildFoodAndDrinksTabContents(event);
      buildFunctionalBlocksContents(event);
      buildBuildingBlocksContents(event);
   }

   private static void buildMainTabContents(BuildCreativeModeTabContentsEvent event) {
      if (event.getTab() == FARMERS_RESPITE_TAB.get()) {
         acceptBlocks(event);
         acceptItems(event);
      }
   }

   private static void buildFoodAndDrinksTabContents(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
         acceptFoodAndDrinkItems(event);
      }
   }

   private static void buildFunctionalBlocksContents(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
         acceptFunctionalBlocks(event);
      }
   }

   private static void buildBuildingBlocksContents(BuildCreativeModeTabContentsEvent event) {
      if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
         acceptBuildingBlocks(event);
      }
   }

   private static void acceptBlocks(BuildCreativeModeTabContentsEvent event) {
      acceptFunctionalBlocks(event);
      acceptBuildingBlocks(event);
      acceptNaturalBlocks(event);
   }

   private static void acceptFunctionalBlocks(BuildCreativeModeTabContentsEvent event) {
      event.accept(FRItems.KETTLE);
   }

   private static void acceptBuildingBlocks(BuildCreativeModeTabContentsEvent event) {
      event.accept(FRItems.GREEN_TEA_LEAVES_SACK);
      event.accept(FRItems.YELLOW_TEA_LEAVES_SACK);
      event.accept(FRItems.BLACK_TEA_LEAVES_SACK);
      event.accept(FRItems.COFFEE_BEANS_SACK);
   }

   private static void acceptNaturalBlocks(BuildCreativeModeTabContentsEvent event) {
      event.accept(FRItems.WILD_TEA_BUSH);
      event.accept(FRItems.WILD_COFFEE_BUSH);
   }

   private static void acceptItems(BuildCreativeModeTabContentsEvent event) {
      acceptNaturalItems(event);
      acceptFoodAndDrinkItems(event);
   }

   private static void acceptNaturalItems(BuildCreativeModeTabContentsEvent event) {
      event.accept(FRItems.GREEN_TEA_LEAVES);
      event.accept(FRItems.YELLOW_TEA_LEAVES);
      event.accept(FRItems.BLACK_TEA_LEAVES);
      event.accept(FRItems.TEA_SEEDS);
      event.accept(FRItems.COFFEE_BERRIES);
      event.accept(FRItems.COFFEE_BEANS);
      event.accept(FRItems.ROSE_HIPS);
   }

   private static void acceptFoodAndDrinkItems(BuildCreativeModeTabContentsEvent event) {
      if (!event.getEntries().contains(((Item)FRItems.COFFEE_BERRIES.get()).getDefaultInstance())) {
         event.accept(FRItems.COFFEE_BERRIES);
      }

      event.accept(FRItems.COFFEE_CAKE);
      event.accept(FRItems.COFFEE_CAKE_SLICE);
      event.accept(FRItems.ROSE_HIP_PIE);
      event.accept(FRItems.ROSE_HIP_PIE_SLICE);
      event.accept(FRItems.GREEN_TEA);
      event.accept(FRItems.YELLOW_TEA);
      event.accept(FRItems.BLACK_TEA);
      event.accept(FRItems.ROSE_HIP_TEA);
      event.accept(FRItems.DANDELION_TEA);
      event.accept(FRItems.PURULENT_TEA);
      event.accept(FRItems.GAMBLERS_TEA);
      event.accept(FRItems.COFFEE);
      event.accept(FRItems.LONG_GREEN_TEA);
      event.accept(FRItems.LONG_YELLOW_TEA);
      event.accept(FRItems.LONG_BLACK_TEA);
      event.accept(FRItems.LONG_ROSE_HIP_TEA);
      event.accept(FRItems.LONG_DANDELION_TEA);
      event.accept(FRItems.LONG_PURULENT_TEA);
      event.accept(FRItems.LONG_GAMBLERS_TEA);
      event.accept(FRItems.LONG_COFFEE);
      event.accept(FRItems.LONG_APPLE_CIDER);
      event.accept(FRItems.STRONG_GREEN_TEA);
      event.accept(FRItems.STRONG_YELLOW_TEA);
      event.accept(FRItems.STRONG_BLACK_TEA);
      event.accept(FRItems.STRONG_ROSE_HIP_TEA);
      event.accept(FRItems.STRONG_PURULENT_TEA);
      event.accept(FRItems.STRONG_GAMBLERS_TEA);
      event.accept(FRItems.STRONG_COFFEE);
      event.accept(FRItems.STRONG_APPLE_CIDER);
      event.accept(FRItems.STRONG_MELON_JUICE);
      event.accept(FRItems.STRONG_HOT_COCOA);
      event.accept(FRItems.GREEN_TEA_COOKIE);
      event.accept(FRItems.NETHER_WART_SOURDOUGH);
      event.accept(FRItems.BLACK_COD);
      event.accept(FRItems.TEA_CURRY);
      event.accept(FRItems.BLAZING_CHILI);
   }
}
