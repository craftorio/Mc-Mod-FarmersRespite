package umpaz.farmersrespite.common;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import umpaz.farmersrespite.common.registry.FREffects;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class FRFoodValues {
   public static final FoodProperties GREEN_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, ticksFromMinutesDuration(3.0F), 0), 1.0F)
      .build();
   public static final FoodProperties YELLOW_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, ticksFromMinutesDuration(3.0F), 0), 1.0F)
      .build();
   public static final FoodProperties BLACK_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), ticksFromSecondsDuration(30.0F), 0), 1.0F)
      .build();
   public static final FoodProperties ROSE_HIP_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, ticksFromSecondsDuration(10.0F), 0), 1.0F)
      .build();
   public static final FoodProperties DANDELION_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance((MobEffect)ModEffects.COMFORT.get(), ticksFromMinutesDuration(3.0F), 0), 1.0F)
      .build();
   public static final FoodProperties PURULENT_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, ticksFromSecondsDuration(30.0F), 0), 1.0F)
      .build();
   public static final FoodProperties COFFEE = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), ticksFromMinutesDuration(5.0F), 1), 1.0F)
      .build();
   public static final FoodProperties LONG_GREEN_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, ticksFromMinutesDuration(4.5F), 0), 1.0F)
      .build();
   public static final FoodProperties LONG_YELLOW_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, ticksFromMinutesDuration(4.5F), 0), 1.0F)
      .build();
   public static final FoodProperties LONG_BLACK_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), ticksFromSecondsDuration(45.0F), 0), 1.0F)
      .build();
   public static final FoodProperties LONG_ROSE_HIP_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, ticksFromSecondsDuration(15.0F), 0), 1.0F)
      .build();
   public static final FoodProperties LONG_DANDELION_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance((MobEffect)ModEffects.COMFORT.get(), ticksFromMinutesDuration(4.5F), 0), 1.0F)
      .build();
   public static final FoodProperties LONG_PURULENT_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, ticksFromSecondsDuration(15.0F), 0), 1.0F)
      .build();
   public static final FoodProperties LONG_COFFEE = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), ticksFromMinutesDuration(10.0F), 0), 1.0F)
      .build();
   public static final FoodProperties LONG_APPLE_CIDER = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, ticksFromMinutesDuration(1.5F), 0), 1.0F)
      .build();
   public static final FoodProperties STRONG_GREEN_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, ticksFromMinutesDuration(1.5F), 1), 1.0F)
      .build();
   public static final FoodProperties STRONG_YELLOW_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, ticksFromMinutesDuration(1.5F), 1), 1.0F)
      .build();
   public static final FoodProperties STRONG_BLACK_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), ticksFromSecondsDuration(15.0F), 1), 1.0F)
      .build();
   public static final FoodProperties STRONG_ROSE_HIP_TEA = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, ticksFromSecondsDuration(10.0F), 1), 1.0F)
      .build();
   public static final FoodProperties STRONG_COFFEE = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), ticksFromMinutesDuration(2.5F), 2), 1.0F)
      .build();
   public static final FoodProperties STRONG_APPLE_CIDER = new Builder()
      .alwaysEat()
      .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, ticksFromSecondsDuration(30.0F), 1), 1.0F)
      .build();
   public static final FoodProperties ROSE_HIP_PIE_SLICE = new Builder()
      .nutrition(3)
      .saturationMod(0.3F)
      .fast()
      .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, ticksFromSecondsDuration(20.0F), 0, false, false), 1.0F)
      .build();
   public static final FoodProperties GREEN_TEA_COOKIE = new Builder()
      .nutrition(2)
      .saturationMod(0.1F)
      .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, ticksFromSecondsDuration(10.0F), 0), 1.0F)
      .build();
   public static final FoodProperties NETHER_WART_SOURDOUGH = new Builder()
      .nutrition(4)
      .saturationMod(0.2F)
      .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, ticksFromSecondsDuration(10.0F), 0), 0.8F)
      .build();
   public static final FoodProperties COFFEE_CAKE_SLICE = new Builder()
      .nutrition(2)
      .saturationMod(0.1F)
      .fast()
      .effect(() -> new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), ticksFromSecondsDuration(30.0F), 0, false, false), 1.0F)
      .build();
   public static final FoodProperties COFFEE_BERRIES = new Builder()
      .nutrition(2)
      .saturationMod(0.4F)
      .effect(() -> new MobEffectInstance(MobEffects.WITHER, ticksFromSecondsDuration(5.0F), 0), 0.8F)
      .effect(() -> new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), ticksFromSecondsDuration(10.0F), 0), 1.0F)
      .build();
   public static final FoodProperties BLACK_COD = new Builder()
      .nutrition(10)
      .saturationMod(0.9F)
      .effect(() -> new MobEffectInstance((MobEffect)ModEffects.NOURISHMENT.get(), ticksFromMinutesDuration(3.0F), 0), 1.0F)
      .effect(() -> new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), ticksFromSecondsDuration(30.0F), 0), 1.0F)
      .build();
   public static final FoodProperties TEA_CURRY = new Builder()
      .nutrition(10)
      .saturationMod(0.8F)
      .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, ticksFromSecondsDuration(30.0F), 0), 1.0F)
      .build();
   public static final FoodProperties BLAZING_CHILLI = new Builder()
      .nutrition(10)
      .saturationMod(0.4F)
      .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, ticksFromMinutesDuration(1.0F), 0), 1.0F)
      .build();

   public static int ticksFromSecondsDuration(float seconds) {
      return (int)Math.floor(seconds * 20.0F);
   }

   public static int ticksFromMinutesDuration(float minutes) {
      return (int)Math.floor(minutes * 1200.0F);
   }
}
