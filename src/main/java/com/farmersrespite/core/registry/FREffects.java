package com.farmersrespite.core.registry;

import java.util.function.Supplier;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class FREffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "farmersrespite");

    public static final RegistryObject<MobEffect> CAFFEINATED = EFFECTS.register("caffeinated", com.farmersrespite.common.effect.CaffeinatedEffect::new);
}
