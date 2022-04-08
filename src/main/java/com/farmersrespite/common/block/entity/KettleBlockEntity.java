package com.farmersrespite.common.block.entity;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.farmersrespite.common.block.KettleBlock;
import com.farmersrespite.common.block.entity.container.KettleContainer;
import com.farmersrespite.common.block.entity.inventory.KettleItemHandler;
import com.farmersrespite.common.crafting.KettleRecipe;
import com.farmersrespite.core.registry.FRBlockEntityTypes;
import com.farmersrespite.core.utility.FRTextUtils;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.utility.ItemUtils;

public class KettleBlockEntity extends SyncedBlockEntity implements MenuProvider, HeatableBlockEntity, Nameable
{
	public static final int MEAL_DISPLAY_SLOT = 2;
	public static final int CONTAINER_SLOT = 3;
	public static final int OUTPUT_SLOT = 4;
	public static final int INVENTORY_SIZE = OUTPUT_SLOT + 1;

	private final ItemStackHandler inventory;
	private final LazyOptional<IItemHandler> inputHandler;
	private final LazyOptional<IItemHandler> outputHandler;

	private int brewTime;
	private int brewTimeTotal;
	private boolean needWater;
	private ItemStack mealContainerStack;
	private Component customName;

	protected final ContainerData kettleData;
	private final Object2IntOpenHashMap<ResourceLocation> experienceTracker;

	private ResourceLocation lastRecipeID;
	private boolean checkNewRecipe;

	public KettleBlockEntity(BlockPos pos, BlockState state) {
		super(FRBlockEntityTypes.KETTLE.get(), pos, state);
		this.inventory = createHandler();
		this.inputHandler = LazyOptional.of(() -> new KettleItemHandler(inventory, Direction.UP));
		this.outputHandler = LazyOptional.of(() -> new KettleItemHandler(inventory, Direction.DOWN));
		this.mealContainerStack = ItemStack.EMPTY;
		this.kettleData = createIntArray();
		this.experienceTracker = new Object2IntOpenHashMap<>();
	}

	public boolean isLid() {
        if (this.level == null) {
            return false;
        } else {
            BlockState state = this.level.getBlockState(this.getBlockPos());
        	boolean i = state.getValue(KettleBlock.LID);
    		boolean flag = i;
    		if (flag)
            return true;
        }
        return false;
    }

