package com.farmersrespite.client;

import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRContainerTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class FRClientSetup {
    public static void init(FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.KETTLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.COFFEE_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.COFFEE_BUSH_TOP.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.COFFEE_STEM.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.COFFEE_STEM_DOUBLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.COFFEE_STEM_MIDDLE.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.SMALL_TEA_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.TEA_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.WILD_TEA_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.WITHER_ROOTS.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.WITHER_ROOTS_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.POTTED_COFFEE_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.POTTED_TEA_BUSH.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(FRBlocks.POTTED_WILD_TEA_BUSH.get(), RenderType.cutout());

        MenuScreens.register(FRContainerTypes.KETTLE.get(), com.farmersrespite.client.gui.KettleScreen::new);
    }
}
