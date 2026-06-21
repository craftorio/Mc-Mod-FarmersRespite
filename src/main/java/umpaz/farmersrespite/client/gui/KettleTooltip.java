package umpaz.farmersrespite.client.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;
import org.joml.Matrix4f;
import vectorwing.farmersdelight.common.utility.TextUtils;

public class KettleTooltip implements ClientTooltipComponent {
   private static final int ITEM_SIZE = 16;
   private static final int MARGIN = 4;
   private final int textSpacing = 9 + 1;
   private final FluidStack mealStack;

   public KettleTooltip(KettleTooltip.KettleTooltipComponent tooltip) {
      this.mealStack = tooltip.mealStack;
   }

   public int getHeight() {
      return this.mealStack.isEmpty() ? this.textSpacing : this.textSpacing + 16;
   }

   public int getWidth(Font font) {
      if (!this.mealStack.isEmpty()) {
         MutableComponent textServingsOf = this.mealStack.getAmount() == 250
            ? TextUtils.getTranslation("tooltip.cooking_pot.single_serving", new Object[0])
            : TextUtils.getTranslation("tooltip.cooking_pot.many_servings", new Object[]{this.mealStack.getAmount() / 250});
         return Math.max(font.width(textServingsOf), font.width(this.mealStack.getDisplayName()) + 20);
      } else {
         return font.width(TextUtils.getTranslation("tooltip.cooking_pot.empty", new Object[0]));
      }
   }

   public void renderImage(Font font, int mouseX, int mouseY, GuiGraphics gui) {
      if (!this.mealStack.isEmpty()) {
         IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(this.mealStack.getFluid());
         ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture(this.mealStack);
         if (stillTexture != null) {
            TextureAtlasSprite sprite = (TextureAtlasSprite)Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
            int tintColor = fluidTypeExtensions.getTintColor(this.mealStack);
            float alpha = (tintColor >> 24 & 0xFF) / 255.0F;
            float red = (tintColor >> 16 & 0xFF) / 255.0F;
            float green = (tintColor >> 8 & 0xFF) / 255.0F;
            float blue = (tintColor & 0xFF) / 255.0F;
            gui.blit(mouseX, mouseY + 9, 0, 16, 16, sprite, red, green, blue, alpha);
         }
      }
   }

   public void renderText(Font font, int x, int y, Matrix4f matrix4f, BufferSource bufferSource) {
      Integer color = ChatFormatting.GRAY.getColor();
      int gray = color == null ? -1 : color;
      if (!this.mealStack.isEmpty()) {
         MutableComponent textServingsOf = this.mealStack.getAmount() == 250
            ? TextUtils.getTranslation("tooltip.cooking_pot.single_serving", new Object[0])
            : TextUtils.getTranslation("tooltip.cooking_pot.many_servings", new Object[]{this.mealStack.getAmount() / 250});
         font.drawInBatch(textServingsOf, x, y, gray, true, matrix4f, bufferSource, DisplayMode.NORMAL, 0, 15728880);
         font.drawInBatch(
            this.mealStack.getDisplayName(), x + 16 + 4, y + this.textSpacing + 4, -1, true, matrix4f, bufferSource, DisplayMode.NORMAL, 0, 15728880
         );
      } else {
         MutableComponent textEmpty = TextUtils.getTranslation("tooltip.cooking_pot.empty", new Object[0]);
         font.drawInBatch(textEmpty, x, y, gray, true, matrix4f, bufferSource, DisplayMode.NORMAL, 0, 15728880);
      }
   }

   public record KettleTooltipComponent(FluidStack mealStack) implements TooltipComponent {
   }
}
