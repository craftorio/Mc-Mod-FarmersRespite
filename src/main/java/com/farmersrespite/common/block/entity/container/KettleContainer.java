package com.farmersrespite.common.block.entity.container;

import com.farmersrespite.common.block.KettleBlock;
import com.farmersrespite.common.block.entity.KettleBlockEntity;
import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRContainerTypes;
import com.mojang.datafixers.util.Pair;

import java.util.Objects;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class KettleContainer extends AbstractContainerMenu {
    public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOTTLE = new ResourceLocation("farmersrespite", "item/empty_container_slot_bottle");

    public final KettleBlockEntity tileEntity;
    public final ItemStackHandler inventory;
    private final ContainerData kettleData;
    private final ContainerLevelAccess canInteractWithCallable;

    public KettleContainer(int windowId, Inventory playerInventory, KettleBlockEntity tileEntity, ContainerData kettleDataIn) {
        super(FRContainerTypes.KETTLE.get(), windowId);
        this.tileEntity = tileEntity;
        this.inventory = tileEntity.getInventory();
        this.kettleData = kettleDataIn;
        this.canInteractWithCallable = ContainerLevelAccess.create(tileEntity.getLevel(), tileEntity.getBlockPos());


        int startX = 8;
        int startY = 18;
        int inputStartX = 42;
        int inputStartY = 17;
        int borderSlotSize = 18;
        for (int row = 0; row < 2; row++) {
            for (int j = 0; j < 1; j++) {
                addSlot(new SlotItemHandler(this.inventory, row + j, inputStartX + j * borderSlotSize, inputStartY + row * borderSlotSize));
            }
        }


        addSlot(new KettleMealSlot(this.inventory, 2, 118, 26));


        addSlot(new SlotItemHandler(this.inventory, 3, 86, 55) {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, KettleContainer.EMPTY_CONTAINER_SLOT_BOTTLE);
            }
        });


        addSlot(new KettleResultSlot(playerInventory.player, tileEntity, this.inventory, 4, 118, 55));


        int startPlayerInvY = startY * 4 + 12;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlot(new Slot(playerInventory, 9 + i * 9 + j, startX + j * borderSlotSize, startPlayerInvY + i * borderSlotSize));
            }
        }


        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, startX + column * borderSlotSize, 142));
        }

        addDataSlots(kettleDataIn);
    }

    public KettleContainer(int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data), new SimpleContainerData(4));
    }

    private static KettleBlockEntity getTileEntity(Inventory playerInventory, FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        BlockEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof KettleBlockEntity) {
            return (KettleBlockEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    public boolean stillValid(Player playerIn) {
        return stillValid(this.canInteractWithCallable, playerIn, FRBlocks.KETTLE.get());
    }


    public ItemStack quickMoveStack(Player playerIn, int index) {
        int indexMealDisplay = 2;
        int indexContainerInput = 3;
        int indexOutput = 4;
        int startPlayerInv = indexOutput + 1;
        int endPlayerInv = startPlayerInv + 36;
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index == indexOutput) {
                if (!moveItemStackTo(itemstack1, startPlayerInv, endPlayerInv, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index > indexOutput) {
                if (itemstack1.getItem() == Items.GLASS_BOTTLE && !moveItemStackTo(itemstack1, indexContainerInput, indexContainerInput + 1, false))
                    return ItemStack.EMPTY;
                if (!moveItemStackTo(itemstack1, 0, indexMealDisplay, false))
                    return ItemStack.EMPTY;
                if (!moveItemStackTo(itemstack1, indexContainerInput, indexOutput, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(itemstack1, startPlayerInv, endPlayerInv, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }
        return itemstack;
    }

    @OnlyIn(Dist.CLIENT)
    public int getBrewProgressionScaled() {
        int i = this.kettleData.get(0);
        int j = this.kettleData.get(1);
        return (j != 0 && i != 0) ? (i * 40 / j) : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isHeated() {
        return this.tileEntity.isHeated();
    }

    @OnlyIn(Dist.CLIENT)
    public int waterLevel() {
        BlockState state = this.tileEntity.getLevel().getBlockState(this.tileEntity.getBlockPos());
        int i = state.getValue(KettleBlock.WATER_LEVEL);
        if (i == 1) {
            return 1;
        }
        if (i == 2) {
            return 2;
        }
        if (i == 3) {
            return 3;
        }
        return 0;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isWater() {
        return (waterLevel() > 0);
    }
}
