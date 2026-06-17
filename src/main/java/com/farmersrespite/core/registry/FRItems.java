package com.farmersrespite.core.registry;

import com.farmersrespite.common.item.PurulentTeaItem;
import com.farmersrespite.core.FarmersRespite;
import com.farmersrespite.core.utility.FRFoods;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class FRItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "farmersrespite");


    public static final RegistryObject<Item> KETTLE = ITEMS.register("kettle", () -> new BlockItem(FRBlocks.KETTLE.get(), (new Item.Properties()).stacksTo(1).tab(FarmersRespite.CREATIVE_TAB)));


    public static final RegistryObject<Item> WILD_TEA_BUSH = ITEMS.register("wild_tea_bush", () -> new BlockItem(FRBlocks.WILD_TEA_BUSH.get(), (new Item.Properties()).tab(FarmersRespite.CREATIVE_TAB)));


    public static final RegistryObject<Item> TEA_SEEDS = ITEMS.register("tea_seeds", () -> new BlockItem(FRBlocks.SMALL_TEA_BUSH.get(), (new Item.Properties()).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans", () -> new BlockItem(FRBlocks.COFFEE_BUSH.get(), (new Item.Properties()).tab(FarmersRespite.CREATIVE_TAB)));


    public static final RegistryObject<Item> GREEN_TEA_LEAVES = ITEMS.register("green_tea_leaves", () -> new Item((new Item.Properties()).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> YELLOW_TEA_LEAVES = ITEMS.register("yellow_tea_leaves", () -> new Item((new Item.Properties()).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> BLACK_TEA_LEAVES = ITEMS.register("black_tea_leaves", () -> new Item((new Item.Properties()).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> COFFEE_BERRIES = ITEMS.register("coffee_berries", () -> new Item((new Item.Properties()).food(FRFoods.COFFEE_BERRIES).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> ROSE_HIPS = ITEMS.register("rose_hips", () -> new Item((new Item.Properties()).tab(FarmersRespite.CREATIVE_TAB)));


    public static final RegistryObject<Item> GREEN_TEA = ITEMS.register("green_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB), true, false));

    public static final RegistryObject<Item> YELLOW_TEA = ITEMS.register("yellow_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB), true, false));

    public static final RegistryObject<Item> BLACK_TEA = ITEMS.register("black_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB), true, false));

    public static final RegistryObject<Item> ROSE_HIP_TEA = ITEMS.register("rose_hip_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.ROSE_HIP_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> DANDELION_TEA = ITEMS.register("dandelion_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.DANDELION_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB), true, false));

    public static final RegistryObject<Item> PURULENT_TEA = ITEMS.register("purulent_tea", () -> new PurulentTeaItem(1200, (new Item.Properties()).food(FRFoods.PURULENT_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB), true, false));


    public static final RegistryObject<Item> LONG_GREEN_TEA = ITEMS.register("long_green_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.LONG_GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> LONG_YELLOW_TEA = ITEMS.register("long_yellow_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.LONG_YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> LONG_BLACK_TEA = ITEMS.register("long_black_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.LONG_BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> LONG_ROSE_HIP_TEA = ITEMS.register("long_rose_hip_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.LONG_ROSE_HIP_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> LONG_DANDELION_TEA = ITEMS.register("long_dandelion_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.LONG_DANDELION_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> LONG_COFFEE = ITEMS.register("long_coffee", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.LONG_COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> LONG_APPLE_CIDER = ITEMS.register("long_apple_cider", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.LONG_APPLE_CIDER).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));


    public static final RegistryObject<Item> STRONG_GREEN_TEA = ITEMS.register("strong_green_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.STRONG_GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> STRONG_YELLOW_TEA = ITEMS.register("strong_yellow_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.STRONG_YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> STRONG_BLACK_TEA = ITEMS.register("strong_black_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.STRONG_BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> STRONG_PURULENT_TEA = ITEMS.register("strong_purulent_tea", () -> new PurulentTeaItem(2400, (new Item.Properties()).food(FRFoods.STRONG_PURULENT_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final RegistryObject<Item> STRONG_ROSE_HIP_TEA = ITEMS.register("strong_rose_hip_tea", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.STRONG_ROSE_HIP_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16)));

    public static final RegistryObject<Item> STRONG_COFFEE = ITEMS.register("strong_coffee", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.STRONG_COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));

    public static final RegistryObject<Item> STRONG_APPLE_CIDER = ITEMS.register("strong_apple_cider", () -> new DrinkableItem((new Item.Properties()).food(FRFoods.STRONG_APPLE_CIDER).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false));


    public static final RegistryObject<Item> GREEN_TEA_COOKIE = ITEMS.register("green_tea_cookie", () -> new Item((new Item.Properties()).food(FRFoods.GREEN_TEA_COOKIE).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> NETHER_WART_SOURDOUGH = ITEMS.register("nether_wart_sourdough", () -> new Item((new Item.Properties()).food(FRFoods.NETHER_WART_SOURDOUGH).tab(FarmersRespite.CREATIVE_TAB)));


    public static final RegistryObject<Item> BLACK_COD = ITEMS.register("black_cod", () -> new ConsumableItem((new Item.Properties()).food(FRFoods.BLACK_COD).craftRemainder(Items.BOWL).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB), true));

    public static final RegistryObject<Item> TEA_CURRY = ITEMS.register("tea_curry", () -> new ConsumableItem((new Item.Properties()).food(FRFoods.TEA_CURRY).craftRemainder(Items.BOWL).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB), true));

    public static final RegistryObject<Item> BLAZING_CHILI = ITEMS.register("blazing_chili", () -> new ConsumableItem((new Item.Properties()).food(FRFoods.BLAZING_CHILLI).craftRemainder(Items.BOWL).stacksTo(16).tab(FarmersRespite.CREATIVE_TAB), true));


    public static final RegistryObject<Item> COFFEE_CAKE = ITEMS.register("coffee_cake", () -> new BlockItem(FRBlocks.COFFEE_CAKE.get(), (new Item.Properties()).stacksTo(1).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> COFFEE_CAKE_SLICE = ITEMS.register("coffee_cake_slice", () -> new Item((new Item.Properties()).food(FRFoods.COFFEE_CAKE_SLICE).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> ROSE_HIP_PIE = ITEMS.register("rose_hip_pie", () -> new BlockItem(FRBlocks.ROSE_HIP_PIE.get(), (new Item.Properties()).tab(FarmersRespite.CREATIVE_TAB)));

    public static final RegistryObject<Item> ROSE_HIP_PIE_SLICE = ITEMS.register("rose_hip_pie_slice", () -> new Item((new Item.Properties()).food(FRFoods.ROSE_HIP_PIE_SLICE).tab(FarmersRespite.CREATIVE_TAB)));
}
