package umpaz.farmersrespite.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.loot.function.FRCopyMealFunction;

public class FRLootFunctions {
   public static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTIONS = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, "farmersrespite");
   public static final RegistryObject<LootItemFunctionType> COPY_MEAL = LOOT_FUNCTIONS.register(
      "copy_meal", () -> new LootItemFunctionType(new FRCopyMealFunction.Serializer())
   );
}