	public boolean isWater() {
        if (this.level == null) {
            return false;
        } else {
            BlockState state = this.level.getBlockState(this.getBlockPos());
        	int i = state.getValue(KettleBlock.WATER_LEVEL);
    		boolean flag = i > 0;
    		if (flag)
            return true;
        }
        return false;
    }

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);
		inventory.deserializeNBT(compound.getCompound("Inventory"));
		brewTime = compound.getInt("BrewTime");
		brewTimeTotal = compound.getInt("BrewTimeTotal");
		needWater = compound.getBoolean("NeedWater");
		mealContainerStack = ItemStack.of(compound.getCompound("Container"));
		if (compound.contains("CustomName", 8)) {
			customName = Component.Serializer.fromJson(compound.getString("CustomName"));
		}
		CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");
		for (String key : compoundRecipes.getAllKeys()) {
			experienceTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
		}
	}

	@Override
	public void saveAdditional(CompoundTag compound) {
		super.saveAdditional(compound);
		compound.putInt("BrewTime", brewTime);
		compound.putInt("BrewTimeTotal", brewTimeTotal);
		compound.putBoolean("NeedWater", needWater);
		compound.put("Container", mealContainerStack.serializeNBT());
		if (customName != null) {
			compound.putString("CustomName", Component.Serializer.toJson(customName));
		}
		compound.put("Inventory", inventory.serializeNBT());
		CompoundTag compoundRecipes = new CompoundTag();
		experienceTracker.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
		compound.put("RecipesUsed", compoundRecipes);
	}

	private CompoundTag writeItems(CompoundTag compound) {
		super.saveAdditional(compound);
		compound.put("Container", mealContainerStack.serializeNBT());
		compound.put("Inventory", inventory.serializeNBT());
		return compound;
	}

	public CompoundTag writeMeal(CompoundTag compound) {
		if (getMeal().isEmpty()) return compound;

		ItemStackHandler drops = new ItemStackHandler(INVENTORY_SIZE);
		for (int i = 0; i < INVENTORY_SIZE; ++i) {
			drops.setStackInSlot(i, i == MEAL_DISPLAY_SLOT ? inventory.getStackInSlot(i) : ItemStack.EMPTY);
		}
		if (customName != null) {
			compound.putString("CustomName", Component.Serializer.toJson(customName));
		}
		compound.put("Container", mealContainerStack.serializeNBT());
		compound.put("Inventory", drops.serializeNBT());
		return compound;
	}

	// ======== BASIC FUNCTIONALITY ========

	public static void brewingTick(Level level, BlockPos pos, BlockState state, KettleBlockEntity kettle) {
		boolean isHeated = kettle.isHeated(level, pos);
		boolean didInventoryChange = false;

		if (isHeated && kettle.hasInput()) {
			Optional<KettleRecipe> recipe = kettle.getMatchingRecipe(new RecipeWrapper(kettle.inventory));
			if (recipe.isPresent() && kettle.canBrew(recipe.get())) {
				didInventoryChange = kettle.processBrewing(recipe.get());
			} else {
				kettle.brewTime = 0;
			}
		} else if (kettle.brewTime > 0) {
			kettle.brewTime = Mth.clamp(kettle.brewTime - 2, 0, kettle.brewTimeTotal);
		}

		ItemStack mealStack = kettle.getMeal();
		if (!mealStack.isEmpty()) {
			if (isHeated) {
				animationTick(level, pos, state, kettle);
			}
			if (!kettle.doesMealHaveContainer(mealStack)) {
				kettle.moveMealToOutput();
				didInventoryChange = true;
			} else if (!kettle.inventory.getStackInSlot(CONTAINER_SLOT).isEmpty()) {
				kettle.useStoredContainersOnMeal();
				didInventoryChange = true;
				}
		}
		if (didInventoryChange) {
			kettle.inventoryChanged();
			}
		}

	public static void animationTick(Level level, BlockPos pos, BlockState state, KettleBlockEntity kettle) {
        boolean i = state.getValue(KettleBlock.LID);
        ItemStack mealStack = kettle.getMeal();
		if (kettle.isHeated(level, pos) && i && !(mealStack.isEmpty())) {
            Direction direction = state.getValue(KettleBlock.FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + 0.5D;
            double d2 = pos.getZ() + 0.5D;
            double d4 = 0.0D;
            double d5 = direction$axis == Direction.Axis.X ? direction.getStepX() * 0.52D : d4;
            double d6 = 0.0D;
            double d7 = direction$axis == Direction.Axis.Z ? direction.getStepZ() * 0.52D : d4;
     		if (level.random.nextInt(5) == 0) {
            level.addParticle(ParticleTypes.EFFECT, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	private Optional<KettleRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
		if (level == null) return Optional.empty();

		if (lastRecipeID != null) {
			Recipe<RecipeWrapper> recipe = ((RecipeManagerAccessor) level.getRecipeManager())
					.getRecipeMap(KettleRecipe.TYPE)
					.get(lastRecipeID);
			if (recipe instanceof KettleRecipe) {
				if (recipe.matches(inventoryWrapper, level)) {
					return Optional.of((KettleRecipe) recipe);
				}
				if (recipe.getResultItem().sameItem(getMeal())) {
					return Optional.empty();
				}
			}
		}

		if (checkNewRecipe) {
			Optional<KettleRecipe> recipe = level.getRecipeManager().getRecipeFor(KettleRecipe.TYPE, inventoryWrapper, level);
			if (recipe.isPresent()) {
				lastRecipeID = recipe.get().getId();
				return recipe;
			}
		}

		checkNewRecipe = false;
		return Optional.empty();
	}

	public ItemStack getContainer() {
		if (!mealContainerStack.isEmpty()) {
			return mealContainerStack;
		} else {
			return getMeal().getContainerItem();
		}
	}

	private boolean hasInput() {
		for (int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
			if (!inventory.getStackInSlot(i).isEmpty()) return true;
		}
		return false;
	}

	protected boolean canBrew(KettleRecipe recipe) {
		needWater = recipe.getNeedWater();
		if (hasInput()) {
			ItemStack resultStack = recipe.getResultItem();
			if (resultStack.isEmpty()) {
				return false;
			} else {
				ItemStack storedMealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
				if ((isWater() || !needWater)) {
				if (storedMealStack.isEmpty()) {
					return true;
				} else if (!storedMealStack.sameItem(resultStack)) {
					return false;
				} else if (storedMealStack.getCount() + resultStack.getCount() <= inventory.getSlotLimit(MEAL_DISPLAY_SLOT)) {
					return true;
				} else {
					return storedMealStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
					}
				}
			}
		}
		return false;
	}

	private boolean processBrewing(KettleRecipe recipe) {
		if (level == null) return false;
        BlockState state = this.level.getBlockState(this.worldPosition);
        int j = state.getValue(KettleBlock.WATER_LEVEL);

        ++brewTime;
		brewTimeTotal = recipe.getBrewTime();
		if (brewTime < brewTimeTotal) {
			return false;
		}

		brewTime = 0;
		mealContainerStack = recipe.getOutputContainer();
		ItemStack resultStack = recipe.getResultItem();
		ItemStack storedMealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
		if (storedMealStack.isEmpty()) {
			inventory.setStackInSlot(MEAL_DISPLAY_SLOT, resultStack.copy());
		} else if (storedMealStack.sameItem(resultStack)) {
			storedMealStack.grow(resultStack.getCount());
		}
		trackRecipeExperience(recipe);
		if (needWater) {
	    level.setBlockAndUpdate(worldPosition, state.setValue(KettleBlock.WATER_LEVEL, j - 1));
		}

		for (int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
			ItemStack slotStack = inventory.getStackInSlot(i);
			if (slotStack.hasContainerItem()) {
				Direction direction = getBlockState().getValue(KettleBlock.FACING).getCounterClockWise();
				double x = worldPosition.getX() + 0.5 + (direction.getStepX() * 0.25);
				double y = worldPosition.getY() + 0.7;
				double z = worldPosition.getZ() + 0.5 + (direction.getStepZ() * 0.25);
				ItemUtils.spawnItemEntity(level, inventory.getStackInSlot(i).getContainerItem(), x, y, z,
						direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);
			}
			if (!slotStack.isEmpty())
				slotStack.shrink(1);
		}
		return true;
	}

	public void trackRecipeExperience(@Nullable Recipe<?> recipe) {
		if (recipe != null) {
			ResourceLocation recipeID = recipe.getId();
			experienceTracker.addTo(recipeID, 1);
		}
	}

	public void clearUsedRecipes(Player player) {
		grantStoredRecipeExperience(player.level, player.position());
		experienceTracker.clear();
	}

	public void grantStoredRecipeExperience(Level level, Vec3 pos) {
		for (Object2IntMap.Entry<ResourceLocation> entry : experienceTracker.object2IntEntrySet()) {
			level.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> splitAndSpawnExperience(level, pos, entry.getIntValue(), ((KettleRecipe) recipe).getExperience()));
		}
	}

	private static void splitAndSpawnExperience(Level level, Vec3 pos, int craftedAmount, float experience) {
		int expTotal = Mth.floor(craftedAmount * experience);
		float expFraction = Mth.frac(craftedAmount * experience);
		if (expFraction != 0.0F && Math.random() < expFraction) {
			++expTotal;
		}

		while (expTotal > 0) {
			int expValue = ExperienceOrb.getExperienceValue(expTotal);
			expTotal -= expValue;
			level.addFreshEntity(new ExperienceOrb(level, pos.x, pos.y, pos.z, expValue));
		}
	}

	public boolean isHeated() {
		if (level == null) return false;
		return this.isHeated(level, worldPosition);
	}

	public ItemStackHandler getInventory() {
		return inventory;
	}

	public ItemStack getMeal() {
		return inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
	}

	public NonNullList<ItemStack> getDroppableInventory() {
		NonNullList<ItemStack> drops = NonNullList.create();
		for (int i = 0; i < INVENTORY_SIZE; ++i) {
			if (i != MEAL_DISPLAY_SLOT) {
				drops.add(inventory.getStackInSlot(i));
			}
		}
		return drops;
	}

	private void moveMealToOutput() {
		ItemStack mealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
		ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);
		int mealCount = Math.min(mealStack.getCount(), mealStack.getMaxStackSize() - outputStack.getCount());
		if (outputStack.isEmpty()) {
			inventory.setStackInSlot(OUTPUT_SLOT, mealStack.split(mealCount));
		} else if (outputStack.getItem() == mealStack.getItem()) {
			mealStack.shrink(mealCount);
			outputStack.grow(mealCount);
		}
	}

	private void useStoredContainersOnMeal() {
		ItemStack mealStack = inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
		ItemStack containerInputStack = inventory.getStackInSlot(CONTAINER_SLOT);
		ItemStack outputStack = inventory.getStackInSlot(OUTPUT_SLOT);

		if (isContainerValid(containerInputStack) && outputStack.getCount() < outputStack.getMaxStackSize()) {
			int smallerStackCount = Math.min(mealStack.getCount(), containerInputStack.getCount());
			int mealCount = Math.min(smallerStackCount, mealStack.getMaxStackSize() - outputStack.getCount());
			if (outputStack.isEmpty()) {
				containerInputStack.shrink(mealCount);
				inventory.setStackInSlot(OUTPUT_SLOT, mealStack.split(mealCount));
			} else if (outputStack.getItem() == mealStack.getItem()) {
				mealStack.shrink(mealCount);
				containerInputStack.shrink(mealCount);
				outputStack.grow(mealCount);
			}
		}
	}

	public ItemStack useHeldItemOnMeal(ItemStack container) {
		if (isContainerValid(container) && !getMeal().isEmpty()) {
			container.shrink(1);
			return getMeal().split(1);
		}
		return ItemStack.EMPTY;
	}

	private boolean doesMealHaveContainer(ItemStack meal) {
		return !mealContainerStack.isEmpty() || meal.hasContainerItem();
	}

	public boolean isContainerValid(ItemStack containerItem) {
		if (containerItem.isEmpty()) return false;
		if (!mealContainerStack.isEmpty()) {
			return mealContainerStack.sameItem(containerItem);
		} else {
			return getMeal().getContainerItem().sameItem(containerItem);
		}
	}

	public boolean isDrinkEmpty() {
        if (this.level == null) {
            return false;
        } else {
            ItemStack meal = this.getMeal();
            if (!meal.isEmpty() && this.isHeated()) {
                return true;
            }
            return false;
        }
    }

	@Override
	public Component getName() {
		return customName != null ? customName : FRTextUtils.getTranslation("container.kettle");
	}

	@Override
	public Component getDisplayName() {
		return getName();
	}

	@Override
	@Nullable
	public Component getCustomName() {
		return customName;
	}

	public void setCustomName(Component name) {
		customName = name;
	}

	@Override
	public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
		return new KettleContainer(id, player, this, kettleData);
	}

	@Override
	@Nonnull
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
		if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
			if (side == null || side.equals(Direction.UP)) {
				return inputHandler.cast();
			} else {
				return outputHandler.cast();
			}
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void setRemoved() {
		super.setRemoved();
		inputHandler.invalidate();
		outputHandler.invalidate();
	}

	@Override
	public CompoundTag getUpdateTag() {
		return writeItems(new CompoundTag());
	}

	private ItemStackHandler createHandler() {
		return new ItemStackHandler(INVENTORY_SIZE)
		{
			@Override
			protected void onContentsChanged(int slot) {
				if (slot >= 0 && slot < MEAL_DISPLAY_SLOT) {
					checkNewRecipe = true;
				}
				inventoryChanged();
			}
		};
	}

	private ContainerData createIntArray() {
		return new ContainerData()
		{
			@Override
			public int get(int index) {
				switch (index) {
					case 0:
						return KettleBlockEntity.this.brewTime;
					case 1:
						return KettleBlockEntity.this.brewTimeTotal;
					default:
						return 0;
				}
			}

			@Override
			public void set(int index, int value) {
				switch (index) {
					case 0:
						KettleBlockEntity.this.brewTime = value;
						break;
					case 1:
						KettleBlockEntity.this.brewTimeTotal = value;
						break;
				}
			}

			@Override
			public int getCount() {
				return 2;
			}
		};
	}
}
