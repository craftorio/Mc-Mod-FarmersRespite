package umpaz.farmersrespite.data.recipe;

import java.util.function.Consumer;
import net.minecraft.advancements.critereon.InventoryChangeTrigger.TriggerInstance;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import umpaz.farmersrespite.common.registry.FRItems;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

public class FRCraftingRecipes {
   public static void register(Consumer<FinishedRecipe> consumer) {
      recipesCrafted(consumer);
   }

   private static void recipesCrafted(Consumer<FinishedRecipe> consumer) {
      ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, (ItemLike)FRItems.COFFEE_CAKE.get())
         .requires((ItemLike)FRItems.COFFEE_CAKE_SLICE.get())
         .requires((ItemLike)FRItems.COFFEE_CAKE_SLICE.get())
         .requires((ItemLike)FRItems.COFFEE_CAKE_SLICE.get())
         .requires((ItemLike)FRItems.COFFEE_CAKE_SLICE.get())
         .requires((ItemLike)FRItems.COFFEE_CAKE_SLICE.get())
         .requires((ItemLike)FRItems.COFFEE_CAKE_SLICE.get())
         .requires((ItemLike)FRItems.COFFEE_CAKE_SLICE.get())
         .unlockedBy("has_cake_slice", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.COFFEE_CAKE_SLICE.get()}))
         .save(consumer, new ResourceLocation("farmersrespite", "coffee_cake_from_slices"));
      ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, (ItemLike)FRItems.BLACK_COD.get())
         .requires(ForgeTags.COOKED_FISHES_COD)
         .requires((ItemLike)FRItems.BLACK_TEA_LEAVES.get())
         .requires(Items.BOWL)
         .requires(ForgeTags.CROPS_CABBAGE)
         .requires((ItemLike)ModItems.COOKED_RICE.get())
         .unlockedBy("has_cod", TriggerInstance.hasItems(new ItemLike[]{Items.COOKED_COD}))
         .save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.RED_DYE)
         .requires((ItemLike)FRItems.COFFEE_BERRIES.get())
         .unlockedBy("has_berries", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.COFFEE_BERRIES.get()}))
         .save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)FRItems.GREEN_TEA_LEAVES.get(), 9)
         .requires((ItemLike)FRItems.GREEN_TEA_LEAVES_SACK.get())
         .unlockedBy("has_sack", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.GREEN_TEA_LEAVES_SACK.get()}))
         .save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)FRItems.YELLOW_TEA_LEAVES.get(), 9)
         .requires((ItemLike)FRItems.YELLOW_TEA_LEAVES_SACK.get())
         .unlockedBy("has_sack", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.YELLOW_TEA_LEAVES_SACK.get()}))
         .save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)FRItems.BLACK_TEA_LEAVES.get(), 9)
         .requires((ItemLike)FRItems.BLACK_TEA_LEAVES_SACK.get())
         .unlockedBy("has_sack", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.BLACK_TEA_LEAVES_SACK.get()}))
         .save(consumer);
      ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, (ItemLike)FRItems.COFFEE_BEANS.get(), 9)
         .requires((ItemLike)FRItems.COFFEE_BEANS_SACK.get())
         .unlockedBy("has_sack", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.COFFEE_BEANS_SACK.get()}))
         .save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, (ItemLike)FRItems.KETTLE.get())
         .pattern("sls")
         .pattern("bBb")
         .pattern("bbb")
         .define('s', Items.STICK)
         .define('l', Items.LEATHER)
         .define('b', Items.COPPER_INGOT)
         .define('B', Items.BUCKET)
         .unlockedBy("has_brick", TriggerInstance.hasItems(new ItemLike[]{Items.BRICK}))
         .save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, (ItemLike)FRItems.COFFEE_CAKE.get())
         .pattern("msm")
         .pattern("cec")
         .pattern("www")
         .define('m', ForgeTags.MILK)
         .define('s', Items.SUGAR)
         .define('c', (ItemLike)FRItems.COFFEE_BEANS.get())
         .define('e', Items.EGG)
         .define('w', Items.WHEAT)
         .unlockedBy("has_beans", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.COFFEE_BEANS.get()}))
         .save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, (ItemLike)FRItems.ROSE_HIP_PIE.get())
         .pattern("mhm")
         .pattern("rrr")
         .pattern("sps")
         .define('m', ForgeTags.MILK)
         .define('h', Items.HONEY_BOTTLE)
         .define('r', (ItemLike)FRItems.ROSE_HIPS.get())
         .define('s', Items.SUGAR)
         .define('p', (ItemLike)ModItems.PIE_CRUST.get())
         .unlockedBy("has_pie_crust", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)ModItems.PIE_CRUST.get()}))
         .save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, (ItemLike)FRItems.GREEN_TEA_COOKIE.get(), 8)
         .pattern("wgw")
         .define('w', Items.WHEAT)
         .define('g', (ItemLike)FRItems.GREEN_TEA_LEAVES.get())
         .unlockedBy("has_leaves", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.GREEN_TEA_LEAVES.get()}))
         .save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, (ItemLike)FRItems.NETHER_WART_SOURDOUGH.get())
         .pattern("nn")
         .pattern("rb")
         .define('n', Items.NETHER_WART)
         .define('r', Items.RED_MUSHROOM)
         .define('b', Items.BROWN_MUSHROOM)
         .unlockedBy("has_wart", TriggerInstance.hasItems(new ItemLike[]{Items.NETHER_WART}))
         .save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, (ItemLike)FRItems.ROSE_HIP_PIE.get())
         .pattern("rr")
         .pattern("rr")
         .define('r', (ItemLike)FRItems.ROSE_HIP_PIE_SLICE.get())
         .unlockedBy("has_rose_hip_pie_slice", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.ROSE_HIP_PIE_SLICE.get()}))
         .save(consumer, new ResourceLocation("farmersrespite", "rose_hip_pie_from_slices"));
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)FRItems.GREEN_TEA_LEAVES_SACK.get())
         .pattern("lll")
         .pattern("lll")
         .pattern("lll")
         .define('l', (ItemLike)FRItems.GREEN_TEA_LEAVES.get())
         .unlockedBy("has_leaves", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.GREEN_TEA_LEAVES.get()}))
         .save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)FRItems.YELLOW_TEA_LEAVES_SACK.get())
         .pattern("lll")
         .pattern("lll")
         .pattern("lll")
         .define('l', (ItemLike)FRItems.YELLOW_TEA_LEAVES.get())
         .unlockedBy("has_leaves", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.YELLOW_TEA_LEAVES.get()}))
         .save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)FRItems.BLACK_TEA_LEAVES_SACK.get())
         .pattern("lll")
         .pattern("lll")
         .pattern("lll")
         .define('l', (ItemLike)FRItems.BLACK_TEA_LEAVES.get())
         .unlockedBy("has_leaves", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.BLACK_TEA_LEAVES.get()}))
         .save(consumer);
      ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, (ItemLike)FRItems.COFFEE_BEANS_SACK.get())
         .pattern("lll")
         .pattern("lll")
         .pattern("lll")
         .define('l', (ItemLike)FRItems.COFFEE_BEANS.get())
         .unlockedBy("has_leaves", TriggerInstance.hasItems(new ItemLike[]{(ItemLike)FRItems.COFFEE_BEANS.get()}))
         .save(consumer);
   }
}
