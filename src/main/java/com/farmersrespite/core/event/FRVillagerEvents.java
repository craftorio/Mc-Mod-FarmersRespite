package com.farmersrespite.core.event;

import java.util.List;
import java.util.Random;

import javax.annotation.ParametersAreNonnullByDefault;

import com.farmersrespite.core.FarmersRespite;
import com.farmersrespite.core.registry.FRItems;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import vectorwing.farmersdelight.common.Configuration;

@Mod.EventBusSubscriber(modid = FarmersRespite.MODID)
@ParametersAreNonnullByDefault
public class FRVillagerEvents
{
	@SubscribeEvent
	public static void onVillagerTrades(VillagerTradesEvent event) {
		if (!Configuration.FARMERS_BUY_FD_CROPS.get()) return;

		Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
		VillagerProfession profession = event.getType();
		if (profession.getRegistryName() == null) return;
		if (profession.getRegistryName().getPath().equals("farmer")) {
			trades.get(3).add(new EmeraldForItemsTrade(FRItems.GREEN_TEA_LEAVES.get(), 12, 16, 20));
			trades.get(3).add(new EmeraldForItemsTrade(FRItems.YELLOW_TEA_LEAVES.get(), 15, 16, 20));
			trades.get(3).add(new EmeraldForItemsTrade(FRItems.BLACK_TEA_LEAVES.get(), 19, 16, 20));
			trades.get(3).add(new EmeraldForItemsTrade(FRItems.BLACK_TEA_LEAVES.get(), 19, 16, 20));
			
			trades.get(3).add(new ItemsForEmeraldsTrade(FRItems.TEA_SEEDS.get(), 1, 4, 16, 10));
			trades.get(4).add(new ItemsForEmeraldsTrade(FRItems.COFFEE_BERRIES.get(), 5, 2, 16, 15));
			trades.get(4).add(new ItemsForEmeraldsTrade(FRItems.GREEN_TEA_COOKIE.get(), 3, 12, 12, 15));
			trades.get(5).add(new ItemsForEmeraldsTrade(FRItems.COFFEE_CAKE.get(), 13, 1, 12, 30));
		}
	} 

	static class EmeraldForItemsTrade implements VillagerTrades.ItemListing
	{
		private final Item tradeItem;
		private final int count;
		private final int maxUses;
		private final int xpValue;
		private final float priceMultiplier;

		public EmeraldForItemsTrade(ItemLike tradeItemIn, int countIn, int maxUsesIn, int xpValueIn) {
			this.tradeItem = tradeItemIn.asItem();
			this.count = countIn;
			this.maxUses = maxUsesIn;
			this.xpValue = xpValueIn;
			this.priceMultiplier = 0.05F;
		}

		@Override
		public MerchantOffer getOffer(Entity trader, Random rand) {
			ItemStack itemstack = new ItemStack(this.tradeItem, this.count);
			return new MerchantOffer(itemstack, new ItemStack(Items.EMERALD), this.maxUses, this.xpValue, this.priceMultiplier);
		}
	}
	
	static class ItemsForEmeraldsTrade implements VillagerTrades.ItemListing
	{
		private final Item buyItem;
		private final int emeraldCost;
		private final int count;
		private final int maxUses;
		private final int xpValue;
		private final float priceMultiplier;

		public ItemsForEmeraldsTrade(ItemLike buyItemIn, int emeraldCostIn, int countIn, int maxUsesIn, int xpValueIn) {
			this.buyItem = buyItemIn.asItem();
			this.emeraldCost = emeraldCostIn;
			this.count = countIn;
			this.maxUses = maxUsesIn;
			this.xpValue = xpValueIn;
			this.priceMultiplier = 0.05F;
		}

		@Override
		public MerchantOffer getOffer(Entity trader, Random rand) {
			ItemStack emerald = new ItemStack(Items.EMERALD, this.emeraldCost);
			ItemStack purchase = new ItemStack(this.buyItem, this.count);
			return new MerchantOffer(emerald, purchase, this.maxUses, this.xpValue, this.priceMultiplier);
		}
	}
}
