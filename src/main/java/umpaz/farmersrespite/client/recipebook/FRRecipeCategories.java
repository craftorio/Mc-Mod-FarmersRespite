package umpaz.farmersrespite.client.recipebook;

import com.google.common.base.Suppliers;
import java.util.function.Supplier;
import net.minecraft.client.RecipeBookCategories;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.event.RegisterRecipeBookCategoriesEvent;
import vectorwing.farmersdelight.common.registry.ModItems;

public class FRRecipeCategories {
   public static final Supplier<RecipeBookCategories> BRWEING_SEARCH = Suppliers.memoize(
      () -> RecipeBookCategories.create("BREWING_SEARCH", new ItemStack[]{new ItemStack(Items.COMPASS)})
   );
   public static final Supplier<RecipeBookCategories> BREWING_DRINKS = Suppliers.memoize(
      () -> RecipeBookCategories.create("BREWING_DRINKS", new ItemStack[]{new ItemStack((ItemLike)ModItems.APPLE_CIDER.get())})
   );

   public static void init(RegisterRecipeBookCategoriesEvent event) {
   }
}
