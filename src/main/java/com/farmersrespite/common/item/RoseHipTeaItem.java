package com.farmersrespite.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.PotionEvent;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class RoseHipTeaItem extends DrinkableItem {
	private final float healAmount;

	public RoseHipTeaItem(float healAmount, Properties properties) {
		super(properties, false, true);
		this.healAmount = healAmount;
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
		applyHealing(this.healAmount, worldIn, entityLiving);
		return super.finishUsingItem(stack, worldIn, entityLiving);
	}

	public static void applyHealing(float healAmount, LevelAccessor world, LivingEntity entity) {
		entity.heal(healAmount);

	}
}