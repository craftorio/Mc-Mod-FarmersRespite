package com.farmersrespite.common.block.entity.container;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

@ParametersAreNonnullByDefault
public class KettleMealSlot
        extends SlotItemHandler {
    public KettleMealSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }


    public boolean mayPlace(ItemStack stack) {
        return false;
    }


    public boolean mayPickup(Player playerIn) {
        return false;
    }
}
