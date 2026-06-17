package com.farmersrespite.integration.jei.category;

import com.farmersrespite.common.crafting.KettleRecipe;
import com.farmersrespite.core.registry.FRItems;
import com.farmersrespite.core.utility.FRTextUtils;
import com.mojang.blaze3d.vertex.PoseStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BrewingRecipeCategory
        implements IRecipeCategory<KettleRecipe> {
    public static final ResourceLocation UID = new ResourceLocation("farmersrespite", "brewing");


    protected final IDrawable heatIndicator;

    protected final IDrawable waterBar;

    protected final IDrawableAnimated arrow;

    private final Component title = FRTextUtils.getTranslation("jei.brewing", new Object[0]);
    private final IDrawable background;
    private final IDrawable icon;

    public BrewingRecipeCategory(IGuiHelper helper) {
        ResourceLocation backgroundImage = new ResourceLocation("farmersrespite", "textures/gui/jei/kettle_jei.png");
        this.background = helper.createDrawable(backgroundImage, 29, 16, 117, 57);
        this.icon = helper.createDrawableIngredient(new ItemStack(FRItems.KETTLE.get()));
        this.heatIndicator = helper.createDrawable(backgroundImage, 176, 0, 17, 15);
        this
                .arrow = helper.drawableBuilder(backgroundImage, 176, 15, 40, 17).buildAnimated(2400, IDrawableAnimated.StartDirection.LEFT, false);
        this.waterBar = helper.createDrawable(backgroundImage, 176, 32, 5, 9);
    }

    public ResourceLocation getUid() {
        return UID;
    }


    public Class<? extends KettleRecipe> getRecipeClass() {
        return KettleRecipe.class;
    }


    public Component getTitle() {
        return this.title;
    }


    public IDrawable getBackground() {
        return this.background;
    }


    public IDrawable getIcon() {
        return this.icon;
    }


    public void setIngredients(KettleRecipe cookingPotRecipe, IIngredients ingredients) {
        List<Ingredient> inputAndContainer = new ArrayList<>(cookingPotRecipe.getIngredients());
        inputAndContainer.add(Ingredient.of(cookingPotRecipe.getOutputContainer()));

        ingredients.setInputIngredients(inputAndContainer);
        ingredients.setOutput(VanillaTypes.ITEM, cookingPotRecipe.getResultItem());
    }


    public void setRecipe(IRecipeLayout recipeLayout, KettleRecipe recipe, IIngredients ingredients) {
        int MEAL_DISPLAY = 2;
        int CONTAINER_INPUT = 3;
        int OUTPUT = 4;
        IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();

        int borderSlotSize = 18;
        for (int row = 0; row < 2; row++) {
            for (int column = 0; column < 1; column++) {
                int inputIndex = row + column;
                if (inputIndex < recipeIngredients.size()) {
                    itemStacks.init(inputIndex, true, column * borderSlotSize + 12, row * borderSlotSize);
                    itemStacks.set(inputIndex, Arrays.asList(recipeIngredients.get(inputIndex).getItems()));
                }
            }
        }

        itemStacks.init(2, false, 88, 9);
        itemStacks.set(2, recipe.getResultItem());

        if (!recipe.getOutputContainer().isEmpty()) {
            itemStacks.init(3, false, 56, 38);
            itemStacks.set(3, recipe.getOutputContainer());
        }

        itemStacks.init(4, false, 88, 38);
        itemStacks.set(4, recipe.getResultItem());
    }


    public void draw(KettleRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
        this.arrow.draw(matrixStack, 33, 9);
        this.heatIndicator.draw(matrixStack, 13, 40);
        if (recipe.getNeedWater())
            this.waterBar.draw(matrixStack, 5, 25);
    }
}
