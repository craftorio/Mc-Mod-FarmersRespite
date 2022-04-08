package com.farmersrespite.client.event;

import com.farmersrespite.core.FarmersRespite;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FarmersRespite.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FRClientSetupEvents {
	
	public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOTTLE = new ResourceLocation(FarmersRespite.MODID, "item/empty_container_slot_bottle");

	@SubscribeEvent
	public static void onStitchEvent(TextureStitchEvent.Pre event) {
		event.addSprite(EMPTY_CONTAINER_SLOT_BOTTLE);
	}
}
