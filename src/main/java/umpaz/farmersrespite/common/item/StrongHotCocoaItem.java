package umpaz.farmersrespite.common.item;

import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent.Remove;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class StrongHotCocoaItem extends DrinkableItem {
   public StrongHotCocoaItem(Properties properties) {
      super(properties, false, true);
   }

   public void affectConsumer(ItemStack stack, Level level, LivingEntity consumer) {
      Iterator<MobEffectInstance> itr = consumer.getActiveEffects().iterator();
      ArrayList<MobEffect> compatibleEffects = new ArrayList<>();

      while (itr.hasNext()) {
         MobEffectInstance effect = itr.next();
         if (effect.getEffect().getCategory().equals(MobEffectCategory.HARMFUL) && effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET))) {
            compatibleEffects.add(effect.getEffect());
         }
      }

      if (compatibleEffects.size() > 0) {
         MobEffectInstance selectedEffect = consumer.getEffect(compatibleEffects.get(level.random.nextInt(compatibleEffects.size())));
         if (selectedEffect != null && !MinecraftForge.EVENT_BUS.post(new Remove(consumer, selectedEffect))) {
            consumer.removeEffect(selectedEffect.getEffect());
         }
      }

      if (compatibleEffects.size() > 1) {
         MobEffectInstance selectedEffect = consumer.getEffect(compatibleEffects.get(level.random.nextInt(compatibleEffects.size())));
         if (selectedEffect != null && !MinecraftForge.EVENT_BUS.post(new Remove(consumer, selectedEffect))) {
            consumer.removeEffect(selectedEffect.getEffect());
         }
      }
   }
}
