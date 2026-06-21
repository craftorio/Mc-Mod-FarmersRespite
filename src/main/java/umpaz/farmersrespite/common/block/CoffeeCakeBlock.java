package umpaz.farmersrespite.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import umpaz.farmersrespite.common.registry.FREffects;
import umpaz.farmersrespite.common.registry.FRItems;
import vectorwing.farmersdelight.common.tag.ModTags;

public class CoffeeCakeBlock extends CakeBlock {
   public CoffeeCakeBlock(Properties properties) {
      super(properties);
   }

   public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
      ItemStack stack = player.getItemInHand(handIn);
      Item item = stack.getItem();
      if (stack.is(ItemTags.CANDLES) && (Integer)state.getValue(BITES) == 0) {
         Block block = Block.byItem(item);
         if (block instanceof CandleBlock) {
            if (!player.isCreative()) {
               stack.shrink(1);
            }

            worldIn.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
            worldIn.setBlockAndUpdate(pos, CandleCoffeeCakeBlock.byCandle(block, this));
            worldIn.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
            player.awardStat(Stats.ITEM_USED.get(item));
            return InteractionResult.SUCCESS;
         }
      }

      if (worldIn.isClientSide) {
         if (stack.is(ModTags.KNIVES)) {
            return this.cutSlice(worldIn, pos, state, player);
         }

         if (this.eatSlice(worldIn, pos, state, player).consumesAction()) {
            return InteractionResult.SUCCESS;
         }

         if (stack.isEmpty()) {
            return InteractionResult.CONSUME;
         }
      }

      return stack.is(ModTags.KNIVES) ? this.cutSlice(worldIn, pos, state, player) : this.eatSlice(worldIn, pos, state, player);
   }

   public InteractionResult eatSlice(Level level, BlockPos pos, BlockState state, Player player) {
      if (!player.canEat(false)) {
         return InteractionResult.PASS;
      }

      player.awardStat(Stats.EAT_CAKE_SLICE);
      player.getFoodData().eat(2, 0.1F);
      if (!level.isClientSide()) {
         player.addEffect(new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), 600, 0, false, false));
      }

      int i = (Integer)state.getValue(BITES);
      level.gameEvent(player, GameEvent.EAT, pos);
      if (i < 6) {
         level.setBlock(pos, (BlockState)state.setValue(BITES, i + 1), 3);
      } else {
         level.removeBlock(pos, false);
         level.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
      }

      return InteractionResult.SUCCESS;
   }

   public InteractionResult cutSlice(Level worldIn, BlockPos pos, BlockState state, Player player) {
      int bites = (Integer)state.getValue(BITES);
      if (bites < 6) {
         worldIn.setBlock(pos, (BlockState)state.setValue(BITES, bites + 1), 3);
      } else {
         worldIn.removeBlock(pos, false);
      }

      Containers.dropItemStack(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack((ItemLike)FRItems.COFFEE_CAKE_SLICE.get()));
      worldIn.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
      return InteractionResult.SUCCESS;
   }
}
