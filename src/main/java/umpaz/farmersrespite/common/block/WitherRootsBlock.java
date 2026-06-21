package umpaz.farmersrespite.common.block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.data.FRDamageTypes;

public class WitherRootsBlock extends BushBlock implements BonemealableBlock {
   private static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 14.0, 14.0);
   private static final VoxelShape SHAPE_SMALL = Block.box(2.0, 0.0, 2.0, 14.0, 6.0, 14.0);
   private static final VoxelShape WILD_COFFEE = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);

   public WitherRootsBlock(Properties properties) {
      super(properties);
   }

   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      if (state == ((Block)FRBlocks.WITHER_ROOTS.get()).defaultBlockState()) {
         return SHAPE_SMALL;
      } else {
         return state == ((Block)FRBlocks.WILD_COFFEE_BUSH.get()).defaultBlockState() ? WILD_COFFEE : SHAPE;
      }
   }

   public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      BlockPos blockpos = pos.below();
      return this.mayPlaceOn(level.getBlockState(blockpos), level, blockpos);
   }

   protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
      return state.is(BlockTags.DIRT) || state.is(Blocks.FARMLAND) || state.is(Blocks.BASALT) || state.is(Blocks.MAGMA_BLOCK);
   }

   public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
      if (!state.canSurvive(world, pos)) {
         AreaEffectCloud cloud = new AreaEffectCloud((Level)world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
         cloud.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0));
         cloud.setDuration(600);
         cloud.setRadius(0.5F);
         cloud.setRadiusOnUse(-0.5F);
         world.addFreshEntity(cloud);
         return Blocks.AIR.defaultBlockState();
      } else {
         return super.updateShape(state, facing, facingState, world, pos, facingPos);
      }
   }

   public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (entity instanceof LivingEntity) {
         entity.makeStuckInBlock(state, new Vec3(0.8F, 0.75, 0.8F));
         if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
            double d0 = Math.abs(entity.getX() - entity.xOld);
            double d1 = Math.abs(entity.getZ() - entity.zOld);
            if (d0 >= 0.003F || d1 >= 0.003F) {
               entity.hurt(new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(FRDamageTypes.WITHER_ROOT)), 1.0F);
               ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0));
            }
         }
      }
   }

   public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
      if (!level.isClientSide) {
         AreaEffectCloud cloud = new AreaEffectCloud(level, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
         cloud.addEffect(new MobEffectInstance(MobEffects.WITHER, 200, 0));
         cloud.setDuration(600);
         cloud.setRadius(0.5F);
         cloud.setRadiusOnUse(-0.5F);
         cloud.setOwner(player);
         level.addFreshEntity(cloud);
      }

      super.playerWillDestroy(level, pos, state, player);
   }

   public boolean isPathfindable(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @Nonnull PathComputationType path) {
      return false;
   }

   @Nullable
   public BlockPathTypes getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
      return BlockPathTypes.DAMAGE_OTHER;
   }

   @Nullable
   public BlockPathTypes getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, BlockPathTypes originalType) {
      return BlockPathTypes.DANGER_OTHER;
   }

   public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos pos, BlockState state, boolean isClientSide) {
      return false;
   }

   public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos pos, BlockState state) {
      return false;
   }

   public void performBonemeal(ServerLevel level, RandomSource randomSource, BlockPos pos, BlockState state) {
   }
}
