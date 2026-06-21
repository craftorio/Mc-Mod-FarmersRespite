package umpaz.farmersrespite.common.registry;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.crafting.KettlePouringRecipe;
import umpaz.farmersrespite.common.crafting.KettleRecipe;

public class FRRecipeSerializers {
   public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "farmersrespite");
   public static final RegistryObject<RecipeSerializer<?>> BREWING = RECIPE_SERIALIZERS.register("brewing", KettleRecipe.Serializer::new);
   public static final RegistryObject<RecipeSerializer<?>> KETTLE_POURING = RECIPE_SERIALIZERS.register("kettle_pouring", KettlePouringRecipe.Serializer::new);
}
