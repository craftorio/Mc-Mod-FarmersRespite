package umpaz.farmersrespite.integration.jei;

import java.util.Optional;
import javax.annotation.ParametersAreNonnullByDefault;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.registration.IRecipeTransferRegistration;
import mezz.jei.api.runtime.IClickableIngredient;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;
import umpaz.farmersrespite.client.gui.KettleScreen;
import umpaz.farmersrespite.common.block.entity.container.KettleMenu;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.registry.FRMenuTypes;
import umpaz.farmersrespite.integration.jei.category.BrewingRecipeCategory;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@JeiPlugin
public class JEIFRPlugin implements IModPlugin {
   private static final ResourceLocation ID = new ResourceLocation("farmersrespite", "jei_plugin");

   public void registerCategories(IRecipeCategoryRegistration registry) {
      registry.addRecipeCategories(
         new IRecipeCategory[]{new BrewingRecipeCategory(registry.getJeiHelpers().getGuiHelper(), registry.getJeiHelpers().getModIdHelper())}
      );
   }

   public void registerRecipes(IRecipeRegistration registration) {
      JEIFRRecipes modRecipes = new JEIFRRecipes();
      registration.addRecipes(JEIFRRecipeTypes.BREWING, modRecipes.getKettleRecipes());
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
      registration.addRecipeCatalyst(new ItemStack((ItemLike)FRItems.KETTLE.get()), new RecipeType[]{JEIFRRecipeTypes.BREWING});
   }

   public void registerGuiHandlers(final IGuiHandlerRegistration registration) {
      registration.addRecipeClickArea(KettleScreen.class, 62, 25, 40, 17, new RecipeType[]{JEIFRRecipeTypes.BREWING});
      final Rect2i bounds = new Rect2i(106, 18, 37, 28);
      registration.addGuiContainerHandler(
         KettleScreen.class,
         new IGuiContainerHandler<KettleScreen>() {
            public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(final KettleScreen containerScreen, double mouseX, double mouseY) {
               return bounds.contains((int)mouseX - containerScreen.getGuiLeft(), (int)mouseY - containerScreen.getGuiTop())
                  ? Optional.of(
                     new IClickableIngredient<FluidStack>() {
                        public ITypedIngredient<FluidStack> getTypedIngredient() {
                           Optional<ITypedIngredient<FluidStack>> aef = registration.getJeiHelpers()
                              .getIngredientManager()
                              .createTypedIngredient(ForgeTypes.FLUID_STACK, ((KettleMenu)containerScreen.getMenu()).tileEntity.getFluidTank().getFluid());
                           return aef.orElse(null);
                        }

                        public Rect2i getArea() {
                           return bounds;
                        }
                     }
                  )
                  : Optional.empty();
            }
         }
      );
   }

   public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
      registration.addRecipeTransferHandler(KettleMenu.class, (MenuType)FRMenuTypes.KETTLE.get(), JEIFRRecipeTypes.BREWING, 0, 2, 3, 36);
   }

   public ResourceLocation getPluginUid() {
      return ID;
   }
}
