package umpaz.farmersrespite.integration.jei;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import umpaz.farmersrespite.common.crafting.KettlePouringRecipe;
import umpaz.farmersrespite.common.crafting.KettleRecipe;
import umpaz.farmersrespite.common.registry.FRRecipeTypes;

public class JEIFRRecipes {
   private final RecipeManager recipeManager;

   public JEIFRRecipes() {
      Minecraft minecraft = Minecraft.getInstance();
      ClientLevel level = minecraft.level;
      if (level != null) {
         this.recipeManager = level.getRecipeManager();
      } else {
         throw new NullPointerException("minecraft world must not be null.");
      }
   }

   public List<KettleUnionRecipe> getKettleRecipes() {
      List<KettleRecipe> kettle = this.recipeManager.getAllRecipesFor(FRRecipeTypes.BREWING.get());
      List<KettlePouringRecipe> pours = this.recipeManager.getAllRecipesFor(FRRecipeTypes.KETTLE_POURING.get());
      List<KettleUnionRecipe> kegRecipes = new ArrayList<>();

      for (KettleRecipe kettleRecipe : kettle) {
         if (kettleRecipe.getFluidOut() != null) {
            for (KettlePouringRecipe pouringRecipe : pours) {
               if (pouringRecipe.getFluid().isSame(kettleRecipe.getFluidOut().getFluid())) {
                  kegRecipes.add(new KettleUnionRecipe(kettleRecipe, pouringRecipe));
               }
            }
         } else {
            kegRecipes.add(new KettleUnionRecipe(kettleRecipe, null));
         }
      }

      return kegRecipes;
   }
}
