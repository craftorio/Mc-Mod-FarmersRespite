package umpaz.farmersrespite.data.recipe;

import java.util.function.Consumer;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.data.builder.FRCuttingBoardRecipeBuilder;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class FRCuttingRecipes {
   public static void register(Consumer<FinishedRecipe> consumer) {
      FRCuttingBoardRecipeBuilder.cuttingRecipe(
            Ingredient.of(new ItemLike[]{(ItemLike)FRItems.COFFEE_BERRIES.get()}),
            Ingredient.of(ForgeTags.TOOLS_KNIVES),
            (ItemLike)FRItems.COFFEE_BEANS.get(),
            1
         )
         .build(consumer);
      FRCuttingBoardRecipeBuilder.cuttingRecipe(
            Ingredient.of(new ItemLike[]{(ItemLike)FRItems.COFFEE_CAKE.get()}),
            Ingredient.of(ForgeTags.TOOLS_KNIVES),
            (ItemLike)FRItems.COFFEE_CAKE_SLICE.get(),
            7
         )
         .build(consumer);
      FRCuttingBoardRecipeBuilder.cuttingRecipe(
            Ingredient.of(new ItemLike[]{(ItemLike)FRItems.ROSE_HIP_PIE.get()}),
            Ingredient.of(ForgeTags.TOOLS_KNIVES),
            (ItemLike)FRItems.ROSE_HIP_PIE_SLICE.get(),
            4
         )
         .build(consumer);
      FRCuttingBoardRecipeBuilder.cuttingRecipe(
            Ingredient.of(new ItemLike[]{Items.ROSE_BUSH}), Ingredient.of(ForgeTags.TOOLS_KNIVES), (ItemLike)FRItems.ROSE_HIPS.get(), 2
         )
         .build(consumer);
   }
}
