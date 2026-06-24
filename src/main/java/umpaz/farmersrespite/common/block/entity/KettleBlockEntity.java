package umpaz.farmersrespite.common.block.entity;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.RecipeHolder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import umpaz.farmersrespite.common.block.KettleBlock;
import umpaz.farmersrespite.common.block.entity.container.KettleMenu;
import umpaz.farmersrespite.common.block.entity.inventory.KettleItemHandler;
import umpaz.farmersrespite.common.crafting.KettlePouringRecipe;
import umpaz.farmersrespite.common.crafting.KettleRecipe;
import umpaz.farmersrespite.common.registry.FRBlockEntityTypes;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.registry.FRRecipeTypes;
import umpaz.farmersrespite.common.registry.FRSounds;
import umpaz.farmersrespite.common.utility.FRTextUtils;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.utility.ItemUtils;

public class KettleBlockEntity extends SyncedBlockEntity implements MenuProvider, HeatableBlockEntity, Nameable, RecipeHolder {
   public static final int DRINK_DISPLAY_SLOT = 2;
   public static final int CONTAINER_SLOT = 3;
   public static final int OUTPUT_SLOT = 4;
   public static final int INVENTORY_SIZE = 5;
   public static boolean WHISTLE = false;
   private final ItemStackHandler inventory = this.createHandler();
   private final LazyOptional<IItemHandler> inputHandler = LazyOptional.of(() -> new KettleItemHandler(this.inventory, Direction.UP));
   private final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() -> new KettleItemHandler(this.inventory, Direction.DOWN));
   private final FluidTank fluidTank;
   private final LazyOptional<FluidTank> fluidTankHandler;
   private int brewTime;
   private int brewTimeTotal;
   private ItemStack drinkContainerStack = ItemStack.EMPTY;
   private Component customName;
   protected final ContainerData kettleData = this.createIntArray();
   private final Object2IntOpenHashMap<ResourceLocation> usedRecipeTracker = new Object2IntOpenHashMap();
   private ResourceLocation lastRecipeID;

   public KettleBlockEntity(BlockPos pos, BlockState state) {
      super((BlockEntityType)FRBlockEntityTypes.KETTLE.get(), pos, state);
      this.fluidTank = this.createFluidTank();
      this.fluidTankHandler = LazyOptional.of(() -> this.fluidTank);
   }

   public static FluidStack getMealFromItem(ItemStack kettleStack) {
      if (!kettleStack.is((Item)FRItems.KETTLE.get())) {
         return FluidStack.EMPTY;
      }

      CompoundTag compound = kettleStack.getTagElement("BlockEntityTag");
      return compound != null && compound.contains("FluidTank") ? FluidStack.loadFluidStackFromNBT(compound.getCompound("FluidTank")) : FluidStack.EMPTY;
   }

   public static ItemStack getContainerFromItem(ItemStack kettleStack) {
      if (!kettleStack.is((Item)FRItems.KETTLE.get())) {
         return ItemStack.EMPTY;
      }

      CompoundTag compound = kettleStack.getTagElement("BlockEntityTag");
      return compound != null ? ItemStack.of(compound.getCompound("Container")) : ItemStack.EMPTY;
   }

   public void load(CompoundTag compound) {
      super.load(compound);
      this.inventory.deserializeNBT(compound.getCompound("Inventory"));
      this.brewTime = compound.getInt("BrewTime");
      this.fluidTank.readFromNBT(compound.getCompound("FluidTank"));
      this.brewTimeTotal = compound.getInt("BrewTimeTotal");
      this.drinkContainerStack = ItemStack.of(compound.getCompound("Container"));
      if (compound.contains("CustomName", 8)) {
         this.customName = Serializer.fromJson(compound.getString("CustomName"));
      }

      CompoundTag compoundRecipes = compound.getCompound("RecipesUsed");

      for (String key : compoundRecipes.getAllKeys()) {
         this.usedRecipeTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
      }
   }

   public ItemStack fluidExtract(KettleBlockEntity kettle, ItemStack slotIn, ItemStack slotOut) {
      Item container = slotIn.getItem();
      ItemStack output = ItemStack.EMPTY;
      Optional<KettlePouringRecipe> recipe = kettle.getPouringRecipe(container, kettle.fluidTank.getFluid());
      boolean changed = false;
      if (recipe.isPresent() && (kettle.fluidTank.isEmpty() || kettle.fluidTank.getFluid().getFluid().isSame(recipe.get().getFluid()))) {
         if (container.equals(recipe.get().getContainer().getItem()) && recipe.get().getAmount() <= kettle.fluidTank.getFluidAmount()) {
            for (;
               kettle.fluidTank.getFluidAmount() >= recipe.get().getAmount()
                  && (
                     output.isEmpty() && slotOut.isEmpty()
                        || ItemHandlerHelper.canItemStacksStack(slotOut, recipe.get().getOutput().copyWithCount(output.getCount() + 1))
                  );
               changed = true
            ) {
               if (slotOut.getCount() + 1 > slotOut.getMaxStackSize()
                  || slotIn.getCount() == 0
                  || slotIn.isEmpty()
                  || slotIn.getCount() < recipe.get().getOutput().getCount()) {
                  break;
               }

               kettle.fluidTank.drain(new FluidStack(kettle.fluidTank.getFluid(), recipe.get().getAmount()), FluidAction.EXECUTE);
               slotIn.shrink(recipe.get().getContainer().getCount());
               if (output.isEmpty()) {
                  output = recipe.get().getOutput().copy();
               } else {
                  output.grow(recipe.get().getOutput().getCount());
               }
            }

            if (changed) {
               if (kettle.level.isClientSide()) {
                  kettle.level.playLocalSound(kettle.getBlockPos(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F, false);
               }

               this.setChanged();
            }
         } else if (container.equals(recipe.get().getOutput().getItem())
            && kettle.fluidTank.getFluidAmount() + recipe.get().getAmount() <= kettle.fluidTank.getCapacity()) {
            for (;
               kettle.fluidTank.getFluidAmount() + recipe.get().getAmount() <= kettle.fluidTank.getCapacity()
                  && slotIn.getCount() != 0
                  && !slotIn.isEmpty()
                  && slotIn.getCount() >= recipe.get().getContainer().getCount()
                  && (
                     output.isEmpty() && slotOut.isEmpty()
                        || ItemHandlerHelper.canItemStacksStack(slotOut, recipe.get().getContainer().copyWithCount(output.getCount() + 1))
                  );
               changed = true
            ) {
               if (slotOut.getCount() + 1 > slotOut.getMaxStackSize()) {
                  break;
               }

               kettle.fluidTank.fill(new FluidStack(recipe.get().getFluid(), recipe.get().getAmount()), FluidAction.EXECUTE);
               slotIn.shrink(recipe.get().getOutput().getCount());
               if (output.isEmpty()) {
                  output = recipe.get().getContainer().copy();
               } else {
                  output.grow(recipe.get().getContainer().getCount());
               }
            }

            if (changed) {
               if (kettle.level.isClientSide()) {
                  kettle.level.playLocalSound(kettle.getBlockPos(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.0F, false);
               }

               this.setChanged();
            }
         }
      }

      LazyOptional<IFluidHandlerItem> fluidHandler = slotIn.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM);
      IFluidHandlerItem iFluidItemHandler = (IFluidHandlerItem)fluidHandler.orElse(null);
      if (fluidHandler.isPresent() && !slotIn.isEmpty()) {
         if (kettle.fluidTank.getFluid().isFluidEqual(iFluidItemHandler.getFluidInTank(0)) || kettle.fluidTank.getFluid().isEmpty()) {
            int amountToDrain = kettle.fluidTank.getCapacity() - kettle.fluidTank.getFluidAmount();
            int amount = iFluidItemHandler.drain(amountToDrain, FluidAction.SIMULATE).getAmount();
            if (amount > 0) {
               kettle.fluidTank.fill(iFluidItemHandler.drain(amountToDrain, FluidAction.EXECUTE), FluidAction.EXECUTE);
               if (amount <= amountToDrain) {
                  slotIn.setCount(0);
                  if (output.isEmpty()) {
                     output = iFluidItemHandler.getContainer().copy();
                  } else {
                     output.grow(iFluidItemHandler.getContainer().getCount());
                  }

                  this.setChanged();
               }
            }
         } else if (!kettle.fluidTank.getFluid().isEmpty() && iFluidItemHandler.isFluidValid(0, kettle.fluidTank.getFluid())) {
            int amountToDrain = kettle.fluidTank.getFluidAmount();
            int amount = iFluidItemHandler.fill(new FluidStack(kettle.fluidTank.getFluid(), amountToDrain), FluidAction.SIMULATE);
            if (amount > 0) {
               iFluidItemHandler.fill(new FluidStack(kettle.fluidTank.getFluid(), amountToDrain), FluidAction.EXECUTE);
               kettle.fluidTank.drain(amountToDrain, FluidAction.EXECUTE);
               if (amount <= amountToDrain) {
                  slotIn.setCount(0);
                  if (output.isEmpty()) {
                     output = iFluidItemHandler.getContainer().copy();
                  } else {
                     output.grow(iFluidItemHandler.getContainer().getCount());
                  }

                  this.setChanged();
               }
            }
         }
      }

      return output;
   }

   public void saveAdditional(CompoundTag compound) {
      super.saveAdditional(compound);
      compound.putInt("BrewTime", this.brewTime);
      compound.putInt("BrewTimeTotal", this.brewTimeTotal);
      compound.put("Container", this.drinkContainerStack.serializeNBT());
      if (this.customName != null) {
         compound.putString("CustomName", Serializer.toJson(this.customName));
      }

      compound.put("FluidTank", this.fluidTank.writeToNBT(new CompoundTag()));
      compound.put("Inventory", this.inventory.serializeNBT());
      CompoundTag compoundRecipes = new CompoundTag();
      this.usedRecipeTracker.forEach((recipeId, craftedAmount) -> compoundRecipes.putInt(recipeId.toString(), craftedAmount));
      compound.put("RecipesUsed", compoundRecipes);
   }

   private CompoundTag writeItems(CompoundTag compound) {
      super.saveAdditional(compound);
      compound.put("Container", this.drinkContainerStack.serializeNBT());
      compound.put("Inventory", this.inventory.serializeNBT());
      compound.put("FluidTank", this.fluidTank.writeToNBT(new CompoundTag()));
      return compound;
   }

   public CompoundTag writeMeal(CompoundTag compound) {
      if (this.getOutput().isEmpty()) {
         return compound;
      }

      ItemStackHandler drops = new ItemStackHandler(5);

      for (int i = 0; i < 5; i++) {
         drops.setStackInSlot(i, i == 2 ? this.inventory.getStackInSlot(i) : ItemStack.EMPTY);
      }

      if (this.customName != null) {
         compound.putString("CustomName", Serializer.toJson(this.customName));
      }

      compound.put("Container", this.drinkContainerStack.serializeNBT());
      compound.put("Inventory", drops.serializeNBT());
      if (!this.fluidTank.isEmpty()) {
         compound.put("FluidTank", this.fluidTank.writeToNBT(new CompoundTag()));
      }

      return compound;
   }

   public static void brewingTick(Level level, BlockPos pos, BlockState state, KettleBlockEntity kettle) {
      boolean isHeated = kettle.isHeated(level, pos);
      boolean didInventoryChange = false;
      if (isHeated && kettle.hasInput()) {
         Optional<KettleRecipe> recipe = kettle.getMatchingRecipe(new RecipeWrapper(kettle.inventory));
         if (recipe.isPresent() && kettle.canBrew(recipe.get(), kettle)) {
            didInventoryChange = kettle.processBrewing(recipe.get(), kettle);
         } else {
            kettle.brewTime = 0;
         }
      } else if (kettle.brewTime > 0) {
         kettle.brewTime = Mth.clamp(kettle.brewTime - 2, 0, kettle.brewTimeTotal);
      }

      ItemStack out = kettle.fluidExtract(kettle, kettle.inventory.getStackInSlot(3), kettle.inventory.getStackInSlot(4));
      if (!out.isEmpty()) {
         kettle.inventory.insertItem(4, out, false);
         didInventoryChange = true;
      }

      if (didInventoryChange) {
         kettle.inventoryChanged();
      }
   }

   public Optional<KettlePouringRecipe> getPouringRecipe(Item slot, FluidStack fluid) {
      if (this.level == null) {
         return Optional.empty();
      } else {
         Optional<KettlePouringRecipe> recipe = this.level
            .getRecipeManager()
            .getAllRecipesFor(FRRecipeTypes.KETTLE_POURING.get())
            .stream()
            .filter(
               r -> (r.getContainer().getItem() == slot || r.getOutput().getItem() == slot) && (fluid.isEmpty() || r.getFluid().isSame(fluid.getFluid()))
            )
            .findFirst();
         if (recipe.isPresent()) {
            return recipe;
         } else {
            return Optional.empty();
         }
      }
   }

   public boolean isValidBrewingIngredient(ItemStack stack) {
      if (this.level == null || stack.isEmpty()) {
         return false;
      }

      return this.level
         .getRecipeManager()
         .getAllRecipesFor(FRRecipeTypes.BREWING.get())
         .stream()
         .flatMap(recipe -> recipe.getIngredients().stream())
         .anyMatch(ingredient -> ingredient.test(stack));
   }

   public boolean isValidPouringContainer(ItemStack stack) {
      if (this.level == null || stack.isEmpty()) {
         return false;
      }

      ItemStack existing = this.inventory.getStackInSlot(CONTAINER_SLOT);
      if (!existing.isEmpty() && ItemStack.isSameItemSameTags(existing, stack)) {
         return true;
      }

      return this.level
         .getRecipeManager()
         .getAllRecipesFor(FRRecipeTypes.KETTLE_POURING.get())
         .stream()
         .anyMatch(recipe -> recipe.getContainer().getItem() == stack.getItem());
   }

   public int getRequiredIngredientCount(ItemStack stack) {
      if (this.level == null || stack.isEmpty()) {
         return 0;
      }

      FluidStack fluid = this.fluidTank.getFluid();
      int maxCount = 0;

      for (KettleRecipe recipe : this.level.getRecipeManager().getAllRecipesFor(FRRecipeTypes.BREWING.get())) {
         if (!fluid.isEmpty() && !recipe.getFluidIn().getFluid().isSame(fluid.getFluid())) {
            continue;
         }

         int matchCount = 0;
         for (Ingredient ingredient : recipe.getIngredients()) {
            if (ingredient.test(stack)) {
               matchCount++;
            }
         }

         if (matchCount > maxCount) {
            maxCount = matchCount;
         }
      }

      return maxCount;
   }

   public boolean needsBothIngredientSlots(ItemStack stack) {
      return this.getRequiredIngredientCount(stack) >= 2;
   }

   public static void animationTick(Level level, BlockPos pos, BlockState state, KettleBlockEntity kettle) {
      boolean i = (Boolean)state.getValue(KettleBlock.LID);
      FluidStack mealStack = kettle.getOutput();
      if (kettle.isHeated(level, pos) && i && !mealStack.isEmpty()) {
         Direction direction = (Direction)state.getValue(KettleBlock.FACING);
         Axis direction$axis = direction.getAxis();
         double d0 = pos.getX() + 0.5;
         double d1 = pos.getY() + 0.5;
         double d2 = pos.getZ() + 0.5;
         double d4 = 0.0;
         double d5 = direction$axis == Axis.X ? direction.getStepX() * 0.52 : d4;
         double d6 = 0.0;
         double d7 = direction$axis == Axis.Z ? direction.getStepZ() * 0.52 : d4;
         if (level.random.nextInt(5) == 0) {
            level.addParticle(ParticleTypes.EFFECT, d0 + d5, d1 + d6, d2 + d7, 0.0, 0.0, 0.0);
         }

         if (WHISTLE) {
            whistleNoise(level, pos, state, kettle);
            WHISTLE = false;
         }
      }
   }

   public static void whistleNoise(Level level, BlockPos pos, BlockState state, KettleBlockEntity kettle) {
      boolean i = (Boolean)state.getValue(KettleBlock.LID);
      if (kettle.isHeated() && i) {
         double x = pos.getX() + 0.5;
         double y = pos.getY();
         double z = pos.getZ() + 0.5;
         float pitch = RandomSource.create().nextFloat() * 0.2F + 0.9F;
         level.playLocalSound(x, y, z, (SoundEvent)FRSounds.BLOCK_KETTLE_WHISTLE.get(), SoundSource.BLOCKS, 0.25F, pitch, false);
      }
   }

   private Optional<KettleRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
      if (this.level == null) {
         return Optional.empty();
      }

      if (this.lastRecipeID != null) {
         Recipe<RecipeWrapper> recipe = (Recipe<RecipeWrapper>)((RecipeManagerAccessor)this.level.getRecipeManager())
            .getRecipeMap(FRRecipeTypes.BREWING.get())
            .get(this.lastRecipeID);
         if (recipe instanceof KettleRecipe kettleRecipe
            && recipe.matches(inventoryWrapper, this.level)
            && kettleRecipe.getFluidIn().isFluidEqual(this.fluidTank.getFluid())) {
            return Optional.of(kettleRecipe);
         }
      }

      Optional<KettleRecipe> recipe = this.level
         .getRecipeManager()
         .getAllRecipesFor(FRRecipeTypes.BREWING.get())
         .stream()
         .filter(a -> a.matches(inventoryWrapper, this.level) && a.getFluidIn().getFluid().isSame(this.fluidTank.getFluid().getFluid()))
         .findFirst();
      if (recipe.isPresent()) {
         this.lastRecipeID = recipe.get().getId();
      } else {
         this.lastRecipeID = null;
      }

      return recipe;
   }

   public ItemStack getContainer() {
      return this.drinkContainerStack;
   }

   private boolean hasInput() {
      for (int i = 0; i < 2; i++) {
         if (!this.inventory.getStackInSlot(i).isEmpty()) {
            return true;
         }
      }

      return false;
   }

   protected boolean canBrew(KettleRecipe recipe, KettleBlockEntity kettle) {
      if (!this.hasInput()) {
         return false;
      } else if (this.level == null) {
         return false;
      } else {
         FluidStack resultStack = recipe.getFluidOut();
         if (resultStack.isEmpty()) {
            return false;
         } else if (!kettle.fluidTank.getFluid().getFluid().isSame(recipe.getFluidIn().getFluid())) {
            return false;
         } else {
            return kettle.fluidTank.getFluidAmount() % recipe.getFluidIn().getAmount() != 0
               ? false
               : FluidStack.areFluidStackTagsEqual(this.fluidTank.getFluid(), resultStack);
         }
      }
   }

   private boolean processBrewing(KettleRecipe recipe, KettleBlockEntity kettle) {
      if (this.level == null) {
         return false;
      }

      this.brewTime++;
      this.brewTimeTotal = recipe.getBrewTime();
      if (this.brewTime < this.brewTimeTotal) {
         return false;
      }

      this.brewTime = 0;
      if (recipe.getFluidOut() != null) {
         kettle.fluidTank.setFluid(new FluidStack(recipe.getFluidOut(), kettle.fluidTank.getFluidAmount()));
         if (kettle.level.isClientSide()) {
            kettle.level.playLocalSound(kettle.getBlockPos(), SoundEvents.BREWING_STAND_BREW, SoundSource.BLOCKS, 1.0F, 0.8F, true);
         }

         WHISTLE = true;
      }

      kettle.setRecipeUsed(recipe);

      for (int i = 0; i < 2; i++) {
         ItemStack slotStack = this.inventory.getStackInSlot(i);
         if (slotStack.hasCraftingRemainingItem()) {
            Direction direction = ((Direction)this.getBlockState().getValue(KettleBlock.FACING)).getCounterClockWise();
            double x = this.worldPosition.getX() + 0.5 + direction.getStepX() * 0.25;
            double y = this.worldPosition.getY() + 0.7;
            double z = this.worldPosition.getZ() + 0.5 + direction.getStepZ() * 0.25;
            ItemUtils.spawnItemEntity(
               this.level,
               this.inventory.getStackInSlot(i).getCraftingRemainingItem(),
               x,
               y,
               z,
               direction.getStepX() * 0.08F,
               0.25,
               direction.getStepZ() * 0.08F
            );
         }

         if (!slotStack.isEmpty()) {
            slotStack.shrink(1);
         }
      }

      return true;
   }

   public void setRecipeUsed(@Nullable Recipe<?> recipe) {
      if (recipe != null) {
         ResourceLocation recipeID = recipe.getId();
         this.usedRecipeTracker.addTo(recipeID, 1);
      }
   }

   @Nullable
   public Recipe<?> getRecipeUsed() {
      return null;
   }

   public void awardUsedRecipes(Player player, List<ItemStack> items) {
      List<Recipe<?>> usedRecipes = this.getUsedRecipesAndPopExperience(player.level(), player.position());
      player.awardRecipes(usedRecipes);
      this.usedRecipeTracker.clear();
   }

   public List<Recipe<?>> getUsedRecipesAndPopExperience(Level level, Vec3 pos) {
      List<Recipe<?>> list = Lists.newArrayList();
      ObjectIterator var4 = this.usedRecipeTracker.object2IntEntrySet().iterator();

      while (var4.hasNext()) {
         Entry<ResourceLocation> entry = (Entry<ResourceLocation>)var4.next();
         level.getRecipeManager().byKey((ResourceLocation)entry.getKey()).ifPresent(recipe -> {
            list.add((Recipe<?>)recipe);
            splitAndSpawnExperience((ServerLevel)level, pos, entry.getIntValue(), ((KettleRecipe)recipe).getExperience());
         });
      }

      return list;
   }

   private static void splitAndSpawnExperience(ServerLevel level, Vec3 pos, int craftedAmount, float experience) {
      int expTotal = Mth.floor(craftedAmount * experience);
      float expFraction = Mth.frac(craftedAmount * experience);
      if (expFraction != 0.0F && Math.random() < expFraction) {
         expTotal++;
      }

      ExperienceOrb.award(level, pos, expTotal);
   }

   public boolean isHeated() {
      return this.level == null ? false : this.isHeated(this.level, this.worldPosition);
   }

   public ItemStackHandler getInventory() {
      return this.inventory;
   }

   public FluidTank getFluidTank() {
      return this.fluidTank;
   }

   public FluidStack getOutput() {
      return this.fluidTank.getFluid();
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

   public Component getName() {
      return (Component)(this.customName != null ? this.customName : FRTextUtils.getTranslation("container.kettle"));
   }

   public Component getDisplayName() {
      return this.getName();
   }

   @Nullable
   public Component getCustomName() {
      return this.customName;
   }

   public void setCustomName(Component name) {
      this.customName = name;
   }

   public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
      return new KettleMenu(id, player, this, this.kettleData);
   }

   @Nonnull
   public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
      if (cap.equals(ForgeCapabilities.ITEM_HANDLER)) {
         return side != null && !side.equals(Direction.UP) ? this.outputHandler.cast() : this.inputHandler.cast();
      } else {
         return cap.equals(ForgeCapabilities.FLUID_HANDLER) ? this.fluidTankHandler.cast() : super.getCapability(cap, side);
      }
   }

   public void setRemoved() {
      super.setRemoved();
      this.inputHandler.invalidate();
      this.outputHandler.invalidate();
      this.fluidTankHandler.invalidate();
   }

   public CompoundTag getUpdateTag() {
      return this.writeItems(new CompoundTag());
   }

   private ItemStackHandler createHandler() {
      return new ItemStackHandler(5) {
         @Override
         public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return switch (slot) {
               case 0, 1 -> KettleBlockEntity.this.isValidBrewingIngredient(stack);
               case 3 -> KettleBlockEntity.this.isValidPouringContainer(stack);
               default -> false;
            };
         }

         @Override
         public int getSlotLimit(int slot) {
            return slot < 2 ? 1 : super.getSlotLimit(slot);
         }

         @Nonnull
         @Override
         public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            ItemStack remaining = super.insertItem(slot, stack, simulate);
            if (slot < 2 && !remaining.isEmpty() && KettleBlockEntity.this.needsBothIngredientSlots(stack)) {
               int otherSlot = slot == 0 ? 1 : 0;
               ItemStack otherStack = this.getStackInSlot(otherSlot);
               if ((otherStack.isEmpty() || ItemStack.isSameItemSameTags(otherStack, stack)) && this.isItemValid(otherSlot, stack)) {
                  remaining = super.insertItem(otherSlot, remaining, simulate);
               }
            }

            return remaining;
         }

         protected void onContentsChanged(int slot) {
            if (slot >= 0 && slot < 2) {
               KettleBlockEntity.this.lastRecipeID = null;
            }

            KettleBlockEntity.this.inventoryChanged();
         }
      };
   }

   private FluidTank createFluidTank() {
      return new FluidTank(1000) {
         protected void onContentsChanged() {
            super.onContentsChanged();
            KettleBlockEntity.this.lastRecipeID = null;
            KettleBlockEntity.this.setChanged();
            KettleBlockEntity.this.inventoryChanged();
         }
      };
   }

   private ContainerData createIntArray() {
      return new ContainerData() {
         public int get(int index) {
            return switch (index) {
               case 0 -> KettleBlockEntity.this.brewTime;
               case 1 -> KettleBlockEntity.this.brewTimeTotal;
               default -> 0;
            };
         }

         public void set(int index, int value) {
            switch (index) {
               case 0:
                  KettleBlockEntity.this.brewTime = value;
                  break;
               case 1:
                  KettleBlockEntity.this.brewTimeTotal = value;
            }
         }

         public int getCount() {
            return 2;
         }
      };
   }
}
