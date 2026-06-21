package umpaz.farmersrespite.common.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FREffects;

public class TeaSackBlock extends Block {
   public TeaSackBlock(Properties properties) {
      super(properties);
   }

   public void onCaughtFire(BlockState state, Level level, BlockPos pos, @Nullable Direction face, @Nullable LivingEntity igniter) {
      AreaEffectCloud cloud = new AreaEffectCloud(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
      if (state.getBlock() == FRBlocks.GREEN_TEA_LEAVES_SACK.get()) {
         cloud.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 20, 0));
      } else if (state.getBlock() == FRBlocks.YELLOW_TEA_LEAVES_SACK.get()) {
         cloud.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20, 0));
      } else if (state.getBlock() == FRBlocks.BLACK_TEA_LEAVES_SACK.get()) {
         cloud.addEffect(new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), 20, 0));
      } else if (state.getBlock() == FRBlocks.COFFEE_BEANS_SACK.get()) {
         cloud.addEffect(new MobEffectInstance((MobEffect)FREffects.CAFFEINATED.get(), 20, 1));
      }

      cloud.setDuration(200);
      cloud.setRadius(1.5F);
      cloud.setOwner(igniter);
      level.addFreshEntity(cloud);
      System.out.println(igniter);
      level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.7F, 1.0F);
   }

   public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
      return 150;
   }
}
