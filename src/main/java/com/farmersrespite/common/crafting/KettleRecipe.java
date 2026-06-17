package com.farmersrespite.common.crafting;

import com.farmersrespite.core.registry.FRRecipeSerializers;
import com.farmersrespite.core.registry.FRRecipeTypes;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class KettleRecipe implements Recipe<RecipeWrapper> {
    public static final int INPUT_SLOTS = 2;
    private final ResourceLocation id;
    private final String group;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ItemStack container;
    private final float experience;
    private final int brewTime;
    private final boolean needWater;

    public KettleRecipe(ResourceLocation id, String group, NonNullList<Ingredient> inputItems, ItemStack output, ItemStack container, float experience, int brewTime, boolean needWater) {
        this.id = id;
        this.group = group;
        this.inputItems = inputItems;
        this.output = output;

        if (!container.isEmpty()) {
            this.container = container;
        } else if (!output.getContainerItem().isEmpty()) {
            this.container = output.getContainerItem();
        } else {
            this.container = ItemStack.EMPTY;
        }

        this.experience = experience;
        this.brewTime = brewTime;
        this.needWater = needWater;
    }


    public boolean isSpecial() {
        return true;
    }


    public ResourceLocation getId() {
        return this.id;
    }


    public String getGroup() {
        return this.group;
    }


    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }


    public ItemStack getResultItem() {
        return this.output;
    }

    public ItemStack getOutputContainer() {
        return this.container;
    }


    public ItemStack assemble(RecipeWrapper inv) {
        return this.output.copy();
    }

    public float getExperience() {
        return this.experience;
    }

    public int getBrewTime() {
        return this.brewTime;
    }

    public boolean getNeedWater() {
        return this.needWater;
    }


    public boolean matches(RecipeWrapper inv, Level worldIn) {
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;

        for (int j = 0; j < 2; j++) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                i++;
                inputs.add(itemstack);
            }
        }
        return (i == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null);
    }


    public boolean canCraftInDimensions(int width, int height) {
        return (width * height >= this.inputItems.size());
    }


    public RecipeSerializer<?> getSerializer() {
        return FRRecipeSerializers.BREWING.get();
    }


    public RecipeType<?> getType() {
        return FRRecipeTypes.BREWING.get();
    }


    public static class Serializer
            extends ForgeRegistryEntry<RecipeSerializer<?>>
            implements RecipeSerializer<KettleRecipe> {
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

        public KettleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String groupIn = GsonHelper.getAsString(json, "group", "");
            NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
            if (inputItemsIn.isEmpty())
                throw new JsonParseException("No ingredients for brewing recipe");
            if (inputItemsIn.size() > 2) {
                throw new JsonParseException("Too many ingredients for brewing recipe! The max is 2");
            }
            ItemStack outputIn = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
            ItemStack container = GsonHelper.isValidNode(json, "container") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "container"), true) : ItemStack.EMPTY;
            float experienceIn = GsonHelper.getAsFloat(json, "experience", 0.0F);
            int brewTimeIn = GsonHelper.getAsInt(json, "brewingtime", 2400);
            boolean needWaterIn = GsonHelper.getAsBoolean(json, "needwater", true);
            return new KettleRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, experienceIn, brewTimeIn, needWaterIn);
        }

        @Nullable
        public KettleRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            int i = buffer.readVarInt();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);

            for (int j = 0; j < inputItemsIn.size(); j++) {
                inputItemsIn.set(j, Ingredient.fromNetwork(buffer));
            }

            ItemStack outputIn = buffer.readItem();
            ItemStack container = buffer.readItem();
            float experienceIn = buffer.readFloat();
            int brewTimeIn = buffer.readVarInt();
            Boolean needWaterIn = Boolean.valueOf(buffer.readBoolean());
            return new KettleRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, experienceIn, brewTimeIn, needWaterIn.booleanValue());
        }


        public void toNetwork(FriendlyByteBuf buffer, KettleRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeVarInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeItem(recipe.output);
            buffer.writeItem(recipe.container);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.brewTime);
            buffer.writeBoolean(recipe.needWater);
        }
    }
}
