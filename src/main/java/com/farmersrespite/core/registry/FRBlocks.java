package com.farmersrespite.core.registry;

import com.farmersrespite.common.block.CoffeeBushBlock;
import com.farmersrespite.common.block.CoffeeBushTopBlock;
import com.farmersrespite.common.block.CoffeeCakeBlock;
import com.farmersrespite.common.block.CoffeeCandleCakeBlock;
import com.farmersrespite.common.block.CoffeeDoubleStemBlock;
import com.farmersrespite.common.block.CoffeeMiddleStemBlock;
import com.farmersrespite.common.block.CoffeeStemBlock;
import com.farmersrespite.common.block.SmallTeaBushBlock;
import com.farmersrespite.common.block.TeaBushBlock;
import com.farmersrespite.common.block.WildTeaBushBlock;
import com.farmersrespite.common.block.WitherRootsBlock;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.block.PieBlock;


public class FRBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "farmersrespite");


    public static final RegistryObject<Block> KETTLE = BLOCKS.register("kettle", com.farmersrespite.common.block.KettleBlock::new);


    public static final RegistryObject<Block> TEA_BUSH = BLOCKS.register("tea_bush", () -> new TeaBushBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> SMALL_TEA_BUSH = BLOCKS.register("small_tea_bush", () -> new SmallTeaBushBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> POTTED_TEA_BUSH = BLOCKS.register("potted_tea_bush", () -> new FlowerPotBlock(SMALL_TEA_BUSH.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> WILD_TEA_BUSH = BLOCKS.register("wild_tea_bush", () -> new WildTeaBushBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> POTTED_WILD_TEA_BUSH = BLOCKS.register("potted_wild_tea_bush", () -> new FlowerPotBlock(WILD_TEA_BUSH.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> COFFEE_BUSH = BLOCKS.register("coffee_bush", () -> new CoffeeBushBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> POTTED_COFFEE_BUSH = BLOCKS.register("potted_coffee_bush", () -> new FlowerPotBlock(COFFEE_BUSH.get(), BlockBehaviour.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> COFFEE_STEM = BLOCKS.register("coffee_stem", () -> new CoffeeStemBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> COFFEE_BUSH_TOP = BLOCKS.register("coffee_bush_top", () -> new CoffeeBushTopBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> COFFEE_STEM_DOUBLE = BLOCKS.register("coffee_stem_double", () -> new CoffeeDoubleStemBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> COFFEE_STEM_MIDDLE = BLOCKS.register("coffee_stem_middle", () -> new CoffeeMiddleStemBlock(BlockBehaviour.Properties.of(Material.PLANT).instabreak().sound(SoundType.GRASS).noOcclusion()));
    public static final RegistryObject<Block> WITHER_ROOTS = BLOCKS.register("wither_roots", () -> new WitherRootsBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> WITHER_ROOTS_PLANT = BLOCKS.register("wither_roots_plant", () -> new WitherRootsBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> COFFEE_CAKE = BLOCKS.register("coffee_cake", () -> new CoffeeCakeBlock(BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL)));
    public static final RegistryObject<Block> ROSE_HIP_PIE = BLOCKS.register("rose_hip_pie", () -> new PieBlock(BlockBehaviour.Properties.copy(Blocks.CAKE), FRItems.ROSE_HIP_PIE_SLICE));
    public static final RegistryObject<Block> CANDLE_COFFEE_CAKE = BLOCKS.register("candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> WHITE_CANDLE_COFFEE_CAKE = BLOCKS.register("white_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.WHITE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> ORANGE_CANDLE_COFFEE_CAKE = BLOCKS.register("orange_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.ORANGE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> MAGENTA_CANDLE_COFFEE_CAKE = BLOCKS.register("magenta_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.MAGENTA_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> LIGHT_BLUE_CANDLE_COFFEE_CAKE = BLOCKS.register("light_blue_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.LIGHT_BLUE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> YELLOW_CANDLE_COFFEE_CAKE = BLOCKS.register("yellow_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.YELLOW_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> LIME_CANDLE_COFFEE_CAKE = BLOCKS.register("lime_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.LIME_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> PINK_CANDLE_COFFEE_CAKE = BLOCKS.register("pink_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.PINK_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> GRAY_CANDLE_COFFEE_CAKE = BLOCKS.register("gray_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.GRAY_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> LIGHT_GRAY_CANDLE_COFFEE_CAKE = BLOCKS.register("light_gray_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.LIGHT_GRAY_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> CYAN_CANDLE_COFFEE_CAKE = BLOCKS.register("cyan_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.CYAN_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> PURPLE_CANDLE_COFFEE_CAKE = BLOCKS.register("purple_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.PURPLE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> BLUE_CANDLE_COFFEE_CAKE = BLOCKS.register("blue_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.BLUE_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> BROWN_CANDLE_COFFEE_CAKE = BLOCKS.register("brown_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.BROWN_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> GREEN_CANDLE_COFFEE_CAKE = BLOCKS.register("green_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.GREEN_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> RED_CANDLE_COFFEE_CAKE = BLOCKS.register("red_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.RED_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));
    public static final RegistryObject<Block> BLACK_CANDLE_COFFEE_CAKE = BLOCKS.register("black_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.BLACK_CANDLE, BlockBehaviour.Properties.of(Material.CAKE, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3))));

    private static ToIntFunction<BlockState> litBlockEmission(int level) {
        return state -> ((Boolean) state.getValue((Property) BlockStateProperties.LIT)).booleanValue() ? level : 0;
    }
}
