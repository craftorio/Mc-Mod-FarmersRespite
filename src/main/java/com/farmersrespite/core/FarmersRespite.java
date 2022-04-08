package com.farmersrespite.core;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.farmersrespite.client.FRClientSetup;
import com.farmersrespite.common.crafting.KettleRecipe;
import com.farmersrespite.core.event.FRCommonSetup;
import com.farmersrespite.core.registry.FRBiomeFeatures;
import com.farmersrespite.core.registry.FRBlockEntityTypes;
import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRContainerTypes;
import com.farmersrespite.core.registry.FREffects;
import com.farmersrespite.core.registry.FRItems;
import com.farmersrespite.core.registry.FRRecipeSerializers;
import com.farmersrespite.core.registry.FRSounds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FarmersRespite.MODID)
@Mod.EventBusSubscriber(modid = FarmersRespite.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FarmersRespite
{
	public static final String MODID = "farmersrespite";
	public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(FarmersRespite.MODID)
	{
		@Nonnull
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(FRBlocks.KETTLE.get());
		}
	};

	public static final Logger LOGGER = LogManager.getLogger();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public FarmersRespite() {
		final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

		modEventBus.addListener(FRCommonSetup::init);
		modEventBus.addListener(FRClientSetup::init);
		modEventBus.addGenericListener(RecipeSerializer.class, this::registerRecipeSerializers);

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FRConfiguration.COMMON_CONFIG);

		FRItems.ITEMS.register(modEventBus);
		FRBlocks.BLOCKS.register(modEventBus);
		FREffects.EFFECTS.register(modEventBus);
		FRBiomeFeatures.FEATURES.register(modEventBus);
		FRSounds.SOUNDS.register(modEventBus);
		FRBlockEntityTypes.TILES.register(modEventBus);
		FRContainerTypes.CONTAINER_TYPES.register(modEventBus);
		FRRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
		event.getRegistry().register(KettleRecipe.SERIALIZER);
	}
}
