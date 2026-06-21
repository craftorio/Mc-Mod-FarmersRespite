package umpaz.farmersrespite.common.crafting;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.registry.FRRecipeSerializers;
import umpaz.farmersrespite.common.registry.FRRecipeTypes;

public class KettlePouringRecipe implements Recipe<RecipeWrapper> {
   private final ResourceLocation id;
   private final Fluid fluid;
   private final int amount;
   private final ItemStack container;
   private final ItemStack output;

   public KettlePouringRecipe(ResourceLocation id, Fluid fluid, ItemStack container, ItemStack output, int amount) {
      this.id = id;
      this.amount = amount;
      this.fluid = fluid;
      this.container = container;
      this.output = output;
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public NonNullList<Ingredient> getIngredients() {
      NonNullList<Ingredient> ingredient = NonNullList.create();
      ingredient.add(Ingredient.of(new ItemStack[]{this.container}));
      return ingredient;
   }

   public boolean matches(RecipeWrapper inv, Level level) {
      return Ingredient.of(new ItemStack[]{this.container}).test(inv.getItem(4));
   }

   public ItemStack assemble(RecipeWrapper recipeWrapper, RegistryAccess registryAccess) {
      return this.output.copy();
   }

   public boolean canCraftInDimensions(int pWidth, int pHeight) {
      return true;
   }

   public ItemStack getContainer() {
      return this.container;
   }

   public ItemStack getOutput() {
      return this.output;
   }

   public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
      return this.output;
   }

   public int getAmount() {
      return this.amount;
   }

   public Fluid getFluid() {
      return this.fluid;
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)FRRecipeSerializers.KETTLE_POURING.get();
   }

   public RecipeType<?> getType() {
      return (RecipeType<?>)FRRecipeTypes.KETTLE_POURING.get();
   }

   public ItemStack getToastSymbol() {
      return new ItemStack((ItemLike)FRItems.KETTLE.get());
   }

   @Override
   public int hashCode() {
      int result = this.getId().hashCode();
      result = 31 * result + this.container.hashCode();
      result = 31 * result + this.fluid.hashCode();
      return 31 * result + this.amount;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }

      if (o != null && this.getClass() == o.getClass()) {
         KettlePouringRecipe that = (KettlePouringRecipe)o;
         if (!this.getId().equals(that.getId())) {
            return false;
         } else if (!this.output.equals(that.output)) {
            return false;
         } else if (this.amount != that.amount) {
            return false;
         } else {
            return !this.fluid.equals(that.fluid) ? false : this.container.equals(that.container);
         }
      } else {
         return false;
      }
   }

   public static class Serializer implements RecipeSerializer<KettlePouringRecipe> {
      public KettlePouringRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         Fluid fluidIn = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(GsonHelper.getAsString(json, "fluid")));
         int amountIn = GsonHelper.getAsInt(json, "amount", 250);
         ItemStack container = GsonHelper.isValidNode(json, "container")
            ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "container"), true)
            : ItemStack.EMPTY;
         ItemStack outputIn = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "output"), true);
         return new KettlePouringRecipe(recipeId, fluidIn, container, outputIn, amountIn);
      }

      @Nullable
      public KettlePouringRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         Fluid fluidIn = buffer.readFluidStack().getFluid();
         int amountIn = buffer.readVarInt();
         ItemStack containerIn = buffer.readItem();
         ItemStack outputIn = buffer.readItem();
         return new KettlePouringRecipe(recipeId, fluidIn, containerIn, outputIn, amountIn);
      }

      public void toNetwork(FriendlyByteBuf buffer, KettlePouringRecipe recipe) {
         buffer.writeFluidStack(new FluidStack(recipe.fluid, 1000));
         buffer.writeVarInt(recipe.amount);
         buffer.writeItem(recipe.container);
         buffer.writeItem(recipe.output);
      }
   }
}
