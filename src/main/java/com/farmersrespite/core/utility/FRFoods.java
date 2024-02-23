package com.farmersrespite.core.utility;

import com.farmersrespite.core.registry.FREffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class FRFoods {
    public static final FoodProperties GREEN_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1800, 0), 1.0F).build();
    public static final FoodProperties YELLOW_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800, 0), 1.0F).build();
    public static final FoodProperties BLACK_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 1800, 0), 1.0F).build();
    public static final FoodProperties ROSE_HIP_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 200, 0), 1.0F).build();
    public static final FoodProperties DANDELION_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 1800, 0), 1.0F).build();
    public static final FoodProperties PURULENT_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 600, 0), 1.0F).build();
    public static final FoodProperties COFFEE = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 6000, 1), 1.0F).build();

    public static final FoodProperties LONG_GREEN_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 3600, 0), 1.0F).build();
    public static final FoodProperties LONG_YELLOW_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 3600, 0), 1.0F).build();
    public static final FoodProperties LONG_BLACK_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 3600, 0), 1.0F).build();
    public static final FoodProperties LONG_ROSE_HIP_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 400, 0), 1.0F).build();
    public static final FoodProperties LONG_DANDELION_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(ModEffects.COMFORT.get(), 3600, 0), 1.0F).build();
    public static final FoodProperties LONG_COFFEE = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 12000, 0), 1.0F).build();
    public static final FoodProperties LONG_APPLE_CIDER = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 1200, 0), 1.0F).build();

    public static final FoodProperties STRONG_GREEN_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 1200, 1), 1.0F).build();
    public static final FoodProperties STRONG_YELLOW_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 1), 1.0F).build();
    public static final FoodProperties STRONG_BLACK_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 1200, 1), 1.0F).build();
    public static final FoodProperties STRONG_ROSE_HIP_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 1), 1.0F).build();
    public static final FoodProperties STRONG_PURULENT_TEA = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 300, 1), 1.0F).build();
    public static final FoodProperties STRONG_COFFEE = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 3000, 2), 1.0F).build();
    public static final FoodProperties STRONG_APPLE_CIDER = (new FoodProperties.Builder())
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 600, 1), 1.0F).build();


    public static final FoodProperties ROSE_HIP_PIE_SLICE = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.3F).fast()

            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 100, 0), 1.0F).build();
    public static final FoodProperties GREEN_TEA_COOKIE = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.1F)
            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 100, 0), 1.0F).build();
    public static final FoodProperties NETHER_WART_SOURDOUGH = (new FoodProperties.Builder())
            .nutrition(8).saturationMod(0.2F)
            .effect(() -> new MobEffectInstance(MobEffects.WEAKNESS, 200, 0), 0.8F).build();
    public static final FoodProperties COFFEE_CAKE_SLICE = (new FoodProperties.Builder())
            .nutrition(3).saturationMod(0.3F).fast()

            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 600, 0), 1.0F).build();
    public static final FoodProperties COFFEE_BERRIES = (new FoodProperties.Builder())
            .nutrition(2).saturationMod(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.WITHER, 100, 0), 0.8F)
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 200, 0), 1.0F).build();


    public static final FoodProperties BLACK_COD = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.9F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1.0F)
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 600, 0), 1.0F).build();
    public static final FoodProperties TEA_CURRY = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.8F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 4800, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 0), 1.0F).build();
    public static final FoodProperties BLAZING_CHILLI = (new FoodProperties.Builder())
            .nutrition(10).saturationMod(0.4F)
            .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 2400, 0), 1.0F)
            .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 0), 1.0F).build();
}
