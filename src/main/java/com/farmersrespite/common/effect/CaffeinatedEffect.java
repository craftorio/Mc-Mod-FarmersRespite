package com.farmersrespite.common.effect;

import com.farmersrespite.core.registry.FREffects;
import com.google.common.collect.Sets;

import java.util.Set;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

public class CaffeinatedEffect
        extends MobEffect {
    public static final Set<MobEffect> CAFFINATED_IMMUNITIES = Sets.newHashSet();

    public CaffeinatedEffect() {
        super(MobEffectCategory.BENEFICIAL, 12161815);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "ca4cd828-53ad-4ce7-93da-92684d75be47", 0.009999999776482582D, AttributeModifier.Operation.MULTIPLY_TOTAL);
        addAttributeModifier(Attributes.ATTACK_SPEED, "3e07acfc-7b1d-40a1-af8c-fbe34be88b3a", 0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (this == FREffects.CAFFEINATED.get() &&
                entity instanceof ServerPlayer playerMP) {
            ServerStatsCounter serverStatsCounter = playerMP.getStats();
            serverStatsCounter.increment(playerMP, Stats.CUSTOM.get(Stats.TIME_SINCE_REST), -(24000 * (amplifier + 1)));
        }

    }

    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @EventBusSubscriber(modid = "farmersrespite", bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CaffeinatedEffectFunctions {
        @SubscribeEvent
        public static void onCaffinatedDuration(PotionEvent.PotionApplicableEvent event) {
            MobEffectInstance effect = event.getPotionEffect();
            LivingEntity entity = event.getEntityLiving();
            if (entity.getEffect(FREffects.CAFFEINATED.get()) != null && CaffeinatedEffect.CAFFINATED_IMMUNITIES.contains(effect.getEffect())) {
                event.setResult(Event.Result.DENY);
            }
        }

        @SubscribeEvent
        public static void onCaffinatedApplied(PotionEvent.PotionAddedEvent event) {
            MobEffectInstance addedEffect = event.getPotionEffect();
            LivingEntity entity = event.getEntityLiving();
            if (addedEffect.getEffect().equals(FREffects.CAFFEINATED.get())) {
                for (MobEffect effect : CaffeinatedEffect.CAFFINATED_IMMUNITIES) {
                    entity.removeEffect(effect);
                }
            }
        }
    }
}
