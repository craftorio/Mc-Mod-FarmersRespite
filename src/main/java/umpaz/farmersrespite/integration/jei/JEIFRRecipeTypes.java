package umpaz.farmersrespite.integration.jei;

import mezz.jei.api.recipe.RecipeType;

public class JEIFRRecipeTypes {
   public static final RecipeType<KettleUnionRecipe> BREWING = RecipeType.create("farmersrespite", "brewing", KettleUnionRecipe.class);
}
