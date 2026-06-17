package com.farmersrespite.core.registry;

import java.util.function.Supplier;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class FRRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "farmersrespite");

    public static final RegistryObject<RecipeSerializer<?>> BREWING = RECIPE_SERIALIZERS.register("brewing", com.farmersrespite.common.crafting.KettleRecipe.Serializer::new);
}
