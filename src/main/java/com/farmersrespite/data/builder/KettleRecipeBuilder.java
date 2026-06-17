package com.farmersrespite.data.builder;

import com.farmersrespite.core.registry.FRRecipeSerializers;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;


@ParametersAreNonnullByDefault
public class KettleRecipeBuilder {
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private final Item result;
    private final int count;
    private final int brewingTime;
    private final float experience;
    private final Item container;
    private final boolean needWater;

    private KettleRecipeBuilder(ItemLike resultIn, @Nullable int count, @Nullable int brewingTime, @Nullable float experience, @Nullable boolean needWater, @Nullable ItemLike container) {
        this.result = resultIn.asItem();
        this.count = count;
        this.brewingTime = brewingTime;
        this.experience = experience;
        this.container = (container != null) ? container.asItem() : null;
        this.needWater = needWater;
    }


    public static KettleRecipeBuilder kettleRecipe(ItemLike mainResult) {
        return new KettleRecipeBuilder(mainResult, 1, 2400, 0.35F, true, null);
    }

    public static KettleRecipeBuilder kettleRecipe(ItemLike mainResult, int count, int brewingTime, float experience, boolean needWater, ItemLike container) {
        return new KettleRecipeBuilder(mainResult, count, brewingTime, experience, needWater, container);
    }

    public KettleRecipeBuilder addIngredient(TagKey<Item> tagIn) {
        return addIngredient(Ingredient.of(tagIn));
    }

    public KettleRecipeBuilder addIngredient(ItemLike itemIn) {
        return addIngredient(itemIn, 1);
    }

    public KettleRecipeBuilder addIngredient(ItemLike itemIn, int quantity) {
        for (int i = 0; i < quantity; i++) {
            addIngredient(Ingredient.of(itemIn));
        }
        return this;
    }

    public KettleRecipeBuilder addIngredient(Ingredient ingredientIn) {
        return addIngredient(ingredientIn, 1);
    }

    public KettleRecipeBuilder addIngredient(Ingredient ingredientIn, int quantity) {
        for (int i = 0; i < quantity; i++) {
            this.ingredients.add(ingredientIn);
        }
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumerIn) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(this.result);
        build(consumerIn, "farmersrespite:brewing/" + location.getPath());
    }

    public void build(Consumer<FinishedRecipe> consumerIn, String save) {
        ResourceLocation resourcelocation = ForgeRegistries.ITEMS.getKey(this.result);
        if ((new ResourceLocation(save)).equals(resourcelocation)) {
            throw new IllegalStateException("Brewing Recipe " + save + " should remove its 'save' argument");
        }
        build(consumerIn, new ResourceLocation(save));
    }


    public void build(Consumer<FinishedRecipe> consumerIn, ResourceLocation id) {
        consumerIn.accept(new Result(id, this.result, this.count, this.ingredients, this.brewingTime, this.experience, this.needWater, this.container));
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final List<Ingredient> ingredients;
        private final Item result;
        private final int count;
        private final int brewingTime;
        private final float experience;
        private final boolean needWater;
        private final Item container;

        public Result(ResourceLocation idIn, Item resultIn, int countIn, List<Ingredient> ingredientsIn, int brewingTimeIn, float experienceIn, @Nullable boolean needWaterIn, @Nullable Item containerIn) {
            this.id = idIn;
            this.ingredients = ingredientsIn;
            this.result = resultIn;
            this.count = countIn;
            this.brewingTime = brewingTimeIn;
            this.experience = experienceIn;
            this.needWater = needWaterIn;
            this.container = containerIn;
        }


        public void serializeRecipeData(JsonObject json) {
            JsonArray arrayIngredients = new JsonArray();

            for (Ingredient ingredient : this.ingredients) {
                arrayIngredients.add(ingredient.toJson());
            }
            json.add("ingredients", arrayIngredients);

            JsonObject objectResult = new JsonObject();
            objectResult.addProperty("item", ForgeRegistries.ITEMS.getKey(this.result).toString());
            if (this.count > 1) {
                objectResult.addProperty("count", Integer.valueOf(this.count));
            }
            json.add("result", objectResult);

            if (this.container != null) {
                JsonObject objectContainer = new JsonObject();
                objectContainer.addProperty("item", ForgeRegistries.ITEMS.getKey(this.container).toString());
                json.add("container", objectContainer);
            }
            if (this.experience > 0.0F) {
                json.addProperty("experience", Float.valueOf(this.experience));
            }
            json.addProperty("brewingtime", Integer.valueOf(this.brewingTime));
            json.addProperty("needwater", Boolean.valueOf(this.needWater));
        }


        public ResourceLocation getId() {
            return this.id;
        }


        public RecipeSerializer<?> getType() {
            return FRRecipeSerializers.BREWING.get();
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
