package com.farmersrespite.core.registry;

import com.farmersrespite.common.crafting.KettleRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;


public class FRRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE.key(), "farmersrespite");

    public static final RegistryObject<RecipeType<KettleRecipe>> BREWING = RECIPE_TYPES.register("brewing", () -> registerRecipeType("brewing"));

    public static <T extends net.minecraft.world.item.crafting.Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
        return new RecipeType<T>() {
            public String toString() {
                return "farmersrespite:" + identifier;
            }
        };
    }
}
