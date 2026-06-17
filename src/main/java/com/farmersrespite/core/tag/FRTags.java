package com.farmersrespite.core.tag;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;


public class FRTags {
    public static final TagKey<Item> TEA_LEAVES = modItemTag("tea_leaves");

    private static TagKey<Item> modItemTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("farmersrespite:" + path));
    }
}
