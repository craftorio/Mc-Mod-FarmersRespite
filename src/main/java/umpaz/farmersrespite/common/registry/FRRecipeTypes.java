package umpaz.farmersrespite.common.registry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.crafting.KettlePouringRecipe;
import umpaz.farmersrespite.common.crafting.KettleRecipe;

public class FRRecipeTypes {
   public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "farmersrespite");
   public static final RegistryObject<RecipeType<KettleRecipe>> BREWING = RECIPE_TYPES.register("brewing", () -> registerRecipeType("brewing"));
   public static final RegistryObject<RecipeType<KettlePouringRecipe>> KETTLE_POURING = RECIPE_TYPES.register(
      "kettle_pouring", () -> registerRecipeType("kettle_pouring")
   );

   public static <T extends Recipe<?>> RecipeType<T> registerRecipeType(final String identifier) {
      return new RecipeType<T>() {
         @Override
         public String toString() {
            return "farmersrespite:" + identifier;
         }
      };
   }
}
