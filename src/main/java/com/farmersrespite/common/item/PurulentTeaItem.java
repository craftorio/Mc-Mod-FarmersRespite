package com.farmersrespite.common.item;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class PurulentTeaItem extends DrinkableItem {
    private final int effectBoost;

    public PurulentTeaItem(int effectBoost, Item.Properties properties) {
        super(properties, true, true);
        this.effectBoost = effectBoost;
    }

    public void affectConsumer(ItemStack stack, Level worldIn, LivingEntity consumer) {
        Iterator<MobEffectInstance> itr = consumer.getActiveEffects().iterator();
        ArrayList<MobEffect> compatibleEffects = new ArrayList<>();
        while (itr.hasNext()) {
            MobEffectInstance effect = itr.next();
            if (effect.isCurativeItem(new ItemStack(Items.MILK_BUCKET))) {
                compatibleEffects.add(effect.getEffect());
            }
        }

        if (compatibleEffects.size() > 0) {
            MobEffectInstance selectedEffect = consumer.getEffect(compatibleEffects.get(worldIn.random.nextInt(compatibleEffects.size())));
            if (selectedEffect != null && !MinecraftForge.EVENT_BUS.post(new PotionEvent.PotionRemoveEvent(consumer, selectedEffect)))
                consumer.addEffect(new MobEffectInstance(selectedEffect.getEffect(), selectedEffect.getDuration() + this.effectBoost, selectedEffect.getAmplifier(), selectedEffect.isAmbient(), selectedEffect.isVisible(), selectedEffect.showIcon()));
        }
    }
}
