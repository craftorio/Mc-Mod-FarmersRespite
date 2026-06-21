package umpaz.farmersrespite.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Rectangle;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import umpaz.farmersrespite.common.block.entity.container.KettleMenu;
import vectorwing.farmersdelight.common.utility.TextUtils;

@ParametersAreNonnullByDefault
public class KettleScreen extends AbstractContainerScreen<KettleMenu> {
   private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("farmersrespite", "textures/gui/kettle.png");
   private static final Rectangle HEAT_ICON = new Rectangle(41, 55, 17, 15);
   private static final Rectangle PROGRESS_ARROW = new Rectangle(62, 25, 0, 17);

   public KettleScreen(KettleMenu screenContainer, Inventory inv, Component titleIn) {
      super(screenContainer, inv, titleIn);
      this.leftPos = 0;
      this.topPos = 0;
      this.imageWidth = 176;
      this.imageHeight = 166;
      this.titleLabelX = 28;
   }

   public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(gui);
      super.render(gui, mouseX, mouseY, partialTicks);
      this.renderTankTooltip(gui, mouseX, mouseY);
      this.renderTooltip(gui, mouseX, mouseY);
      this.renderHeatIndicatorTooltip(gui, mouseX, mouseY);
   }

   private void renderTankTooltip(GuiGraphics gui, int mouseX, int mouseY) {
      if (this.isHovering(106, 18, 37, 28, mouseX, mouseY) && !((KettleMenu)this.menu).kettleTank.isEmpty()) {
         Component component = MutableComponent.create(((KettleMenu)this.menu).kettleTank.getFluid().getDisplayName().getContents())
            .append(" (%s/%s mB)".formatted(((KettleMenu)this.menu).kettleTank.getFluidAmount(), ((KettleMenu)this.menu).kettleTank.getCapacity()));
         gui.renderTooltip(this.font, component, mouseX, mouseY);
      }
   }

   private void renderHeatIndicatorTooltip(GuiGraphics gui, int mouseX, int mouseY) {
      if (this.isHovering(HEAT_ICON.x, HEAT_ICON.y, HEAT_ICON.width, HEAT_ICON.height, mouseX, mouseY)) {
         String key = "container.cooking_pot." + (((KettleMenu)this.menu).isHeated() ? "heated" : "not_heated");
         Tooltip tooltip = Tooltip.create(TextUtils.getTranslation(key, new Object[]{this.menu}));
         this.setTooltipForNextRenderPass(tooltip, DefaultTooltipPositioner.INSTANCE, true);
      }
   }

   private void renderWaterBarIndicatorTooltip(GuiGraphics gui, int mouseX, int mouseY) {
      if (this.isHovering(35, 18, 3, 31, mouseX, mouseY)) {
         MutableComponent key = null;
         Tooltip tooltip = Tooltip.create(key);
         this.setTooltipForNextRenderPass(tooltip, DefaultTooltipPositioner.INSTANCE, true);
      }
   }

   protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
      super.renderLabels(gui, mouseX, mouseY);
      gui.drawString(this.font, this.playerInventoryTitle, 8, this.imageHeight - 96 + 2, 4210752, false);
   }

   protected void renderBg(GuiGraphics gui, float partialTicks, int mouseX, int mouseY) {
      if (this.minecraft != null) {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         gui.blit(BACKGROUND_TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
         if (((KettleMenu)this.menu).isHeated()) {
            gui.blit(BACKGROUND_TEXTURE, this.leftPos + HEAT_ICON.x, this.topPos + HEAT_ICON.y, 176, 0, HEAT_ICON.width, HEAT_ICON.height);
         }

         int l = ((KettleMenu)this.menu).getBrewProgressionScaled();
         gui.blit(BACKGROUND_TEXTURE, this.leftPos + PROGRESS_ARROW.x, this.topPos + PROGRESS_ARROW.y, 176, 15, l + 1, PROGRESS_ARROW.height);
         FluidStack fluidStack = ((KettleMenu)this.menu).kettleTank.getFluid();
         if (!fluidStack.isEmpty()) {
            IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
            ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(fluidStack);
            if (stillTexture != null) {
               TextureAtlasSprite sprite = (TextureAtlasSprite)this.minecraft.getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
               int tintColor = fluidTypeExtensions.getTintColor(fluidStack);
               float alpha = (tintColor >> 24 & 0xFF) / 255.0F;
               float red = (tintColor >> 16 & 0xFF) / 255.0F;
               float green = (tintColor >> 8 & 0xFF) / 255.0F;
               float blue = (tintColor & 0xFF) / 255.0F;
               float capacity = (float)((KettleMenu)this.menu).kettleTank.getFluidAmount() / ((KettleMenu)this.menu).kettleTank.getCapacity();
               if (capacity > 0.57) {
                  int y1 = this.topPos + 18 + (int)(12.0F * (1.0F - (capacity - 0.57F) / 0.43F));
                  int y2 = this.topPos + 18 + 12;
                  float topCapacity = (capacity - 0.57F) / 0.43F;
                  float vDistance = sprite.getV1() - sprite.getV0();
                  float v0 = sprite.getV0() + 0.25F * vDistance + 0.75F * vDistance * (1.0F - topCapacity);
                  gui.innerBlit(
                     sprite.atlasLocation(),
                     this.leftPos + 105,
                     this.leftPos + 105 + 16,
                     y1,
                     y2,
                     0,
                     sprite.getU0(),
                     sprite.getU1(),
                     v0,
                     sprite.getV1(),
                     red,
                     green,
                     blue,
                     alpha
                  );
                  gui.innerBlit(
                     sprite.atlasLocation(),
                     this.leftPos + 121,
                     this.leftPos + 121 + 16,
                     y1,
                     y2,
                     0,
                     sprite.getU0(),
                     sprite.getU0() + (sprite.getU1() - sprite.getU0()),
                     v0,
                     sprite.getV1(),
                     red,
                     green,
                     blue,
                     alpha
                  );
                  gui.innerBlit(
                     sprite.atlasLocation(),
                     this.leftPos + 137,
                     this.leftPos + 137 + 7,
                     y1,
                     y2,
                     0,
                     sprite.getU0(),
                     sprite.getU0() + 0.5F * (sprite.getU1() - sprite.getU0()),
                     v0,
                     sprite.getV1(),
                     red,
                     green,
                     blue,
                     alpha
                  );
               }

               int y1 = this.topPos + 30 + (int)(16.0F * (1.0F - Math.min(1.0F, capacity / 0.57F)));
               int y2 = this.topPos + 30 + 16;
               float vDistance = sprite.getV1() - sprite.getV0();
               float v0 = sprite.getV0() + vDistance * (1.0F - Math.min(1.0F, capacity / 0.57F));
               gui.innerBlit(
                  sprite.atlasLocation(),
                  this.leftPos + 105,
                  this.leftPos + 105 + 16,
                  y1,
                  y2,
                  0,
                  sprite.getU0(),
                  sprite.getU1(),
                  v0,
                  sprite.getV1(),
                  red,
                  green,
                  blue,
                  alpha
               );
               gui.innerBlit(
                  sprite.atlasLocation(),
                  this.leftPos + 121,
                  this.leftPos + 121 + 16,
                  y1,
                  y2,
                  0,
                  sprite.getU0(),
                  sprite.getU0() + (sprite.getU1() - sprite.getU0()),
                  v0,
                  sprite.getV1(),
                  red,
                  green,
                  blue,
                  alpha
               );
               gui.innerBlit(
                  sprite.atlasLocation(),
                  this.leftPos + 137,
                  this.leftPos + 137 + 7,
                  y1,
                  y2,
                  0,
                  sprite.getU0(),
                  sprite.getU0() + 0.5F * (sprite.getU1() - sprite.getU0()),
                  v0,
                  sprite.getV1(),
                  red,
                  green,
                  blue,
                  alpha
               );
               gui.blit(BACKGROUND_TEXTURE, this.leftPos + 105, this.topPos + 17, 176, 32, 40, 34);
            }
         }
      }
   }
}
