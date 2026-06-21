package umpaz.farmersrespite.common.effect;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.stats.StatsCounter;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import umpaz.farmersrespite.common.registry.FREffects;

public class CaffeinatedEffect extends MobEffect {
   public CaffeinatedEffect() {
      super(MobEffectCategory.BENEFICIAL, 3348480);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "ca4cd828-53ad-4ce7-93da-92684d75be47", 0.1F, Operation.MULTIPLY_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, "3e07acfc-7b1d-40a1-af8c-fbe34be88b3a", 0.2F, Operation.MULTIPLY_TOTAL);
   }

   public void applyEffectTick(LivingEntity entity, int amplifier) {
      if (this == FREffects.CAFFEINATED.get() && entity instanceof ServerPlayer playerMP) {
         StatsCounter statisticsManager = playerMP.getStats();
         statisticsManager.increment(playerMP, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), -(24000 * (amplifier + 1)));
      }
   }

   public boolean isDurationEffectTick(int duration, int amplifier) {
      return true;
   }
}
