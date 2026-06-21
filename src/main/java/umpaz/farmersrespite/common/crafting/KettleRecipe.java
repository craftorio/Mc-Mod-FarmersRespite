package umpaz.farmersrespite.common.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.util.ArrayList;
import java.util.List;
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
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistries;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.registry.FRRecipeSerializers;
import umpaz.farmersrespite.common.registry.FRRecipeTypes;

public class KettleRecipe implements Recipe<RecipeWrapper> {
   public static final int INPUT_SLOTS = 2;
   private final ResourceLocation id;
   private final NonNullList<Ingredient> inputItems;
   private final FluidStack fluidIn;
   private final FluidStack fluidOut;
   private final float experience;
   private final int brewTime;

   public KettleRecipe(ResourceLocation id, NonNullList<Ingredient> inputItems, FluidStack fluidIn, FluidStack fluidOut, float experience, int brewTime) {
      this.id = id;
      this.inputItems = inputItems;
      this.fluidIn = fluidIn;
      this.fluidOut = fluidOut;
      this.experience = experience;
      this.brewTime = brewTime;
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public NonNullList<Ingredient> getIngredients() {
      return this.inputItems;
   }

   public FluidStack getFluidIn() {
      return this.fluidIn;
   }

   public FluidStack getFluidOut() {
      return this.fluidOut;
   }

   public ItemStack getResultItem(RegistryAccess registryAccess) {
      return ItemStack.EMPTY;
   }

   public ItemStack assemble(RecipeWrapper inv, RegistryAccess access) {
      return null;
   }

   public float getExperience() {
      return this.experience;
   }

   public int getBrewTime() {
      return this.brewTime;
   }

   public boolean matches(RecipeWrapper inv, Level level) {
      List<ItemStack> inputs = new ArrayList<>();
      int i = 0;

      for (int j = 0; j < 2; j++) {
         ItemStack itemstack = inv.getItem(j);
         if (!itemstack.isEmpty()) {
            i++;
            inputs.add(itemstack);
         }
      }

      return i == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null;
   }

   public boolean canCraftInDimensions(int width, int height) {
      return width * height >= this.inputItems.size();
   }

   public RecipeSerializer<?> getSerializer() {
      return (RecipeSerializer<?>)FRRecipeSerializers.BREWING.get();
   }

   public RecipeType<?> getType() {
      return (RecipeType<?>)FRRecipeTypes.BREWING.get();
   }

   public ItemStack getToastSymbol() {
      return new ItemStack((ItemLike)FRItems.KETTLE.get());
   }

   public static class Serializer implements RecipeSerializer<KettleRecipe> {
      public KettleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
         NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
         if (inputItemsIn.isEmpty()) {
            throw new JsonParseException("No ingredients for brewing recipe");
         }

         if (inputItemsIn.size() > 2) {
            throw new JsonParseException("Too many ingredients for brewing recipe! The max is 2");
         }

         JsonObject baseFluid = json.getAsJsonObject("base");
         if (baseFluid == null) {
            throw new JsonParseException("No base fluid for brewing recipe");
         }

         String fluid = GsonHelper.getAsString(baseFluid, "fluid");
         Fluid fluidObj = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluid));
         if (fluidObj == null) {
            throw new JsonParseException("Invalid fluid: " + fluid);
         }

         FluidStack fluidIn = new FluidStack(fluidObj, GsonHelper.getAsInt(baseFluid, "count", 200));
         JsonObject resultFluid = json.getAsJsonObject("result");
         if (resultFluid == null) {
            throw new JsonParseException("No result fluid for brewing recipe");
         }

         fluid = GsonHelper.getAsString(resultFluid, "fluid");
         fluidObj = (Fluid)ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fluid));
         if (fluidObj == null) {
            throw new JsonParseException("Invalid fluid: " + fluid);
         }

         FluidStack fluidOut = new FluidStack(fluidObj, GsonHelper.getAsInt(resultFluid, "count", 200));
         float experienceIn = GsonHelper.getAsFloat(json, "experience", 0.0F);
         int brewTimeIn = GsonHelper.getAsInt(json, "brewingtime", 200);
         return new KettleRecipe(recipeId, inputItemsIn, fluidIn, fluidOut, experienceIn, brewTimeIn);
      }

      private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
         NonNullList<Ingredient> nonnulllist = NonNullList.create();

         for (int i = 0; i < ingredientArray.size(); i++) {
            Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
            if (!ingredient.isEmpty()) {
               nonnulllist.add(ingredient);
            }
         }

         return nonnulllist;
      }

      @Nullable
      public KettleRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
         int i = buffer.readVarInt();
         NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);

         for (int j = 0; j < inputItemsIn.size(); j++) {
            inputItemsIn.set(j, Ingredient.fromNetwork(buffer));
         }

         FluidStack fluidIn = buffer.readFluidStack();
         FluidStack fluidOut = buffer.readFluidStack();
         float experienceIn = buffer.readFloat();
         int brewTimeIn = buffer.readVarInt();
         return new KettleRecipe(recipeId, inputItemsIn, fluidIn, fluidOut, experienceIn, brewTimeIn);
      }

      public void toNetwork(FriendlyByteBuf buffer, KettleRecipe recipe) {
         buffer.writeVarInt(recipe.inputItems.size());

         for (Ingredient ingredient : recipe.inputItems) {
            ingredient.toNetwork(buffer);
         }

         buffer.writeFluidStack(recipe.fluidIn);
         buffer.writeFluidStack(recipe.fluidOut);
         buffer.writeFloat(recipe.experience);
         buffer.writeVarInt(recipe.brewTime);
      }
   }
}
