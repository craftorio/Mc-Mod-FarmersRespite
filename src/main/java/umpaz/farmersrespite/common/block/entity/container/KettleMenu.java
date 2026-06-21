package umpaz.farmersrespite.common.block.entity.container;

import com.mojang.datafixers.util.Pair;
import java.util.Objects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.RecipeBookType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import umpaz.farmersrespite.FarmersRespite;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRMenuTypes;

public class KettleMenu extends RecipeBookMenu<RecipeWrapper> {
   public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOTTLE = new ResourceLocation("farmersrespite", "item/empty_container_slot_bottle");
   public final FluidTank kettleTank;
   public final KettleBlockEntity tileEntity;
   public final ItemStackHandler inventory;
   private final ContainerData kettleData;
   private final ContainerLevelAccess canInteractWithCallable;
   protected final Level level;

   public KettleMenu(int windowId, Inventory playerInventory, KettleBlockEntity tileEntity, ContainerData kettleDataIn) {
      super((MenuType)FRMenuTypes.KETTLE.get(), windowId);
      this.tileEntity = tileEntity;
      this.inventory = tileEntity.getInventory();
      this.kettleData = kettleDataIn;
      this.kettleTank = tileEntity.getFluidTank();
      this.level = playerInventory.player.level();
      this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());
      int startX = 8;
      int startY = 18;
      int inputStartX = 42;
      int inputStartY = 17;
      int borderSlotSize = 18;

      for (int row = 0; row < 2; row++) {
         for (int column = 0; column < 1; column++) {
            this.addSlot(new SlotItemHandler(this.inventory, row * 1 + column, inputStartX + column * borderSlotSize, inputStartY + row * borderSlotSize));
         }
      }

      this.addSlot(new SlotItemHandler(this.inventory, 3, 86, 55) {
         @OnlyIn(Dist.CLIENT)
         public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
            return Pair.of(InventoryMenu.BLOCK_ATLAS, KettleMenu.EMPTY_CONTAINER_SLOT_BOTTLE);
         }
      });
      this.addSlot(new KettleResultSlot(playerInventory.player, tileEntity, this.inventory, 4, 118, 55));
      int startPlayerInvY = startY * 4 + 12;

      for (int row = 0; row < 3; row++) {
         for (int column = 0; column < 9; column++) {
            this.addSlot(new Slot(playerInventory, 9 + row * 9 + column, startX + column * borderSlotSize, startPlayerInvY + row * borderSlotSize));
         }
      }

      for (int column = 0; column < 9; column++) {
         this.addSlot(new Slot(playerInventory, column, startX + column * borderSlotSize, 142));
      }

      this.addDataSlots(kettleDataIn);
   }

   private static KettleBlockEntity getTileEntity(Inventory playerInventory, FriendlyByteBuf data) {
      Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
      Objects.requireNonNull(data, "data cannot be null");
      BlockEntity tileAtPos = playerInventory.player.level().getBlockEntity(data.readBlockPos());
      if (tileAtPos instanceof KettleBlockEntity) {
         return (KettleBlockEntity)tileAtPos;
      } else {
         throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
      }
   }

   public KettleMenu(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
      this(windowId, playerInventory, getTileEntity(playerInventory, data), new SimpleContainerData(4));
   }

   public boolean stillValid(Player playerIn) {
      return stillValid(this.canInteractWithCallable, playerIn, (Block)FRBlocks.KETTLE.get());
   }

   public ItemStack quickMoveStack(Player playerIn, int index) {
      int indexContainerInput = 2;
      int indexOutput = 3;
      int startPlayerInv = indexOutput + 1;
      int endPlayerInv = startPlayerInv + 36;
      Slot slot = (Slot)this.slots.get(index);
      ItemStack slotStackCopy = ItemStack.EMPTY;
      if (slot.hasItem()) {
         ItemStack slotStack = slot.getItem();
         slotStackCopy = slotStack.copy();
         if (index == indexOutput) {
            if (!this.moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, true)) {
               return ItemStack.EMPTY;
            }
         } else if (index <= indexOutput) {
            if (!this.moveItemStackTo(slotStack, startPlayerInv, endPlayerInv, false)) {
               return ItemStack.EMPTY;
            }
         } else {
            boolean isValidContainer = slotStack.is(this.tileEntity.getInventory().getStackInSlot(indexContainerInput).getItem())
               || this.tileEntity.getPouringRecipe(slotStack.getItem(), this.tileEntity.getFluidTank().getFluid()).isPresent()
               || slotStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).isPresent();
            if (isValidContainer && !this.moveItemStackTo(slotStack, indexContainerInput, indexContainerInput + 1, false)) {
               return ItemStack.EMPTY;
            }

            if (!this.moveItemStackTo(slotStack, 0, indexContainerInput, false)) {
               return ItemStack.EMPTY;
            }
         }

         if (slotStack.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }

         if (slotStack.getCount() == slotStackCopy.getCount()) {
            return ItemStack.EMPTY;
         }

         slot.onTake(playerIn, slotStack);
      }

      return slotStackCopy;
   }

   @OnlyIn(Dist.CLIENT)
   public int getBrewProgressionScaled() {
      int i = this.kettleData.get(0);
      int j = this.kettleData.get(1);
      return j != 0 && i != 0 ? i * 40 / j : 0;
   }

   @OnlyIn(Dist.CLIENT)
   public boolean isHeated() {
      return this.tileEntity.isHeated();
   }

   public void fillCraftSlotsStackedContents(StackedContents helper) {
      for (int i = 0; i < this.inventory.getSlots(); i++) {
         helper.accountSimpleStack(this.inventory.getStackInSlot(i));
      }
   }

   public void clearCraftingContent() {
      for (int i = 0; i < 2; i++) {
         this.inventory.setStackInSlot(i, ItemStack.EMPTY);
      }
   }

   public boolean recipeMatches(Recipe<? super RecipeWrapper> recipe) {
      return recipe.matches(new RecipeWrapper(this.inventory), this.level);
   }

   public int getResultSlotIndex() {
      return 3;
   }

   public int getGridWidth() {
      return 1;
   }

   public int getGridHeight() {
      return 2;
   }

   public int getSize() {
      return 3;
   }

   public RecipeBookType getRecipeBookType() {
      return FarmersRespite.RECIPE_TYPE_BREWING;
   }

   public boolean shouldMoveToInventory(int slot) {
      return slot < this.getGridWidth() * this.getGridHeight();
   }
}
