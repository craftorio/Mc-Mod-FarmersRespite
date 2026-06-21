package umpaz.farmersrespite.common.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class GamblersTeaItem extends DrinkableItem {
   private final int duration;
   private final int amplifier;

   public GamblersTeaItem(int duration, int amplifier, Properties properties) {
      super(properties, false, true);
      this.duration = duration;
      this.amplifier = amplifier;
   }

   public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
      if (level.random.nextBoolean()) {
         consumer.addEffect(new MobEffectInstance(MobEffects.GLOWING, this.duration, 0), consumer);
         if (this.amplifier == 1) {
            consumer.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, this.duration, 0), consumer);
         }
      } else {
         consumer.addEffect(new MobEffectInstance(MobEffects.WITHER, this.duration, this.amplifier), consumer);
      }
   }
}
