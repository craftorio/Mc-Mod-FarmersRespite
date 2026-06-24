package umpaz.farmersrespite.common.block.entity.container;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;

@ParametersAreNonnullByDefault
public class KettleIngredientSlot extends SlotItemHandler {
   private final KettleBlockEntity tileEntity;

   public KettleIngredientSlot(KettleBlockEntity tileEntity, IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
      super(inventoryIn, index, xPosition, yPosition);
      this.tileEntity = tileEntity;
   }

   @Override
   public boolean mayPlace(ItemStack stack) {
      return this.tileEntity.isValidBrewingIngredient(stack);
   }

   @Override
   public int getMaxStackSize() {
      return 1;
   }

   @Override
   public int getMaxStackSize(ItemStack stack) {
      return 1;
   }
}
