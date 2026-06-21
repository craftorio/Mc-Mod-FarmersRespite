package umpaz.farmersrespite.common.item;

import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fluids.FluidStack;
import umpaz.farmersrespite.client.gui.KettleTooltip;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;

public class KettleItem extends BlockItem {
   private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);

   public KettleItem(Block block, Properties properties) {
      super(block, properties);
   }

   public boolean isBarVisible(ItemStack stack) {
      return getServingCount(stack) > 0;
   }

   public int getBarWidth(ItemStack stack) {
      return Math.min(1 + getServingCount(stack) / 77, 13);
   }

   public int getBarColor(ItemStack stack) {
      return BAR_COLOR;
   }

   public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
      FluidStack mealStack = KettleBlockEntity.getMealFromItem(stack);
      return Optional.of(new KettleTooltip.KettleTooltipComponent(mealStack));
   }

   private static int getServingCount(ItemStack stack) {
      CompoundTag nbt = stack.getTagElement("BlockEntityTag");
      if (nbt == null) {
         return 0;
      }

      FluidStack mealStack = KettleBlockEntity.getMealFromItem(stack);
      return mealStack.getAmount();
   }
}
