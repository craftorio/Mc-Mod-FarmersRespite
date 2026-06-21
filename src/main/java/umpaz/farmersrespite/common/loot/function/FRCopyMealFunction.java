package umpaz.farmersrespite.common.loot.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import umpaz.farmersrespite.common.block.entity.KettleBlockEntity;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FRCopyMealFunction extends LootItemConditionalFunction {
   public static final ResourceLocation ID = new ResourceLocation("farmersrespite", "copy_meal");

   private FRCopyMealFunction(LootItemCondition[] conditions) {
      super(conditions);
   }

   public static Builder<?> builder() {
      return simpleBuilder(FRCopyMealFunction::new);
   }

   protected ItemStack run(ItemStack stack, LootContext context) {
      BlockEntity tile = (BlockEntity)context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
      if (tile instanceof KettleBlockEntity) {
         CompoundTag tag = ((KettleBlockEntity)tile).writeMeal(new CompoundTag());
         if (!tag.isEmpty()) {
            stack.addTagElement("BlockEntityTag", tag);
         }
      }

      return stack;
   }

   @Nullable
   public LootItemFunctionType getType() {
      return null;
   }

   public static class Serializer extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Serializer<FRCopyMealFunction> {
      public FRCopyMealFunction deserialize(JsonObject json, JsonDeserializationContext context, LootItemCondition[] conditions) {
         return new FRCopyMealFunction(conditions);
      }
   }
}
