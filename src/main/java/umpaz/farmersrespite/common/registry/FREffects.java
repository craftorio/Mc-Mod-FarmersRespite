package umpaz.farmersrespite.common.registry;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.effect.CaffeinatedEffect;

public class FREffects {
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "farmersrespite");
   public static final RegistryObject<MobEffect> CAFFEINATED = EFFECTS.register("caffeinated", CaffeinatedEffect::new);
}
