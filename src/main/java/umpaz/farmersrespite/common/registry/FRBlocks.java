package umpaz.farmersrespite.common.registry;

import java.util.function.ToIntFunction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.block.CandleCoffeeCakeBlock;
import umpaz.farmersrespite.common.block.CoffeeBushBlock;
import umpaz.farmersrespite.common.block.CoffeeBushTopBlock;
import umpaz.farmersrespite.common.block.CoffeeCakeBlock;
import umpaz.farmersrespite.common.block.CoffeeDoubleStemBlock;
import umpaz.farmersrespite.common.block.CoffeeMiddleStemBlock;
import umpaz.farmersrespite.common.block.CoffeeStemBlock;
import umpaz.farmersrespite.common.block.KettleBlock;
import umpaz.farmersrespite.common.block.SmallTeaBushBlock;
import umpaz.farmersrespite.common.block.TeaBushBlock;
import umpaz.farmersrespite.common.block.TeaSackBlock;
import umpaz.farmersrespite.common.block.WildTeaBushBlock;
import umpaz.farmersrespite.common.block.WitherRootsBlock;
import vectorwing.farmersdelight.common.block.PieBlock;

public class FRBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "farmersrespite");
   public static final RegistryObject<Block> KETTLE = BLOCKS.register("kettle", KettleBlock::new);
   public static final RegistryObject<Block> TEA_BUSH = BLOCKS.register(
      "tea_bush",
      () -> new TeaBushBlock(
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noOcclusion()
      )
   );
   public static final RegistryObject<Block> SMALL_TEA_BUSH = BLOCKS.register(
      "small_tea_bush",
      () -> new SmallTeaBushBlock(
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noOcclusion()
      )
   );
   public static final RegistryObject<Block> WILD_TEA_BUSH = BLOCKS.register(
      "wild_tea_bush",
      () -> new WildTeaBushBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
      )
   );
   public static final RegistryObject<Block> POTTED_TEA_BUSH = BLOCKS.register(
      "potted_tea_bush",
      () -> new FlowerPotBlock(null, SMALL_TEA_BUSH, Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).pushReaction(PushReaction.DESTROY).instabreak().noOcclusion())
   );
   public static final RegistryObject<Block> POTTED_WILD_TEA_BUSH = BLOCKS.register(
      "potted_wild_tea_bush",
      () -> new FlowerPotBlock(null, WILD_TEA_BUSH, Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).pushReaction(PushReaction.DESTROY).instabreak().noOcclusion())
   );
   public static final RegistryObject<Block> GREEN_TEA_LEAVES_SACK = BLOCKS.register(
      "green_tea_leaves_sack",
      () -> new TeaSackBlock(Properties.of().mapColor(MapColor.TERRACOTTA_LIGHT_GREEN).ignitedByLava().strength(0.5F).sound(SoundType.WOOL))
   );
   public static final RegistryObject<Block> YELLOW_TEA_LEAVES_SACK = BLOCKS.register(
      "yellow_tea_leaves_sack",
      () -> new TeaSackBlock(Properties.of().mapColor(MapColor.COLOR_YELLOW).ignitedByLava().strength(0.5F).sound(SoundType.WOOL))
   );
   public static final RegistryObject<Block> BLACK_TEA_LEAVES_SACK = BLOCKS.register(
      "black_tea_leaves_sack",
      () -> new TeaSackBlock(Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).ignitedByLava().strength(0.5F).sound(SoundType.WOOL))
   );
   public static final RegistryObject<Block> COFFEE_BEANS_SACK = BLOCKS.register(
      "coffee_beans_sack", () -> new TeaSackBlock(Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().strength(0.5F).sound(SoundType.WOOL))
   );
   public static final RegistryObject<Block> COFFEE_BUSH = BLOCKS.register(
      "coffee_bush",
      () -> new CoffeeBushBlock(
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noOcclusion()
      )
   );
   public static final RegistryObject<Block> COFFEE_STEM = BLOCKS.register(
      "coffee_stem",
      () -> new CoffeeStemBlock(
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noOcclusion()
      )
   );
   public static final RegistryObject<Block> COFFEE_BUSH_TOP = BLOCKS.register(
      "coffee_bush_top",
      () -> new CoffeeBushTopBlock(
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noOcclusion()
      )
   );
   public static final RegistryObject<Block> COFFEE_STEM_DOUBLE = BLOCKS.register(
      "coffee_stem_double",
      () -> new CoffeeDoubleStemBlock(
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noOcclusion()
      )
   );
   public static final RegistryObject<Block> COFFEE_STEM_MIDDLE = BLOCKS.register(
      "coffee_stem_middle",
      () -> new CoffeeMiddleStemBlock(
         Properties.of().mapColor(MapColor.PLANT).pushReaction(PushReaction.DESTROY).instabreak().sound(SoundType.GRASS).noOcclusion()
      )
   );
   public static final RegistryObject<Block> WITHER_ROOTS = BLOCKS.register(
      "wither_roots",
      () -> new WitherRootsBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
      )
   );
   public static final RegistryObject<Block> WITHER_ROOTS_PLANT = BLOCKS.register(
      "wither_roots_plant",
      () -> new WitherRootsBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
      )
   );
   public static final RegistryObject<Block> WILD_COFFEE_BUSH = BLOCKS.register(
      "wild_coffee_bush",
      () -> new WitherRootsBlock(
         Properties.of()
            .mapColor(MapColor.PLANT)
            .pushReaction(PushReaction.DESTROY)
            .noCollission()
            .instabreak()
            .sound(SoundType.GRASS)
            .offsetType(OffsetType.XZ)
      )
   );
   public static final RegistryObject<Block> POTTED_COFFEE_BUSH = BLOCKS.register(
      "potted_coffee_bush",
      () -> new FlowerPotBlock(null, COFFEE_BUSH, Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).pushReaction(PushReaction.DESTROY).instabreak().noOcclusion())
   );
   public static final RegistryObject<Block> POTTED_WILD_COFFEE_BUSH = BLOCKS.register(
      "potted_wild_coffee_bush",
      () -> new FlowerPotBlock(
         null, WILD_COFFEE_BUSH, Properties.of().mapColor(MapColor.TERRACOTTA_BLACK).pushReaction(PushReaction.DESTROY).instabreak().noOcclusion()
      )
   );
   public static final RegistryObject<Block> COFFEE_CAKE = BLOCKS.register(
      "coffee_cake",
      () -> new CoffeeCakeBlock(
         Properties.of().mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).strength(0.5F).sound(SoundType.WOOL)
      )
   );
   public static final RegistryObject<Block> ROSE_HIP_PIE = BLOCKS.register(
      "rose_hip_pie", () -> new PieBlock(Properties.copy(Blocks.CAKE), FRItems.ROSE_HIP_PIE_SLICE)
   );
   public static final RegistryObject<Block> CANDLE_COFFEE_CAKE = BLOCKS.register(
      "candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> WHITE_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "white_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.WHITE_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> ORANGE_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "orange_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.ORANGE_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> MAGENTA_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "magenta_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.MAGENTA_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> LIGHT_BLUE_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "light_blue_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.LIGHT_BLUE_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> YELLOW_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "yellow_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.YELLOW_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> LIME_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "lime_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.LIME_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> PINK_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "pink_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.PINK_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> GRAY_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "gray_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.GRAY_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> LIGHT_GRAY_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "light_gray_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.LIGHT_GRAY_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> CYAN_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "cyan_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.CYAN_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> PURPLE_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "purple_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.PURPLE_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> BLUE_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "blue_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.BLUE_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> BROWN_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "brown_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.BROWN_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> GREEN_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "green_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.GREEN_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> RED_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "red_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.RED_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );
   public static final RegistryObject<Block> BLACK_CANDLE_COFFEE_CAKE = BLOCKS.register(
      "black_candle_coffee_cake",
      () -> new CandleCoffeeCakeBlock(
         Blocks.BLACK_CANDLE,
         Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .pushReaction(PushReaction.DESTROY)
            .strength(0.5F)
            .sound(SoundType.WOOL)
            .lightLevel(litBlockEmission(3))
      )
   );

   private static ToIntFunction<BlockState> litBlockEmission(int lightValue) {
      return state -> state.getValue(BlockStateProperties.LIT) ? lightValue : 0;
   }
}
