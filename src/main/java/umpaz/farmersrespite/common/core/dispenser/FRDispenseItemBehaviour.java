package umpaz.farmersrespite.common.core.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import umpaz.farmersrespite.common.registry.FRBlocks;

@EventBusSubscriber(modid = "farmersrespite", bus = Bus.MOD)
public class FRDispenseItemBehaviour {
   private static void replacePotionBehaviour() {
      DispenseItemBehavior newPotionBehaviour = new OptionalDispenseItemBehavior() {
         private final DispenseItemBehavior defaultPotionBehaviour = (DispenseItemBehavior)DispenserBlock.DISPENSER_REGISTRY.get(Items.POTION);

         public ItemStack execute(BlockSource source, ItemStack stack) {
            ServerLevel level = source.getLevel();
            BlockPos pos = source.getPos();
            BlockPos facingPos = source.getPos().relative((Direction)source.getBlockState().getValue(DispenserBlock.FACING));
            BlockState facingState = level.getBlockState(facingPos);
            if (facingState.is((Block)FRBlocks.KETTLE.get()) && PotionUtils.getPotion(stack) == Potions.WATER) {
               this.setSuccess(true);
               this.playSound(source);
               level.playSound((Player)null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
               this.playAnimation(source, (Direction)source.getBlockState().getValue(DispenserBlock.FACING));
               return new ItemStack(Items.GLASS_BOTTLE);
            } else {
               this.setSuccess(true);
               return this.defaultPotionBehaviour.dispense(source, stack);
            }
         }
      };
      DispenserBlock.registerBehavior(Items.POTION, newPotionBehaviour);
   }

   private static void replaceWaterBucketBehaviour() {
      DispenseItemBehavior newWaterBucketBehaviour = new OptionalDispenseItemBehavior() {
         private final DispenseItemBehavior defaultBucketBehaviour = (DispenseItemBehavior)DispenserBlock.DISPENSER_REGISTRY.get(Items.WATER_BUCKET);

         public ItemStack execute(BlockSource source, ItemStack stack) {
            ServerLevel level = source.getLevel();
            BlockPos pos = source.getPos();
            BlockPos facingPos = source.getPos().relative((Direction)source.getBlockState().getValue(DispenserBlock.FACING));
            BlockState facingState = level.getBlockState(facingPos);
            if (!facingState.is((Block)FRBlocks.KETTLE.get())) {
               this.setSuccess(true);
               return this.defaultBucketBehaviour.dispense(source, stack);
            } else {
               this.setSuccess(true);
               this.playSound(source);
               level.playSound((Player)null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
               this.playAnimation(source, (Direction)source.getBlockState().getValue(DispenserBlock.FACING));
               return new ItemStack(Items.BUCKET);
            }
         }
      };
      DispenserBlock.registerBehavior(Items.WATER_BUCKET, newWaterBucketBehaviour);
   }

   private static void replaceDispenseItemBehaviours() {
      replacePotionBehaviour();
      replaceWaterBucketBehaviour();
   }

   @SubscribeEvent
   public static void replaceDispenseItemBehaviours(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> replaceDispenseItemBehaviours());
   }
}
