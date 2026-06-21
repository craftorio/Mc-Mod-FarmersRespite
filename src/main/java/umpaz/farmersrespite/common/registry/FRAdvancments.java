package umpaz.farmersrespite.common.registry;

import net.minecraft.advancements.CriteriaTriggers;
import umpaz.farmersrespite.common.advancement.StuntTeaTrigger;

public class FRAdvancments {
   public static StuntTeaTrigger STUNT_TEA_BUSH = new StuntTeaTrigger();

   public static void register() {
      CriteriaTriggers.register(STUNT_TEA_BUSH);
   }
}
