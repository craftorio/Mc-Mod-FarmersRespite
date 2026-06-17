package com.farmersrespite.client.gui;

import com.farmersrespite.common.block.entity.container.KettleContainer;
import com.farmersrespite.core.utility.FRTextUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;


@ParametersAreNonnullByDefault
public class KettleScreen
        extends AbstractContainerScreen<KettleContainer> {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("farmersrespite", "textures/gui/kettle.png");
    private static final Rectangle HEAT_ICON = new Rectangle(41, 55, 17, 15);
    private static final Rectangle PROGRESS_ARROW = new Rectangle(62, 25, 0, 17);
    private static final Rectangle WATER_BAR1 = new Rectangle(34, 38, 5, 11);
    private static final Rectangle WATER_BAR2 = new Rectangle(34, 28, 5, 10);
    private static final Rectangle WATER_BAR3 = new Rectangle(34, 17, 5, 12);

    public KettleScreen(KettleContainer screenContainer, Inventory inv, Component titleIn) {
        super(screenContainer, inv, titleIn);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.titleLabelX = 35;
    }


    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        renderMealDisplayTooltip(ms, mouseX, mouseY);
        renderHeatIndicatorTooltip(ms, mouseX, mouseY);
        renderWaterBarIndicatorTooltip(ms, mouseX, mouseY);
    }

    private void renderHeatIndicatorTooltip(PoseStack ms, int mouseX, int mouseY) {
        if (isHovering(HEAT_ICON.x, HEAT_ICON.y, HEAT_ICON.width, HEAT_ICON.height, mouseX, mouseY)) {
            List<Component> tooltip = new ArrayList<>();
            String key = "container.kettle." + (this.menu.isHeated() ? "heated" : "not_heated");
            tooltip.add(FRTextUtils.getTranslation(key, this.menu));
            renderComponentTooltip(ms, tooltip, mouseX, mouseY);
        }
    }

    private void renderWaterBarIndicatorTooltip(PoseStack ms, int mouseX, int mouseY) {
        if (isHovering(34, 17, 5, 32, mouseX, mouseY)) {
            List<Component> tooltip = new ArrayList<>();
            MutableComponent key = null;
            if (this.menu.waterLevel() == 0) {
                key = FRTextUtils.getTranslation("container.kettle.no_water");
            }
            if (this.menu.waterLevel() == 1) {
                key = FRTextUtils.getTranslation("container.kettle.has_single_water");
            }
            if (this.menu.waterLevel() > 1) {
                key = FRTextUtils.getTranslation("container.kettle.has_many_water", this.menu.waterLevel());
            }
            tooltip.add(key);
            renderComponentTooltip(ms, tooltip, mouseX, mouseY);
        }
    }

    protected void renderMealDisplayTooltip(PoseStack ms, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            if (this.hoveredSlot.index == 2) {
                List<Component> tooltip = new ArrayList<>();
                ItemStack mealStack = this.hoveredSlot.getItem();
                tooltip.add(((MutableComponent) mealStack.getItem().getDescription()).withStyle((mealStack.getRarity()).color));
                ItemStack containerStack = this.menu.tileEntity.getContainer();
                String container = !containerStack.isEmpty() ? containerStack.getItem().getDescription().getString() : "";
                tooltip.add(FRTextUtils.getTranslation("container.kettle.served_on", container).withStyle(ChatFormatting.GRAY));
                renderComponentTooltip(ms, tooltip, mouseX, mouseY);
            } else {
                renderTooltip(ms, this.hoveredSlot.getItem(), mouseX, mouseY);
            }
        }
    }


    protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {
        super.renderLabels(ms, mouseX, mouseY);
        this.font.draw(ms, this.playerInventoryTitle, 8.0F, (this.imageHeight - 96 + 2), 4210752);
    }


    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.minecraft == null) {
            return;
        }
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);


        if (this.menu.isHeated()) {
            blit(ms, this.leftPos + HEAT_ICON.x, this.topPos + HEAT_ICON.y, 176, 0, HEAT_ICON.width, HEAT_ICON.height);
        }


        int l = this.menu.getBrewProgressionScaled();
        blit(ms, this.leftPos + PROGRESS_ARROW.x, this.topPos + PROGRESS_ARROW.y, 176, 15, l + 1, PROGRESS_ARROW.height);


        if (this.menu.waterLevel() == 1) {
            blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 53, WATER_BAR1.width, WATER_BAR1.height);
        }
        if (this.menu.waterLevel() == 2) {
            blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 53, WATER_BAR1.width, WATER_BAR1.height);
            blit(ms, this.leftPos + WATER_BAR2.x, this.topPos + WATER_BAR2.y, 176, 43, WATER_BAR2.width, WATER_BAR2.height);
        }
        if (this.menu.waterLevel() == 3) {
            blit(ms, this.leftPos + WATER_BAR1.x, this.topPos + WATER_BAR1.y, 176, 53, WATER_BAR1.width, WATER_BAR1.height);
            blit(ms, this.leftPos + WATER_BAR2.x, this.topPos + WATER_BAR2.y, 176, 43, WATER_BAR2.width, WATER_BAR2.height);
            blit(ms, this.leftPos + WATER_BAR3.x, this.topPos + WATER_BAR3.y, 176, 32, WATER_BAR3.width, WATER_BAR3.height);
        }
    }
}
