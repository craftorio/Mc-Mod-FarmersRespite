package umpaz.farmersrespite.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class StrongMelonJuiceItem extends DrinkableItem {
   public StrongMelonJuiceItem(Properties properties) {
      super(properties, false, true);
   }

   public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
      consumer.heal(4.0F);
   }
}
