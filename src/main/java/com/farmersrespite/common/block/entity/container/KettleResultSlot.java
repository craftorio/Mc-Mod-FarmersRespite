package com.farmersrespite.common.block.entity.container;

import com.farmersrespite.common.block.entity.KettleBlockEntity;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;


@ParametersAreNonnullByDefault
public class KettleResultSlot
        extends SlotItemHandler {
    public final KettleBlockEntity tileEntity;
    private final Player player;
    private int removeCount;

    public KettleResultSlot(Player player, KettleBlockEntity tile, IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.tileEntity = tile;
        this.player = player;
    }


    public boolean mayPlace(ItemStack stack) {
        return false;
    }


    @Nonnull
    public ItemStack remove(int amount) {
        if (hasItem()) {
            this.removeCount += Math.min(amount, getItem().getCount());
        }

        return super.remove(amount);
    }


    public void onTake(Player thePlayer, ItemStack stack) {
        checkTakeAchievements(stack);
        super.onTake(thePlayer, stack);
    }


    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removeCount += amount;
        checkTakeAchievements(stack);
    }


    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);

        if (!this.player.level.isClientSide) {
            this.tileEntity.clearUsedRecipes(this.player);
        }

        this.removeCount = 0;
    }
}