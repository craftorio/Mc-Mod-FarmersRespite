package com.farmersrespite.common.block.entity;

import com.farmersrespite.common.block.KettleBlock;
import com.farmersrespite.common.block.entity.container.KettleContainer;
import com.farmersrespite.common.block.entity.inventory.KettleItemHandler;
import com.farmersrespite.common.crafting.KettleRecipe;
import com.farmersrespite.core.registry.FRBlockEntityTypes;
import com.farmersrespite.core.registry.FRRecipeTypes;
import com.farmersrespite.core.utility.FRTextUtils;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.utility.ItemUtils;


public class KettleBlockEntity
        extends SyncedBlockEntity
        implements MenuProvider, HeatableBlockEntity, Nameable {
    public static final int MEAL_DISPLAY_SLOT = 2;
    public static final int CONTAINER_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;
    public static final int INVENTORY_SIZE = 5;
    protected final ContainerData kettleData;
    private final ItemStackHandler inventory;
    private final LazyOptional<IItemHandler> inputHandler;
    private final LazyOptional<IItemHandler> outputHandler;
    private final Object2IntOpenHashMap<ResourceLocation> experienceTracker;
    private int brewTime;
    private int brewTimeTotal;
    private boolean needWater;
    private ItemStack mealContainerStack;
    private Component customName;
    private ResourceLocation lastRecipeID;
    private boolean checkNewRecipe;
    public KettleBlockEntity(BlockPos pos, BlockState state) {
        super(FRBlockEntityTypes.KETTLE.get(), pos, state);
        this.inventory = createHandler();
        this.inputHandler = LazyOptional.of(() -> new KettleItemHandler(this.inventory, Direction.UP));
        this.outputHandler = LazyOptional.of(() -> new KettleItemHandler(this.inventory, Direction.DOWN));
        this.mealContainerStack = ItemStack.EMPTY;
        this.kettleData = createIntArray();
        this.experienceTracker = new Object2IntOpenHashMap();
        this.checkNewRecipe = true;
    }

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
            } else if (!kettle.inventory.getStackInSlot(3).isEmpty()) {
                kettle.useStoredContainersOnMeal();
                didInventoryChange = true;
            }
        }
        if (didInventoryChange) {
            kettle.inventoryChanged();
        }
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, KettleBlockEntity kettle) {
        boolean i = ((Boolean) state.getValue((Property) KettleBlock.LID)).booleanValue();
        ItemStack mealStack = kettle.getMeal();
        if (kettle.isHeated(level, pos) && i && !mealStack.isEmpty()) {
            Direction direction = (Direction) state.getValue((Property) KettleBlock.FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double d0 = pos.getX() + 0.5D;
            double d1 = pos.getY() + 0.5D;
            double d2 = pos.getZ() + 0.5D;
            double d4 = 0.0D;
            double d5 = (direction$axis == Direction.Axis.X) ? (direction.getStepX() * 0.52D) : d4;
            double d6 = 0.0D;
            double d7 = (direction$axis == Direction.Axis.Z) ? (direction.getStepZ() * 0.52D) : d4;
            if (level.random.nextInt(5) == 0) {
                level.addParticle(ParticleTypes.EFFECT, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private static void splitAndSpawnExperience(Level level, Vec3 pos, int craftedAmount, float experience) {
        int expTotal = Mth.floor(craftedAmount * experience);
        float expFraction = Mth.frac(craftedAmount * experience);
        if (expFraction != 0.0F && Math.random() < expFraction) {
            expTotal++;
        }

        while (expTotal > 0) {
            int expValue = ExperienceOrb.getExperienceValue(expTotal);
            expTotal -= expValue;
            level.addFreshEntity(new ExperienceOrb(level, pos.x, pos.y, pos.z, expValue));
        }
    }

    public boolean isLid() {
        if (this.level == null) {
            return false;
        }
        BlockState state = this.level.getBlockState(getBlockPos());
        boolean i = ((Boolean) state.getValue((Property) KettleBlock.LID)).booleanValue();
        boolean flag = i;
        return flag;
    }

    public boolean isWater() {
        if (this.level == null) {
            return false;
        }
        BlockState state = this.level.getBlockState(getBlockPos());
        int i = ((Integer) state.getValue((Property) KettleBlock.WATER_LEVEL)).intValue();
        boolean flag = (i > 0);
        return flag;
    }

    public void load(CompoundTag compound) {
        super.load(compound);
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
        this.brewTime = compound.getInt("BrewTime");
        this.brewTimeTotal = compound.getInt("BrewTimeTotal");
        this.needWater = compound.getBoolean("NeedWater");
        this.mealContainerStack = ItemStack.of(compound.getCompound("Container"));
        if (compound.contains("CustomName", 8)) {
            this.customName = Component.Serializer.fromJson(compound.getString("CustomName"));
        }
        CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");
        for (String key : compoundRecipes.getAllKeys()) {
            this.experienceTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
    }

    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("BrewTime", this.brewTime);
        compound.putInt("BrewTimeTotal", this.brewTimeTotal);
        compound.putBoolean("NeedWater", this.needWater);
        compound.put("Container", this.mealContainerStack.serializeNBT());
        if (this.customName != null) {
            compound.putString("CustomName", Component.Serializer.toJson(this.customName));
        }
        compound.put("Inventory", this.inventory.serializeNBT());
        CompoundTag compoundRecipes = new CompoundTag();
        this.experienceTracker.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount.intValue()));
        compound.put("RecipesUsed", compoundRecipes);
    }

    private CompoundTag writeItems(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.put("Container", this.mealContainerStack.serializeNBT());
        compound.put("Inventory", this.inventory.serializeNBT());
        return compound;
    }

    public CompoundTag writeMeal(CompoundTag compound) {
        if (getMeal().isEmpty()) return compound;

        ItemStackHandler drops = new ItemStackHandler(5);
        for (int i = 0; i < 5; i++) {
            drops.setStackInSlot(i, (i == 2) ? this.inventory.getStackInSlot(i) : ItemStack.EMPTY);
        }
        if (this.customName != null) {
            compound.putString("CustomName", Component.Serializer.toJson(this.customName));
        }
        compound.put("Container", this.mealContainerStack.serializeNBT());
        compound.put("Inventory", drops.serializeNBT());
        return compound;
    }

    private Optional<KettleRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
        if (this.level == null) return Optional.empty();

        if (this.lastRecipeID != null) {


            Recipe<RecipeWrapper> recipe = (Recipe<RecipeWrapper>) ((RecipeManagerAccessor) this.level.getRecipeManager()).getRecipeMap((RecipeType) FRRecipeTypes.BREWING.get()).get(this.lastRecipeID);
            if (recipe instanceof KettleRecipe) {
                if (recipe.matches(inventoryWrapper, this.level)) {
                    return Optional.of((KettleRecipe) recipe);
                }
                if (recipe.getResultItem().sameItem(getMeal())) {
                    return Optional.empty();
                }
            }
        }

        if (this.checkNewRecipe) {
            Optional<KettleRecipe> recipe = this.level.getRecipeManager().getRecipeFor((RecipeType) FRRecipeTypes.BREWING.get(), (Container) inventoryWrapper, this.level);
            if (recipe.isPresent()) {
                this.lastRecipeID = recipe.get().getId();
                return recipe;
            }
        }

        this.checkNewRecipe = false;
        return Optional.empty();
    }

    public ItemStack getContainer() {
        if (!this.mealContainerStack.isEmpty()) {
            return this.mealContainerStack;
        }
        return getMeal().getContainerItem();
    }

    private boolean hasInput() {
        for (int i = 0; i < 2; i++) {
            if (!this.inventory.getStackInSlot(i).isEmpty()) return true;
        }
        return false;
    }

    protected boolean canBrew(KettleRecipe recipe) {
        this.needWater = recipe.getNeedWater();
        if (hasInput()) {
            ItemStack resultStack = recipe.getResultItem();
            if (resultStack.isEmpty()) {
                return false;
            }
            ItemStack storedMealStack = this.inventory.getStackInSlot(2);
            if (isWater() || !this.needWater) {
                if (storedMealStack.isEmpty())
                    return true;
                if (!storedMealStack.sameItem(resultStack))
                    return false;
                if (storedMealStack.getCount() + resultStack.getCount() <= this.inventory.getSlotLimit(2)) {
                    return true;
                }
                return (storedMealStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize());
            }
        }


        return false;
    }

    private boolean processBrewing(KettleRecipe recipe) {
        if (this.level == null) return false;
        BlockState state = this.level.getBlockState(this.worldPosition);
        int j = ((Integer) state.getValue((Property) KettleBlock.WATER_LEVEL)).intValue();

        this.brewTime++;
        this.brewTimeTotal = recipe.getBrewTime();
        if (this.brewTime < this.brewTimeTotal) {
            return false;
        }

        this.brewTime = 0;
        this.mealContainerStack = recipe.getOutputContainer();
        ItemStack resultStack = recipe.getResultItem();
        ItemStack storedMealStack = this.inventory.getStackInSlot(2);
        if (storedMealStack.isEmpty()) {
            this.inventory.setStackInSlot(2, resultStack.copy());
        } else if (storedMealStack.sameItem(resultStack)) {
            storedMealStack.grow(resultStack.getCount());
        }
        trackRecipeExperience(recipe);
        if (this.needWater) {
            this.level.setBlockAndUpdate(this.worldPosition, state.setValue(KettleBlock.WATER_LEVEL, Integer.valueOf(j - 1)));
        }

        for (int i = 0; i < 2; i++) {
            ItemStack slotStack = this.inventory.getStackInSlot(i);
            if (slotStack.hasContainerItem()) {
                Direction direction = ((Direction) getBlockState().getValue((Property) KettleBlock.FACING)).getCounterClockWise();
                double x = this.worldPosition.getX() + 0.5D + direction.getStepX() * 0.25D;
                double y = this.worldPosition.getY() + 0.7D;
                double z = this.worldPosition.getZ() + 0.5D + direction.getStepZ() * 0.25D;
                ItemUtils.spawnItemEntity(this.level, this.inventory.getStackInSlot(i).getContainerItem(), x, y, z, (direction
                        .getStepX() * 0.08F), 0.25D, (direction.getStepZ() * 0.08F));
            }
            if (!slotStack.isEmpty())
                slotStack.shrink(1);
        }
        return true;
    }

    public void trackRecipeExperience(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            this.experienceTracker.addTo(recipeID, 1);
        }
    }

    public void clearUsedRecipes(Player player) {
        grantStoredRecipeExperience(player.level, player.position());
        this.experienceTracker.clear();
    }

    public void grantStoredRecipeExperience(Level level, Vec3 pos) {
        for (ObjectIterator<Object2IntMap.Entry<ResourceLocation>> objectIterator = this.experienceTracker.object2IntEntrySet().iterator(); objectIterator.hasNext(); ) {
            Object2IntMap.Entry<ResourceLocation> entry = objectIterator.next();
            level.getRecipeManager().byKey(entry.getKey()).ifPresent(recipe -> splitAndSpawnExperience(level, pos, entry.getIntValue(), ((KettleRecipe) recipe).getExperience()));
        }

    }

    public boolean isHeated() {
        if (this.level == null) return false;
        return isHeated(this.level, this.worldPosition);
    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    public ItemStack getMeal() {
        return this.inventory.getStackInSlot(2);
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        for (int i = 0; i < 5; i++) {
            if (i != 2) {
                drops.add(this.inventory.getStackInSlot(i));
            }
        }
        return drops;
    }

    private void moveMealToOutput() {
        ItemStack mealStack = this.inventory.getStackInSlot(2);
        ItemStack outputStack = this.inventory.getStackInSlot(4);
        int mealCount = Math.min(mealStack.getCount(), mealStack.getMaxStackSize() - outputStack.getCount());
        if (outputStack.isEmpty()) {
            this.inventory.setStackInSlot(4, mealStack.split(mealCount));
        } else if (outputStack.getItem() == mealStack.getItem()) {
            mealStack.shrink(mealCount);
            outputStack.grow(mealCount);
        }
    }

    private void useStoredContainersOnMeal() {
        ItemStack mealStack = this.inventory.getStackInSlot(2);
        ItemStack containerInputStack = this.inventory.getStackInSlot(3);
        ItemStack outputStack = this.inventory.getStackInSlot(4);

        if (isContainerValid(containerInputStack) && outputStack.getCount() < outputStack.getMaxStackSize()) {
            int smallerStackCount = Math.min(mealStack.getCount(), containerInputStack.getCount());
            int mealCount = Math.min(smallerStackCount, mealStack.getMaxStackSize() - outputStack.getCount());
            if (outputStack.isEmpty()) {
                containerInputStack.shrink(mealCount);
                this.inventory.setStackInSlot(4, mealStack.split(mealCount));
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
        return (!this.mealContainerStack.isEmpty() || meal.hasContainerItem());
    }

    public boolean isContainerValid(ItemStack containerItem) {
        if (containerItem.isEmpty()) return false;
        if (!this.mealContainerStack.isEmpty()) {
            return this.mealContainerStack.sameItem(containerItem);
        }
        return getMeal().getContainerItem().sameItem(containerItem);
    }


    public boolean isDrinkEmpty() {
        if (this.level == null) {
            return false;
        }
        ItemStack meal = getMeal();
        return !meal.isEmpty() && isHeated();
    }


    public Component getName() {
        return (this.customName != null) ? this.customName : FRTextUtils.getTranslation("container.kettle", new Object[0]);
    }


    public Component getDisplayName() {
        return getName();
    }


    @Nullable
    public Component getCustomName() {
        return this.customName;
    }

    public void setCustomName(Component name) {
        this.customName = name;
    }


    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new KettleContainer(id, player, this, this.kettleData);
    }


    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if (cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            if (side == null || side.equals(Direction.UP)) {
                return this.inputHandler.cast();
            }
            return this.outputHandler.cast();
        }

        return super.getCapability(cap, side);
    }


    public void setRemoved() {
        super.setRemoved();
        this.inputHandler.invalidate();
        this.outputHandler.invalidate();
    }


    public CompoundTag getUpdateTag() {
        return writeItems(new CompoundTag());
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(5) {
            protected void onContentsChanged(int slot) {
                if (slot >= 0 && slot < 2) {
                    KettleBlockEntity.this.checkNewRecipe = true;
                }
                KettleBlockEntity.this.inventoryChanged();
            }
        };
    }

    private ContainerData createIntArray() {
        return new ContainerData() {
            public int get(int index) {
                switch (index) {
                    case 0:
                        return KettleBlockEntity.this.brewTime;
                    case 1:
                        return KettleBlockEntity.this.brewTimeTotal;
                }
                return 0;
            }


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


            public int getCount() {
                return 2;
            }
        };
    }
}
