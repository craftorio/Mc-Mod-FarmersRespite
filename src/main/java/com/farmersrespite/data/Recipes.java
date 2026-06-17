package com.farmersrespite.data;

import com.farmersrespite.core.registry.FRItems;
import com.farmersrespite.data.recipe.BrewingRecipes;
import com.farmersrespite.data.recipe.FRCookingRecipes;
import com.farmersrespite.data.recipe.FRCuttingRecipes;

import java.util.function.Consumer;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class Recipes
        extends RecipeProvider {
    public Recipes(DataGenerator generator) {
        super(generator);
    }


    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        BrewingRecipes.register(consumer);
        FRCookingRecipes.register(consumer);
        FRCuttingRecipes.register(consumer);
        recipesCrafted(consumer);
    }


    private void recipesCrafted(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(FRItems.COFFEE_CAKE.get())
                .requires(FRItems.COFFEE_CAKE_SLICE.get())
                .requires(FRItems.COFFEE_CAKE_SLICE.get())
                .requires(FRItems.COFFEE_CAKE_SLICE.get())
                .requires(FRItems.COFFEE_CAKE_SLICE.get())
                .requires(FRItems.COFFEE_CAKE_SLICE.get())
                .requires(FRItems.COFFEE_CAKE_SLICE.get())
                .requires(FRItems.COFFEE_CAKE_SLICE.get())
                .unlockedBy("has_cake_slice", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{FRItems.COFFEE_CAKE_SLICE.get()
                })).save(consumer, new ResourceLocation("farmersrespite", "coffee_cake_from_slices"));
        ShapelessRecipeBuilder.shapeless(FRItems.BLACK_COD.get())
                .requires(ForgeTags.COOKED_FISHES_COD)
                .requires(FRItems.BLACK_TEA_LEAVES.get())
                .requires(Items.BOWL)
                .requires(ForgeTags.CROPS_CABBAGE)
                .requires(ForgeTags.CROPS_RICE)
                .unlockedBy("has_cod", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{Items.COOKED_COD
                })).save(consumer);
        ShapelessRecipeBuilder.shapeless(Items.RED_DYE)
                .requires(FRItems.COFFEE_BERRIES.get())
                .unlockedBy("has_berries", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{FRItems.COFFEE_BERRIES.get()
                })).save(consumer);

        ShapedRecipeBuilder.shaped(FRItems.KETTLE.get())
                .pattern("sls")
                .pattern("bBb")
                .pattern("bbb")
                .define(Character.valueOf('s'), Items.STICK)
                .define(Character.valueOf('l'), Items.LEATHER)
                .define(Character.valueOf('b'), Items.COPPER_INGOT)
                .define(Character.valueOf('B'), Items.BUCKET)
                .unlockedBy("has_brick", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{Items.BRICK
                })).save(consumer);
        ShapedRecipeBuilder.shaped(FRItems.COFFEE_CAKE.get())
                .pattern("msm")
                .pattern("cec")
                .pattern("www")
                .define(Character.valueOf('m'), ForgeTags.MILK)
                .define(Character.valueOf('s'), Items.SUGAR)
                .define(Character.valueOf('c'), FRItems.COFFEE_BEANS.get())
                .define(Character.valueOf('e'), Items.EGG)
                .define(Character.valueOf('w'), Items.WHEAT)
                .unlockedBy("has_beans", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{FRItems.COFFEE_BEANS.get()
                })).save(consumer);
        ShapedRecipeBuilder.shaped(FRItems.ROSE_HIP_PIE.get())
                .pattern("mhm")
                .pattern("rrr")
                .pattern("sps")
                .define(Character.valueOf('m'), ForgeTags.MILK)
                .define(Character.valueOf('h'), Items.HONEY_BOTTLE)
                .define(Character.valueOf('r'), FRItems.ROSE_HIPS.get())
                .define(Character.valueOf('s'), Items.SUGAR)
                .define(Character.valueOf('p'), ModItems.PIE_CRUST.get())
                .unlockedBy("has_pie_crust", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{ModItems.PIE_CRUST.get()
                })).save(consumer);
        ShapedRecipeBuilder.shaped(FRItems.GREEN_TEA_COOKIE.get(), 8)
                .pattern("wgw")
                .define(Character.valueOf('w'), Items.WHEAT)
                .define(Character.valueOf('g'), FRItems.GREEN_TEA_LEAVES.get())
                .unlockedBy("has_leaves", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{FRItems.GREEN_TEA_LEAVES.get()
                })).save(consumer);
        ShapedRecipeBuilder.shaped(FRItems.NETHER_WART_SOURDOUGH.get())
                .pattern("nn")
                .pattern("rb")
                .define(Character.valueOf('n'), Items.NETHER_WART)
                .define(Character.valueOf('r'), Items.RED_MUSHROOM)
                .define(Character.valueOf('b'), Items.BROWN_MUSHROOM)
                .unlockedBy("has_wart", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{Items.NETHER_WART
                })).save(consumer);
        ShapedRecipeBuilder.shaped(FRItems.ROSE_HIP_PIE.get())
                .pattern("rr")
                .pattern("rr")
                .define(Character.valueOf('r'), FRItems.ROSE_HIP_PIE_SLICE.get())
                .unlockedBy("has_rose_hip_pie_slice", InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[]{FRItems.ROSE_HIP_PIE_SLICE.get()
                })).save(consumer, new ResourceLocation("farmersrespite", "rose_hip_pie_from_slices"));
    }
}
