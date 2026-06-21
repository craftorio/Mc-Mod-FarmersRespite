package umpaz.farmersrespite.common.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries.Keys;

public class FRTags {
   public static final TagKey<Item> TEA_LEAVES = modItemTag("tea_leaves");

   private static TagKey<Item> modItemTag(String path) {
      return TagKey.create(Keys.ITEMS, new ResourceLocation("farmersrespite:" + path));
   }

   private static TagKey<Block> modBlockTag(String path) {
      return TagKey.create(Keys.BLOCKS, new ResourceLocation("farmersrespite:" + path));
   }
}
