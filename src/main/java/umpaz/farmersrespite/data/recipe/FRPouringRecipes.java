package umpaz.farmersrespite.data.recipe;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import umpaz.farmersrespite.common.registry.FRFluids;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.data.builder.KettlePouringRecipeBuilder;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FRPouringRecipes {
   public static void register(Consumer<FinishedRecipe> consumer) {
      pouringRecipes(consumer);
   }

   private static void pouringRecipes(Consumer<FinishedRecipe> consumer) {
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.GREEN_TEA.get(), 250, (ItemLike)FRItems.GREEN_TEA.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.YELLOW_TEA.get(), 250, (ItemLike)FRItems.YELLOW_TEA.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.BLACK_TEA.get(), 250, (ItemLike)FRItems.BLACK_TEA.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.ROSE_HIP_TEA.get(), 250, (ItemLike)FRItems.ROSE_HIP_TEA.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.DANDELION_TEA.get(), 250, (ItemLike)FRItems.DANDELION_TEA.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.PURULENT_TEA.get(), 250, (ItemLike)FRItems.PURULENT_TEA.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.GAMBLERS_TEA.get(), 250, (ItemLike)FRItems.GAMBLERS_TEA.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.COFFEE.get(), 250, (ItemLike)FRItems.COFFEE.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.APPLE_CIDER.get(), 250, (ItemLike)ModItems.APPLE_CIDER.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.MELON_JUICE.get(), 250, (ItemLike)ModItems.MELON_JUICE.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.HOT_COCOA.get(), 250, (ItemLike)ModItems.HOT_COCOA.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.LONG_GREEN_TEA.get(), 250, (ItemLike)FRItems.LONG_GREEN_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.LONG_YELLOW_TEA.get(), 250, (ItemLike)FRItems.LONG_YELLOW_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.LONG_BLACK_TEA.get(), 250, (ItemLike)FRItems.LONG_BLACK_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.LONG_ROSE_HIP_TEA.get(), 250, (ItemLike)FRItems.LONG_ROSE_HIP_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.LONG_DANDELION_TEA.get(), 250, (ItemLike)FRItems.LONG_DANDELION_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.LONG_PURULENT_TEA.get(), 250, (ItemLike)FRItems.LONG_PURULENT_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.LONG_GAMBLERS_TEA.get(), 250, (ItemLike)FRItems.LONG_GAMBLERS_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.LONG_COFFEE.get(), 250, (ItemLike)FRItems.LONG_COFFEE.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.LONG_APPLE_CIDER.get(), 250, (ItemLike)FRItems.LONG_APPLE_CIDER.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_GREEN_TEA.get(), 250, (ItemLike)FRItems.STRONG_GREEN_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_YELLOW_TEA.get(), 250, (ItemLike)FRItems.STRONG_YELLOW_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_BLACK_TEA.get(), 250, (ItemLike)FRItems.STRONG_BLACK_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_ROSE_HIP_TEA.get(), 250, (ItemLike)FRItems.STRONG_ROSE_HIP_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_PURULENT_TEA.get(), 250, (ItemLike)FRItems.STRONG_PURULENT_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_GAMBLERS_TEA.get(), 250, (ItemLike)FRItems.STRONG_GAMBLERS_TEA.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_COFFEE.get(), 250, (ItemLike)FRItems.STRONG_COFFEE.get(), consumer);
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_APPLE_CIDER.get(), 250, (ItemLike)FRItems.STRONG_APPLE_CIDER.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_MELON_JUICE.get(), 250, (ItemLike)FRItems.STRONG_MELON_JUICE.get(), consumer
      );
      KettlePouringRecipeBuilder.kettlePouringRecipe(
         Items.GLASS_BOTTLE, (Fluid)FRFluids.STRONG_HOT_COCOA.get(), 250, (ItemLike)FRItems.STRONG_HOT_COCOA.get(), consumer
      );
   }
}
