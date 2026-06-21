package umpaz.farmersrespite.integration.jei;

import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;
import umpaz.farmersrespite.common.crafting.KettlePouringRecipe;
import umpaz.farmersrespite.common.crafting.KettleRecipe;

public class KettleUnionRecipe extends KettleRecipe {
   private final ItemStack catalyst;
   private ItemStack output;
   private final int catalystAmount;

   KettleUnionRecipe(KettleRecipe kettleRecipe, @Nullable KettlePouringRecipe pouringRecipe) {
      super(
         kettleRecipe.getId(),
         kettleRecipe.getIngredients(),
         kettleRecipe.getFluidIn(),
         kettleRecipe.getFluidOut(),
         kettleRecipe.getExperience(),
         kettleRecipe.getBrewTime()
      );
      if (pouringRecipe != null) {
         this.output = pouringRecipe.getOutput();
      }

      if (pouringRecipe != null) {
         this.catalyst = pouringRecipe.getContainer();
         this.catalystAmount = pouringRecipe.getAmount();
      } else {
         this.catalyst = null;
         this.catalystAmount = 0;
      }
   }

   public ItemStack getCatalyst() {
      return this.catalyst;
   }

   public int getCatalystAmount() {
      return this.catalystAmount;
   }

   public ItemStack getOutput() {
      return this.output;
   }
}
