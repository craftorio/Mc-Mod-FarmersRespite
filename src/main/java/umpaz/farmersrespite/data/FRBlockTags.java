package umpaz.farmersrespite.data;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import umpaz.farmersrespite.common.registry.FRBlocks;
import vectorwing.farmersdelight.common.tag.ModTags;

public class FRBlockTags extends IntrinsicHolderTagsProvider<Block> {
   public FRBlockTags(PackOutput output, CompletableFuture<Provider> registries, @Nullable ExistingFileHelper existingFileHelper) {
      super(
         output,
         Keys.BLOCKS,
         registries,
         attribute -> (ResourceKey)ForgeRegistries.BLOCKS.getResourceKey(attribute).get(),
         "farmersrespite",
         existingFileHelper
      );
   }

   protected void addTags(@Nonnull Provider registries) {
      this.registerMinecraftTags();
      this.registerBlockMineables();
   }

   protected void registerBlockMineables() {
      this.tag(BlockTags.MINEABLE_WITH_AXE)
         .add(
            new Block[]{
               (Block)FRBlocks.TEA_BUSH.get(),
               (Block)FRBlocks.WILD_TEA_BUSH.get(),
               (Block)FRBlocks.SMALL_TEA_BUSH.get(),
               (Block)FRBlocks.WILD_COFFEE_BUSH.get(),
               (Block)FRBlocks.COFFEE_BUSH.get(),
               (Block)FRBlocks.COFFEE_BUSH_TOP.get(),
               (Block)FRBlocks.COFFEE_STEM.get(),
               (Block)FRBlocks.COFFEE_STEM_DOUBLE.get(),
               (Block)FRBlocks.COFFEE_STEM_MIDDLE.get()
            }
         );
      this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add((Block)FRBlocks.KETTLE.get());
      this.tag(ModTags.MINEABLE_WITH_KNIFE)
         .add(
            new Block[]{
               (Block)FRBlocks.COFFEE_CAKE.get(),
               (Block)FRBlocks.ROSE_HIP_PIE.get(),
               (Block)FRBlocks.COFFEE_CAKE.get(),
               (Block)FRBlocks.CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.WHITE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.ORANGE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.MAGENTA_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.LIGHT_BLUE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.YELLOW_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.LIME_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.PINK_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.GRAY_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.LIGHT_GRAY_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.CYAN_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.PURPLE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.BLUE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.BROWN_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.GREEN_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.RED_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.BLACK_CANDLE_COFFEE_CAKE.get()
            }
         );
   }

   protected void registerMinecraftTags() {
      this.tag(BlockTags.CANDLE_CAKES)
         .add(
            new Block[]{
               (Block)FRBlocks.CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.WHITE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.ORANGE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.MAGENTA_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.LIGHT_BLUE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.YELLOW_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.LIME_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.PINK_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.GRAY_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.LIGHT_GRAY_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.CYAN_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.PURPLE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.BLUE_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.BROWN_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.GREEN_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.RED_CANDLE_COFFEE_CAKE.get(),
               (Block)FRBlocks.BLACK_CANDLE_COFFEE_CAKE.get()
            }
         );
      this.tag(BlockTags.FLOWER_POTS)
         .add(
            new Block[]{
               (Block)FRBlocks.POTTED_TEA_BUSH.get(),
               (Block)FRBlocks.POTTED_COFFEE_BUSH.get(),
               (Block)FRBlocks.POTTED_WILD_TEA_BUSH.get(),
               (Block)FRBlocks.POTTED_WILD_COFFEE_BUSH.get()
            }
         );
   }
}
