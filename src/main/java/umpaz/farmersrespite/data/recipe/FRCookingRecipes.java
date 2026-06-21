package umpaz.farmersrespite.data.recipe;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.data.builder.FRCookingPotRecipeBuilder;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class FRCookingRecipes {
   public static final int FAST_COOKING = 100;
   public static final int NORMAL_COOKING = 200;
   public static final int SLOW_COOKING = 400;

   public static void register(Consumer<FinishedRecipe> consumer) {
      cookMiscellaneous(consumer);
   }

   private static void cookMiscellaneous(Consumer<FinishedRecipe> consumer) {
      FRCookingPotRecipeBuilder.cookingPotRecipe((ItemLike)FRItems.BLAZING_CHILI.get(), 1, 200, 0.35F)
         .addIngredient(Items.BLAZE_POWDER)
         .addIngredient(Items.BLAZE_POWDER)
         .addIngredient(Items.NETHER_WART)
         .addIngredient(Items.NETHER_WART)
         .addIngredient((ItemLike)FRItems.COFFEE_BEANS.get())
         .addIngredient(ForgeTags.RAW_BEEF)
         .unlockedByAnyIngredient(Items.BLAZE_POWDER, Items.NETHER_WART, Items.BEEF, (ItemLike)FRItems.COFFEE_BEANS.get())
         .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
         .build(consumer);
      FRCookingPotRecipeBuilder.cookingPotRecipe((ItemLike)FRItems.TEA_CURRY.get(), 1, 200, 0.35F)
         .addIngredient((ItemLike)FRItems.YELLOW_TEA_LEAVES.get())
         .addIngredient((ItemLike)FRItems.YELLOW_TEA_LEAVES.get())
         .addIngredient(ForgeTags.RAW_CHICKEN)
         .addIngredient(ForgeTags.CROPS_CABBAGE)
         .addIngredient(ForgeTags.CROPS_ONION)
         .addIngredient(ForgeTags.CROPS_RICE)
         .unlockedByAnyIngredient(
            Items.CHICKEN,
            (ItemLike)ModItems.CABBAGE.get(),
            (ItemLike)ModItems.ONION.get(),
            (ItemLike)ModItems.RICE.get(),
            (ItemLike)FRItems.YELLOW_TEA_LEAVES.get()
         )
         .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
         .build(consumer);
   }
}
