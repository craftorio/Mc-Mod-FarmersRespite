package com.farmersrespite.core.registry;

import com.farmersrespite.common.effect.CaffeinatedEffect;
import com.farmersrespite.core.FarmersRespite;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FREffects
{
	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, FarmersRespite.MODID);

	public static final RegistryObject<MobEffect> CAFFEINATED = EFFECTS.register("caffeinated", CaffeinatedEffect::new);

}
