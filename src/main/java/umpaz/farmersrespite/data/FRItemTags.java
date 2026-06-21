package umpaz.farmersrespite.data;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.common.tag.FRTags;

public class FRItemTags extends ItemTagsProvider {
   public FRItemTags(
      PackOutput packOutput,
      CompletableFuture<Provider> registries,
      CompletableFuture<TagLookup<Block>> blockTagsProvider,
      @Nullable ExistingFileHelper fileHelper
   ) {
      super(packOutput, registries, blockTagsProvider, "farmersrespite", fileHelper);
   }

   protected void addTags(@Nonnull Provider registries) {
      this.registerModTags();
   }

   private void registerModTags() {
      this.tag(FRTags.TEA_LEAVES)
         .add(new Item[]{(Item)FRItems.GREEN_TEA_LEAVES.get(), (Item)FRItems.YELLOW_TEA_LEAVES.get(), (Item)FRItems.BLACK_TEA_LEAVES.get()});
   }
}
