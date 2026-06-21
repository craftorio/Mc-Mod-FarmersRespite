package umpaz.farmersrespite.common;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class FRConfiguration {
   public static ForgeConfigSpec COMMON_CONFIG;
   public static final String CATEGORY_SETTINGS = "settings";
   public static BooleanValue BONE_MEAL_TEA;
   public static BooleanValue BONE_MEAL_COFFEE;

   static {
      Builder COMMON_BUILDER = new Builder();
      COMMON_BUILDER.comment("Game settings").push("settings");
      BONE_MEAL_TEA = COMMON_BUILDER.comment("Are tea bushes bonemealable?").define("enableBoneMealTeaBush", false);
      BONE_MEAL_COFFEE = COMMON_BUILDER.comment("Are coffee bushes bonemealable?").define("enableBoneMealCoffeeBush", false);
      COMMON_CONFIG = COMMON_BUILDER.build();
   }
}
