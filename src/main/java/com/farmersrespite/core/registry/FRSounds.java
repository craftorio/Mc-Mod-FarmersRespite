package com.farmersrespite.core.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class FRSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "farmersrespite");

    public static final RegistryObject<SoundEvent> BLOCK_KETTLE_WHISTLE = SOUNDS.register("block.kettle.whistle", () -> new SoundEvent(new ResourceLocation("farmersrespite", "block.kettle.whistle")));
}
