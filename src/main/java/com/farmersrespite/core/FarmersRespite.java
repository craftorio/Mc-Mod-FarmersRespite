package com.farmersrespite.core;

import com.farmersrespite.client.FRClientSetup;
import com.farmersrespite.core.event.FRCommonSetup;
import com.farmersrespite.core.registry.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.annotation.Nonnull;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.IConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("farmersrespite")
@Mod.EventBusSubscriber(modid = "farmersrespite", bus = Mod.EventBusSubscriber.Bus.MOD)
public class FarmersRespite {
    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab("farmersrespite") {
        @Nonnull
        public ItemStack makeIcon() {
            return new ItemStack(FRBlocks.KETTLE.get());
        }
    };
    public static final String MODID = "farmersrespite";
    public static final Logger LOGGER = LogManager.getLogger();
    public static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public FarmersRespite() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(FRCommonSetup::init);
        modEventBus.addListener(FRClientSetup::init);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, FRConfiguration.COMMON_CONFIG);

        FRItems.ITEMS.register(modEventBus);
        FRBlocks.BLOCKS.register(modEventBus);
        FREffects.EFFECTS.register(modEventBus);
        FRBiomeFeatures.FEATURES.register(modEventBus);
        FRSounds.SOUNDS.register(modEventBus);
        FRBlockEntityTypes.TILES.register(modEventBus);
        FRContainerTypes.CONTAINER_TYPES.register(modEventBus);
        FRRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        FRRecipeTypes.RECIPE_TYPES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
