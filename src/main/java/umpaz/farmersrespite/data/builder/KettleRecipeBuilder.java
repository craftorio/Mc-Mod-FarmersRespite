package umpaz.farmersrespite.data.builder;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import umpaz.farmersrespite.common.registry.FRRecipeSerializers;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KettleRecipeBuilder {
   private final List<Ingredient> ingredients = Lists.newArrayList();
   private final int brewingTime;
   private final float experience;
   private final FluidStack fluidIn;
   private final FluidStack fluidOut;

   protected KettleRecipeBuilder(FluidStack fluidIn, FluidStack fluidOut, int brewingTime, float experience) {
      this.fluidIn = fluidIn;
      this.fluidOut = fluidOut;
      this.brewingTime = brewingTime;
      this.experience = experience;
   }

   public static KettleRecipeBuilder kettleRecipe(FluidStack fluidIn, FluidStack fluidOut, int cookingTime, float experience) {
      return new KettleRecipeBuilder(fluidIn, fluidOut, cookingTime, experience);
   }

   public KettleRecipeBuilder addIngredient(TagKey<Item> tagIn) {
      return this.addIngredient(Ingredient.of(tagIn));
   }

   public KettleRecipeBuilder addIngredient(ItemLike itemIn) {
      return this.addIngredient(itemIn, 1);
   }

   public KettleRecipeBuilder addIngredient(ItemLike itemIn, int quantity) {
      for (int i = 0; i < quantity; i++) {
         this.addIngredient(Ingredient.of(new ItemLike[]{itemIn}));
      }

      return this;
   }

   public KettleRecipeBuilder addIngredient(Ingredient ingredientIn) {
      return this.addIngredient(ingredientIn, 1);
   }

   public KettleRecipeBuilder addIngredient(Ingredient ingredientIn, int quantity) {
      for (int i = 0; i < quantity; i++) {
         this.ingredients.add(ingredientIn);
      }

      return this;
   }

   protected String getDefaultRecipeName(ResourceLocation resultItemKey) {
      return "farmersrespite:brewing/" + resultItemKey.getPath();
   }

   public void build(Consumer<FinishedRecipe> consumerIn) {
      ResourceLocation baseFluidLocation = ForgeRegistries.FLUIDS.getKey(this.fluidIn.getFluid());
      ResourceLocation resultFluidLocation = ForgeRegistries.FLUIDS.getKey(this.fluidOut.getFluid());
      this.build(consumerIn, "farmersrespite:brewing/" + resultFluidLocation.getPath() + "_from_" + baseFluidLocation.getPath());
   }

   public void build(Consumer<FinishedRecipe> consumerIn, String save) {
      this.build(consumerIn, new ResourceLocation(save));
   }

   public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
      consumerIn.accept(new KettleRecipeBuilder.Result(id, this.fluidIn, this.fluidOut, this.ingredients, this.brewingTime, this.experience));
   }

   public static class Result implements FinishedRecipe {
      private final ResourceLocation id;
      private final List<Ingredient> ingredients;
      private final int cookingTime;
      private final float experience;
      private final FluidStack fluidIn;
      private final FluidStack fluidOut;

      public Result(ResourceLocation idIn, FluidStack fluidIn, FluidStack fluidOut, List<Ingredient> ingredientsIn, int cookingTimeIn, float experienceIn) {
         this.id = idIn;
         this.fluidIn = fluidIn;
         this.fluidOut = fluidOut;
         this.ingredients = ingredientsIn;
         this.cookingTime = cookingTimeIn;
         this.experience = experienceIn;
      }

      public void serializeRecipeData(JsonObject json) {
         JsonArray arrayIngredients = new JsonArray();

         for (Ingredient ingredient : this.ingredients) {
            arrayIngredients.add(ingredient.toJson());
         }

         json.add("ingredients", arrayIngredients);
         JsonObject basefluid = new JsonObject();
         basefluid.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(this.fluidIn.getFluid()).toString());
         basefluid.addProperty("count", this.fluidIn.getAmount());
         json.add("base", basefluid);
         JsonObject resultFluid = new JsonObject();
         resultFluid.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(this.fluidOut.getFluid()).toString());
         resultFluid.addProperty("count", this.fluidOut.getAmount());
         json.add("result", resultFluid);
         if (this.experience > 0.0F) {
            json.addProperty("experience", this.experience);
         }

         json.addProperty("cookingtime", this.cookingTime);
      }

      public ResourceLocation getId() {
         return this.id;
      }

      public RecipeSerializer<?> getType() {
         return (RecipeSerializer<?>)FRRecipeSerializers.BREWING.get();
      }

      @Nullable
      public JsonObject serializeAdvancement() {
         return null;
      }

      @Nullable
      public ResourceLocation getAdvancementId() {
         return null;
      }
   }
}
