package umpaz.farmersrespite.integration.jei.category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.drawable.IDrawableAnimated.StartDirection;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IModIdHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.utility.FRTextUtils;
import umpaz.farmersrespite.integration.jei.JEIFRRecipeTypes;
import umpaz.farmersrespite.integration.jei.KettleUnionRecipe;
import vectorwing.farmersdelight.common.utility.ClientRenderUtils;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BrewingRecipeCategory implements IRecipeCategory<KettleUnionRecipe> {
   protected final IModIdHelper modIdHelper;
   protected final IDrawable heatIndicator;
   protected final IDrawableAnimated arrow;
   private final Component title = FRTextUtils.getTranslation("jei.brewing");
   private final IDrawable background;
   private final IDrawable icon;
   protected final IDrawable timeIcon;
   protected final IDrawable expIcon;
   protected final IDrawable kettleOverlay;

   public BrewingRecipeCategory(IGuiHelper helper, IModIdHelper modIdHelper) {
      this.modIdHelper = modIdHelper;
      ResourceLocation backgroundImage = new ResourceLocation("farmersrespite", "textures/gui/jei/kettle_jei.png");
      this.background = helper.createDrawable(backgroundImage, 29, 16, 117, 57);
      this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack((ItemLike)FRItems.KETTLE.get()));
      this.heatIndicator = helper.createDrawable(backgroundImage, 176, 0, 17, 15);
      this.arrow = helper.drawableBuilder(backgroundImage, 176, 15, 40, 17).buildAnimated(200, StartDirection.LEFT, false);
      this.expIcon = helper.createDrawable(backgroundImage, 176, 73, 9, 9);
      this.timeIcon = helper.createDrawable(backgroundImage, 176, 62, 8, 11);
      this.kettleOverlay = helper.createDrawable(backgroundImage, 176, 32, 39, 30);
   }

   public RecipeType<KettleUnionRecipe> getRecipeType() {
      return JEIFRRecipeTypes.BREWING;
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

   public void setRecipe(IRecipeLayoutBuilder builder, KettleUnionRecipe recipe, IFocusGroup focusGroup) {
      NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
      int borderSlotSize = 18;

      for (int row = 0; row < 2; row++) {
         for (int column = 0; column < 1; column++) {
            int inputIndex = row * 1 + column;
            if (inputIndex < recipeIngredients.size()) {
               builder.addSlot(RecipeIngredientRole.INPUT, column * borderSlotSize + 13, row * borderSlotSize + 1)
                  .addItemStacks(Arrays.asList(((Ingredient)recipeIngredients.get(inputIndex)).getItems()));
            }
         }
      }

      if (recipe.getFluidIn() != null) {
         ((IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.CATALYST, 3, 2)
               .addFluidStack(recipe.getFluidIn().getFluid(), recipe.getFluidIn().getAmount()))
            .setFluidRenderer(2000L, false, 6, 32);
      }

      if (recipe.getFluidOut() != null) {
         ((IRecipeSlotBuilder)builder.addSlot(RecipeIngredientRole.OUTPUT, 76, 1)
               .addFluidStack(recipe.getFluidOut().getFluid(), recipe.getFluidOut().getAmount()))
            .setFluidRenderer(2000L, false, 39, 30)
            .setOverlay(this.kettleOverlay, 0, 0);
      }

      if (recipe.getCatalyst() != null) {
         builder.addSlot(RecipeIngredientRole.CATALYST, 57, 39).addItemStack(recipe.getCatalyst());
      }

      if (recipe.getOutput() != null) {
         builder.addSlot(RecipeIngredientRole.OUTPUT, 89, 39).addItemStack(recipe.getOutput());
      }

      builder.moveRecipeTransferButton(123, 43);
   }

   public void draw(KettleUnionRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics matrixStack, double mouseX, double mouseY) {
      this.arrow.draw(matrixStack, 33, 9);
      this.heatIndicator.draw(matrixStack, 13, 40);
      this.timeIcon.draw(matrixStack, 53, 2);
      if (recipe.getExperience() > 0.0F) {
         this.expIcon.draw(matrixStack, 52, 21);
      }
   }

   public List<Component> getTooltipStrings(KettleUnionRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
      if (ClientRenderUtils.isCursorInsideBounds(34, 2, 38, 29, mouseX, mouseY)) {
         List<Component> tooltipStrings = new ArrayList<>();
         int cookTime = recipe.getBrewTime();
         if (cookTime > 0) {
            int cookTimeSeconds = cookTime / 20;
            tooltipStrings.add(Component.translatable("gui.jei.category.smelting.time.seconds", new Object[]{cookTimeSeconds}));
         }

         float experience = recipe.getExperience();
         if (experience > 0.0F) {
            tooltipStrings.add(Component.translatable("gui.jei.category.smelting.experience", new Object[]{experience}));
         }

         return tooltipStrings;
      } else {
         return ClientRenderUtils.isCursorInsideBounds(76, 39, 10, 16, mouseX, mouseY) && recipe.getCatalyst() != null
            ? Collections.singletonList(
               Component.literal(String.valueOf(recipe.getCatalystAmount())).append(I18n.get("generic.unit.millibuckets", new Object[0]))
            )
            : Collections.emptyList();
      }
   }
}
