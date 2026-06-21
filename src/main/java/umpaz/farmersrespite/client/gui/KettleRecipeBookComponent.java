package umpaz.farmersrespite.client.gui;

import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import umpaz.farmersrespite.common.utility.FRTextUtils;

public class KettleRecipeBookComponent extends RecipeBookComponent {
   protected static final ResourceLocation RECIPE_BOOK_BUTTONS = new ResourceLocation("farmersrespite", "textures/gui/recipe_book_buttons.png");

   protected void initFilterButtonTextures() {
      this.filterButton.initTextureValues(0, 0, 28, 18, RECIPE_BOOK_BUTTONS);
   }

   public void hide() {
      this.setVisible(false);
   }

   @Nonnull
   protected Component getRecipeFilterName() {
      return FRTextUtils.getTranslation("container.recipe_book.brewable");
   }

   public void setupGhostRecipe(Recipe<?> recipe, List<Slot> slots) {
      ItemStack resultStack = recipe.getResultItem(this.minecraft.level.registryAccess());
      this.ghostRecipe.setRecipe(recipe);
      if (slots.get(2).getItem().isEmpty()) {
         this.ghostRecipe.addIngredient(Ingredient.of(new ItemStack[]{resultStack}), slots.get(2).x, slots.get(2).y);
      }

      this.placeRecipe(this.menu.getGridWidth(), this.menu.getGridHeight(), this.menu.getResultSlotIndex(), recipe, recipe.getIngredients().iterator(), 0);
   }
}
