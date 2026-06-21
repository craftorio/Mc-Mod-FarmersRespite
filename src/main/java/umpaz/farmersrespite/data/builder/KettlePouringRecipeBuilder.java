package umpaz.farmersrespite.data.builder;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import umpaz.farmersrespite.common.registry.FRRecipeSerializers;

public class KettlePouringRecipeBuilder {
   private final ItemStack container;
   private final Fluid fluid;
   private final int amount;
   private final ItemStack output;

   private KettlePouringRecipeBuilder(ItemStack container, Fluid fluid, int amount, ItemStack output) {
      this.container = container;
      this.fluid = fluid;
      this.amount = amount;
      this.output = output;
   }

   public static void kettlePouringRecipe(ItemLike container, Fluid fluid, int amount, ItemLike output, Consumer<FinishedRecipe> consumer) {
      new KettlePouringRecipeBuilder(container.asItem().getDefaultInstance(), fluid, amount, output.asItem().getDefaultInstance()).build(consumer);
   }

   public void build(Consumer<FinishedRecipe> consumerIn) {
      ResourceLocation outputLocation = ForgeRegistries.ITEMS.getKey(this.output.getItem());
      this.build(consumerIn, "farmersrespite:pouring/" + outputLocation.getPath());
   }

   public void build(Consumer<FinishedRecipe> consumerIn, String save) {
      ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.output.getItem());
      if (new ResourceLocation(save).equals(resourcelocation)) {
         throw new IllegalStateException("Pouring Recipe " + save + " should remove its 'save' argument");
      }

      this.build(consumerIn, new ResourceLocation(save));
   }

   public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
      consumerIn.accept(new KettlePouringRecipeBuilder.Result(id, this.container, this.fluid, this.amount, this.output));
   }

   public static class Result implements FinishedRecipe {
      private final ResourceLocation id;
      private final ItemStack container;
      private final Fluid fluid;
      private final int amount;
      private final ItemStack output;

      public Result(ResourceLocation idIn, ItemStack containerIn, Fluid fluidIn, int amountIn, ItemStack outputIn) {
         this.id = idIn;
         this.container = containerIn;
         this.fluid = fluidIn;
         this.amount = amountIn;
         this.output = outputIn;
      }

      public void serializeRecipeData(JsonObject json) {
         JsonObject objectContainer = new JsonObject();
         objectContainer.addProperty("item", ForgeRegistries.ITEMS.getKey(this.container.getItem()).toString());
         json.add("container", objectContainer);
         if (this.container.hasTag()) {
            objectContainer.addProperty("nbt", this.output.getTag().toString());
         }

         JsonObject objectContainer1 = new JsonObject();
         objectContainer1.addProperty("item", ForgeRegistries.ITEMS.getKey(this.output.getItem()).toString());
         if (this.output.hasTag()) {
            objectContainer1.addProperty("nbt", this.output.getTag().toString());
         }

         json.add("output", objectContainer1);
         json.addProperty("fluid", ForgeRegistries.FLUIDS.getKey(this.fluid).toString());
         json.addProperty("amount", this.amount);
      }

      public ResourceLocation getId() {
         return this.id;
      }

      public RecipeSerializer<?> getType() {
         return (RecipeSerializer<?>)FRRecipeSerializers.KETTLE_POURING.get();
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
