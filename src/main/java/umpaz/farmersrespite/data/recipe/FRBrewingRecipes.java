package umpaz.farmersrespite.data.recipe;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidStack;
import umpaz.farmersrespite.common.registry.FRFluids;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.tag.FRTags;
import umpaz.farmersrespite.data.builder.KettleRecipeBuilder;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class FRBrewingRecipes {
   public static void register(Consumer<FinishedRecipe> consumer) {
      cookMiscellaneous(consumer);
   }

   private static void cookMiscellaneous(Consumer<FinishedRecipe> consumer) {
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.GREEN_TEA.get(), 1000), 2400, 0.35F)
         .addIngredient((ItemLike)FRItems.GREEN_TEA_LEAVES.get())
         .addIngredient((ItemLike)FRItems.GREEN_TEA_LEAVES.get())
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.YELLOW_TEA.get(), 1000), 2400, 0.35F)
         .addIngredient((ItemLike)FRItems.YELLOW_TEA_LEAVES.get())
         .addIngredient((ItemLike)FRItems.YELLOW_TEA_LEAVES.get())
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.BLACK_TEA.get(), 1000), 2400, 0.35F)
         .addIngredient((ItemLike)FRItems.BLACK_TEA_LEAVES.get())
         .addIngredient((ItemLike)FRItems.BLACK_TEA_LEAVES.get())
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.ROSE_HIP_TEA.get(), 1000), 2400, 0.35F)
         .addIngredient((ItemLike)FRItems.ROSE_HIPS.get())
         .addIngredient((ItemLike)FRItems.ROSE_HIPS.get())
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.DANDELION_TEA.get(), 1000), 2400, 0.35F)
         .addIngredient(Items.DANDELION)
         .addIngredient(FRTags.TEA_LEAVES)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.PURULENT_TEA.get(), 1000), 2400, 0.35F)
         .addIngredient(Items.NETHER_WART)
         .addIngredient(Items.FERMENTED_SPIDER_EYE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.GAMBLERS_TEA.get(), 1000), 2400, 0.35F)
         .addIngredient((ItemLike)FRItems.COFFEE_BERRIES.get())
         .addIngredient(Items.GLOW_BERRIES)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.COFFEE.get(), 1000), 2400, 0.35F)
         .addIngredient((ItemLike)FRItems.COFFEE_BEANS.get())
         .addIngredient((ItemLike)FRItems.COFFEE_BEANS.get())
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.APPLE_CIDER.get(), 1000), 2400, 0.35F)
         .addIngredient(Items.APPLE)
         .addIngredient(Items.SUGAR)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack(Fluids.WATER, 1000), new FluidStack((Fluid)FRFluids.MELON_JUICE.get(), 1000), 2400, 0.35F)
         .addIngredient(Items.MELON_SLICE)
         .addIngredient(Items.SUGAR)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack((Fluid)ForgeMod.MILK.get(), 1000), new FluidStack((Fluid)FRFluids.HOT_COCOA.get(), 1000), 2400, 0.35F)
         .addIngredient(Items.COCOA_BEANS)
         .addIngredient(Items.SUGAR)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.GREEN_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.LONG_GREEN_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(ForgeTags.MILK)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.YELLOW_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.LONG_YELLOW_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(ForgeTags.MILK)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.BLACK_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.LONG_BLACK_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(ForgeTags.MILK)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.ROSE_HIP_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.LONG_ROSE_HIP_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(ForgeTags.MILK)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.DANDELION_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.LONG_DANDELION_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(ForgeTags.MILK)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.PURULENT_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.LONG_PURULENT_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(ForgeTags.MILK)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.GAMBLERS_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.LONG_GAMBLERS_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(ForgeTags.MILK)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(new FluidStack((Fluid)FRFluids.COFFEE.get(), 1000), new FluidStack((Fluid)FRFluids.LONG_COFFEE.get(), 1000), 2400, 0.35F)
         .addIngredient(ForgeTags.MILK)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.APPLE_CIDER.get(), 1000), new FluidStack((Fluid)FRFluids.LONG_APPLE_CIDER.get(), 1000), 2400, 0.35F
         )
         .addIngredient(ForgeTags.MILK)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.GREEN_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_GREEN_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.YELLOW_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_YELLOW_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.BLACK_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_BLACK_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.PURULENT_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_PURULENT_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.ROSE_HIP_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_ROSE_HIP_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.GAMBLERS_TEA.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_GAMBLERS_TEA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.COFFEE.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_COFFEE.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.APPLE_CIDER.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_APPLE_CIDER.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.HOT_COCOA.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_HOT_COCOA.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
      KettleRecipeBuilder.kettleRecipe(
            new FluidStack((Fluid)FRFluids.MELON_JUICE.get(), 1000), new FluidStack((Fluid)FRFluids.STRONG_MELON_JUICE.get(), 1000), 2400, 0.35F
         )
         .addIngredient(Items.HONEY_BOTTLE)
         .build(consumer);
   }
}
