package umpaz.farmersrespite.common.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class StuntTeaTrigger extends SimpleCriterionTrigger<StuntTeaTrigger.TriggerInstance> {
   private static final ResourceLocation ID = new ResourceLocation("farmersrespite", "stunt_tea_bush");

   public ResourceLocation getId() {
      return ID;
   }

   public void trigger(ServerPlayer player) {
      this.trigger(player, StuntTeaTrigger.TriggerInstance::test);
   }

   protected StuntTeaTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext conditionsParser) {
      return new StuntTeaTrigger.TriggerInstance(player);
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      public TriggerInstance(ContextAwarePredicate player) {
         super(StuntTeaTrigger.ID, player);
      }

      public static StuntTeaTrigger.TriggerInstance simple() {
         return new StuntTeaTrigger.TriggerInstance(ContextAwarePredicate.ANY);
      }

      public boolean test() {
         return true;
      }
   }
}
