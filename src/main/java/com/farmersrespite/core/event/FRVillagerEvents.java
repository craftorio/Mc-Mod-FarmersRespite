package com.farmersrespite.core.event;

import com.farmersrespite.core.registry.FRItems;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import vectorwing.farmersdelight.common.Configuration;


@EventBusSubscriber(modid = "farmersrespite")
@ParametersAreNonnullByDefault
public class FRVillagerEvents {
    @SubscribeEvent
    public static void onWandererTrades(WandererTradesEvent event) {
        if (Configuration.FARMERS_BUY_FD_CROPS.get().booleanValue()) {
            List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
            trades.add(emeraldForItemsTrade(FRItems.GREEN_TEA_LEAVES.get(), 7, 16, 20));
            trades.add(emeraldForItemsTrade(FRItems.YELLOW_TEA_LEAVES.get(), 9, 16, 20));
            trades.add(emeraldForItemsTrade(FRItems.BLACK_TEA_LEAVES.get(), 13, 16, 20));

            trades.add(itemForEmeraldTrade(FRItems.TEA_SEEDS.get(), 1, 4, 16, 10));
            trades.add(itemForEmeraldTrade(FRItems.COFFEE_BERRIES.get(), 5, 2, 16, 15));
            trades.add(itemForEmeraldTrade(FRItems.GREEN_TEA_COOKIE.get(), 3, 12, 12, 15));
            trades.add(itemForEmeraldTrade(FRItems.COFFEE_CAKE.get(), 13, 1, 12, 30));
        }
    }


    public static BasicItemListing emeraldForItemsTrade(ItemLike item, int count, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, count), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }

    public static BasicItemListing itemForEmeraldTrade(ItemLike item, int price, int count, int maxTrades, int xp) {
        return new BasicItemListing(price, new ItemStack(item, count), maxTrades, xp, 0.05F);
    }
}
