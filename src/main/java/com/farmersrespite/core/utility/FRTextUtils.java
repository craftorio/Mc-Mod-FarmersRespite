package com.farmersrespite.core.utility;

import com.farmersrespite.core.FarmersRespite;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import java.util.List;
import java.util.Map;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


public class FRTextUtils {
    private static final MutableComponent NO_EFFECTS = (new TranslatableComponent("effect.none")).withStyle(ChatFormatting.GRAY);

	/**
	 * Syntactic sugar for custom translation keys. Always prefixed with the mod's ID in lang files (e.g. farmersdelight.your.key.here).
	 */
	public static MutableComponent getTranslation(String key, Object... args) {
		return new TranslatableComponent(FarmersRespite.MODID + "." + key, args);
	}


    @OnlyIn(Dist.CLIENT)
    public static void addFoodEffectTooltip(ItemStack itemIn, List<Component> lores, float durationFactor) {
        FoodProperties foodStats = itemIn.getItem().getFoodProperties();
        if (foodStats == null) {
            return;
        }
        List<Pair<MobEffectInstance, Float>> effectList = foodStats.getEffects();
        List<Pair<Attribute, AttributeModifier>> attributeList = Lists.newArrayList();
        if (effectList.isEmpty()) {
            lores.add(NO_EFFECTS);
        } else {
            for (Pair<MobEffectInstance, Float> effectPair : effectList) {
                MobEffectInstance instance = effectPair.getFirst();
                TranslatableComponent translatableComponent = new TranslatableComponent(instance.getDescriptionId());
                MobEffect effect = instance.getEffect();
                Map<Attribute, AttributeModifier> attributeMap = effect.getAttributeModifiers();
                if (!attributeMap.isEmpty()) {
                    for (Map.Entry<Attribute, AttributeModifier> entry : attributeMap.entrySet()) {
                        AttributeModifier rawModifier = entry.getValue();
                        AttributeModifier modifier = new AttributeModifier(rawModifier.getName(), effect.getAttributeModifierValue(instance.getAmplifier(), rawModifier), rawModifier.getOperation());
                        attributeList.add(new Pair(entry.getKey(), modifier));
                    }
                }

                if (instance.getAmplifier() > 0) {
                    translatableComponent = new TranslatableComponent("potion.withAmplifier", translatableComponent, new TranslatableComponent("potion.potency." + instance.getAmplifier()));
                }

                if (instance.getDuration() > 20) {
                    translatableComponent = new TranslatableComponent("potion.withDuration", translatableComponent, MobEffectUtil.formatDuration(instance, durationFactor));
                }

                lores.add(translatableComponent.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }

        if (!attributeList.isEmpty()) {
            lores.add(TextComponent.EMPTY);
            lores.add((new TranslatableComponent("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

            for (Pair<Attribute, AttributeModifier> pair : attributeList) {
                double formattedAmount;
                AttributeModifier modifier = pair.getSecond();
                double amount = modifier.getAmount();

                if (modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    formattedAmount = modifier.getAmount();
                } else {
                    formattedAmount = modifier.getAmount() * 100.0D;
                }

                if (amount > 0.0D) {
                    lores.add((new TranslatableComponent("attribute.modifier.plus." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), new TranslatableComponent(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
                    continue;
                }
                if (amount < 0.0D) {
                    formattedAmount *= -1.0D;
                    lores.add((new TranslatableComponent("attribute.modifier.take." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(formattedAmount), new TranslatableComponent(pair.getFirst().getDescriptionId()))).withStyle(ChatFormatting.RED));
                }
            }
        }
    }
}
