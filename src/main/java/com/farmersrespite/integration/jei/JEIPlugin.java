package com.farmersrespite.integration.jei;

import com.farmersrespite.client.gui.KettleScreen;
import com.farmersrespite.common.block.entity.container.KettleContainer;
import com.farmersrespite.core.registry.FRItems;
import com.farmersrespite.core.registry.FRRecipeTypes;
import com.farmersrespite.integration.jei.category.BrewingRecipeCategory;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.ParametersAreNonnullByDefault;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@JeiPlugin
public class JEIPlugin
        implements IModPlugin {
    private static final ResourceLocation ID = new ResourceLocation("farmersrespite", "jei_plugin");
    private static final Minecraft MC = Minecraft.getInstance();

    private static List<Recipe<?>> findRecipesByType(RecipeType<?> type) {
        return MC.level
                .getRecipeManager()
                .getRecipes()
                .stream()
                .filter(r -> (r.getType() == type))
                .collect(Collectors.toList());
    }


    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new BrewingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }


    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(findRecipesByType(FRRecipeTypes.BREWING.get()), BrewingRecipeCategory.UID);
    }


    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(FRItems.KETTLE.get()), BrewingRecipeCategory.UID);
    }


    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(KettleScreen.class, 62, 25, 40, 17, BrewingRecipeCategory.UID);
    }


    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(KettleContainer.class, BrewingRecipeCategory.UID, 0, 2, 5, 36);
    }


    public ResourceLocation getPluginUid() {
        return ID;
    }
}
