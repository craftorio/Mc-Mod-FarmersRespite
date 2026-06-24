package umpaz.farmersrespite.common.block.entity.container;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

@ParametersAreNonnullByDefault
public class KettleIngredientSlot extends SlotItemHandler {
   public KettleIngredientSlot(IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
      super(inventoryIn, index, xPosition, yPosition);
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
