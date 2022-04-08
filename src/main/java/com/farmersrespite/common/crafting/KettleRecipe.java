package com.farmersrespite.common.crafting;

import javax.annotation.Nullable;

import com.farmersrespite.core.FarmersRespite;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class KettleRecipe implements Recipe<RecipeWrapper>
{
	public static RecipeType<KettleRecipe> TYPE = RecipeType.register(FarmersRespite.MODID + ":brewing");
	public static final Serializer SERIALIZER = new Serializer();
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

	@Override
	public ResourceLocation getId() {
		return this.id;
	}

	@Override
	public String getGroup() {
		return this.group;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return this.inputItems;
	}

	@Override
	public ItemStack getResultItem() {
		return this.output;
	}

	public ItemStack getOutputContainer() {
		return this.container;
	}

	@Override
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

	@Override
	public boolean matches(RecipeWrapper inv, Level worldIn) {
		java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
		int i = 0;

		for (int j = 0; j < INPUT_SLOTS; ++j) {
			ItemStack itemstack = inv.getItem(j);
			if (!itemstack.isEmpty()) {
				++i;
				inputs.add(itemstack);
			}
		}
		return i == this.inputItems.size() && net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs, this.inputItems) != null;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= this.inputItems.size();
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return KettleRecipe.SERIALIZER;
	}

	@Override
	public RecipeType<?> getType() {
		return KettleRecipe.TYPE;
	}

	private static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<KettleRecipe>
	{
		Serializer() {
			this.setRegistryName(new ResourceLocation(FarmersRespite.MODID, "brewing"));
		}

		@Override
		public KettleRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
			final String groupIn = GsonHelper.getAsString(json, "group", "");
			final NonNullList<Ingredient> inputItemsIn = readIngredients(GsonHelper.getAsJsonArray(json, "ingredients"));
			if (inputItemsIn.isEmpty()) {
				throw new JsonParseException("No ingredients for brewing recipe");
			} else if (inputItemsIn.size() > KettleRecipe.INPUT_SLOTS) {
				throw new JsonParseException("Too many ingredients for brewing recipe! The max is " + KettleRecipe.INPUT_SLOTS);
			} else {
				final ItemStack outputIn = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
				ItemStack container = GsonHelper.isValidNode(json, "container") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "container"), true) : ItemStack.EMPTY;
				final float experienceIn = GsonHelper.getAsFloat(json, "experience", 0.0F);
				final int brewTimeIn = GsonHelper.getAsInt(json, "brewingtime", 2400);
				final boolean needWaterIn = GsonHelper.getAsBoolean(json, "needwater", true);
				return new KettleRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, experienceIn, brewTimeIn, needWaterIn);
			}
		}

		private static NonNullList<Ingredient> readIngredients(JsonArray ingredientArray) {
			NonNullList<Ingredient> nonnulllist = NonNullList.create();

			for (int i = 0; i < ingredientArray.size(); ++i) {
				Ingredient ingredient = Ingredient.fromJson(ingredientArray.get(i));
				if (!ingredient.isEmpty()) {
					nonnulllist.add(ingredient);
				}
			}

			return nonnulllist;
		}

		@Nullable
		@Override
		public KettleRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
			String groupIn = buffer.readUtf(32767);
			int i = buffer.readVarInt();
			NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);

			for (int j = 0; j < inputItemsIn.size(); ++j) {
				inputItemsIn.set(j, Ingredient.fromNetwork(buffer));
			}

			ItemStack outputIn = buffer.readItem();
			ItemStack container = buffer.readItem();
			float experienceIn = buffer.readFloat();
			int brewTimeIn = buffer.readVarInt();
			Boolean needWaterIn = buffer.readBoolean();
			return new KettleRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, experienceIn, brewTimeIn, needWaterIn);
		}

		@Override
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
