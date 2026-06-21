package umpaz.farmersrespite.data;

import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import umpaz.farmersrespite.data.recipe.FRBrewingRecipes;
import umpaz.farmersrespite.data.recipe.FRCookingRecipes;
import umpaz.farmersrespite.data.recipe.FRCraftingRecipes;
import umpaz.farmersrespite.data.recipe.FRCuttingRecipes;
import umpaz.farmersrespite.data.recipe.FRPouringRecipes;

public class FRRecipes extends RecipeProvider {
   public FRRecipes(PackOutput output) {
      super(output);
   }

   protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
      FRCraftingRecipes.register(consumer);
      FRBrewingRecipes.register(consumer);
      FRCookingRecipes.register(consumer);
      FRCuttingRecipes.register(consumer);
      FRPouringRecipes.register(consumer);
   }
}
