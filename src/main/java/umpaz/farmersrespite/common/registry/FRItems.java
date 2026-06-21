package umpaz.farmersrespite.common.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.FRFoodValues;
import umpaz.farmersrespite.common.item.GamblersTeaItem;
import umpaz.farmersrespite.common.item.KettleItem;
import umpaz.farmersrespite.common.item.PurulentTeaItem;
import umpaz.farmersrespite.common.item.StrongHotCocoaItem;
import umpaz.farmersrespite.common.item.StrongMelonJuiceItem;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;

public class FRItems {
   public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "farmersrespite");
   public static final RegistryObject<Item> KETTLE = ITEMS.register("kettle", () -> new KettleItem((Block)FRBlocks.KETTLE.get(), basicItem().stacksTo(1)));
   public static final RegistryObject<Item> GREEN_TEA_LEAVES_SACK = ITEMS.register(
      "green_tea_leaves_sack", () -> new BlockItem((Block)FRBlocks.GREEN_TEA_LEAVES_SACK.get(), basicItem())
   );
   public static final RegistryObject<Item> YELLOW_TEA_LEAVES_SACK = ITEMS.register(
      "yellow_tea_leaves_sack", () -> new BlockItem((Block)FRBlocks.YELLOW_TEA_LEAVES_SACK.get(), basicItem())
   );
   public static final RegistryObject<Item> BLACK_TEA_LEAVES_SACK = ITEMS.register(
      "black_tea_leaves_sack", () -> new BlockItem((Block)FRBlocks.BLACK_TEA_LEAVES_SACK.get(), basicItem())
   );
   public static final RegistryObject<Item> COFFEE_BEANS_SACK = ITEMS.register(
      "coffee_beans_sack", () -> new BlockItem((Block)FRBlocks.COFFEE_BEANS_SACK.get(), basicItem())
   );
   public static final RegistryObject<Item> WILD_TEA_BUSH = ITEMS.register(
      "wild_tea_bush", () -> new BlockItem((Block)FRBlocks.WILD_TEA_BUSH.get(), basicItem())
   );
   public static final RegistryObject<Item> WILD_COFFEE_BUSH = ITEMS.register(
      "wild_coffee_bush", () -> new BlockItem((Block)FRBlocks.WILD_COFFEE_BUSH.get(), basicItem())
   );
   public static final RegistryObject<Item> GREEN_TEA_LEAVES = ITEMS.register("green_tea_leaves", () -> new Item(basicItem()));
   public static final RegistryObject<Item> YELLOW_TEA_LEAVES = ITEMS.register("yellow_tea_leaves", () -> new Item(basicItem()));
   public static final RegistryObject<Item> BLACK_TEA_LEAVES = ITEMS.register("black_tea_leaves", () -> new Item(basicItem()));
   public static final RegistryObject<Item> TEA_SEEDS = ITEMS.register("tea_seeds", () -> new BlockItem((Block)FRBlocks.SMALL_TEA_BUSH.get(), basicItem()));
   public static final RegistryObject<Item> COFFEE_BEANS = ITEMS.register("coffee_beans", () -> new BlockItem((Block)FRBlocks.COFFEE_BUSH.get(), basicItem()));
   public static final RegistryObject<Item> COFFEE_BERRIES = ITEMS.register("coffee_berries", () -> new Item(foodItem(FRFoodValues.COFFEE_BERRIES)));
   public static final RegistryObject<Item> ROSE_HIPS = ITEMS.register("rose_hips", () -> new Item(basicItem()));
   public static final RegistryObject<Item> GREEN_TEA = ITEMS.register(
      "green_tea", () -> new DrinkableItem(drinkItem().food(FRFoodValues.GREEN_TEA), true, false)
   );
   public static final RegistryObject<Item> YELLOW_TEA = ITEMS.register(
      "yellow_tea", () -> new DrinkableItem(drinkItem().food(FRFoodValues.YELLOW_TEA), true, false)
   );
   public static final RegistryObject<Item> BLACK_TEA = ITEMS.register(
      "black_tea", () -> new DrinkableItem(drinkItem().food(FRFoodValues.BLACK_TEA), true, false)
   );
   public static final RegistryObject<Item> ROSE_HIP_TEA = ITEMS.register(
      "rose_hip_tea", () -> new DrinkableItem(drinkItem().food(FRFoodValues.ROSE_HIP_TEA), true, false)
   );
   public static final RegistryObject<Item> DANDELION_TEA = ITEMS.register(
      "dandelion_tea", () -> new DrinkableItem(drinkItem().food(FRFoodValues.DANDELION_TEA), true, false)
   );
   public static final RegistryObject<Item> PURULENT_TEA = ITEMS.register(
      "purulent_tea", () -> new PurulentTeaItem(300, drinkItem().food(FRFoodValues.PURULENT_TEA))
   );
   public static final RegistryObject<Item> GAMBLERS_TEA = ITEMS.register("gamblers_tea", () -> new GamblersTeaItem(200, 0, drinkItem()));
   public static final RegistryObject<Item> COFFEE = ITEMS.register("coffee", () -> new DrinkableItem(drinkItem().food(FRFoodValues.COFFEE), true, false));
   public static final RegistryObject<Item> LONG_GREEN_TEA = ITEMS.register(
      "long_green_tea", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.LONG_GREEN_TEA), true, false)
   );
   public static final RegistryObject<Item> LONG_YELLOW_TEA = ITEMS.register(
      "long_yellow_tea", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.LONG_YELLOW_TEA), true, false)
   );
   public static final RegistryObject<Item> LONG_BLACK_TEA = ITEMS.register(
      "long_black_tea", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.LONG_BLACK_TEA), true, false)
   );
   public static final RegistryObject<Item> LONG_ROSE_HIP_TEA = ITEMS.register(
      "long_rose_hip_tea", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.LONG_ROSE_HIP_TEA), true, false)
   );
   public static final RegistryObject<Item> LONG_DANDELION_TEA = ITEMS.register(
      "long_dandelion_tea", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.LONG_DANDELION_TEA), true, false)
   );
   public static final RegistryObject<Item> LONG_PURULENT_TEA = ITEMS.register(
      "long_purulent_tea", () -> new PurulentTeaItem(300, drinkItemNoItem().food(FRFoodValues.LONG_PURULENT_TEA))
   );
   public static final RegistryObject<Item> LONG_GAMBLERS_TEA = ITEMS.register("long_gamblers_tea", () -> new GamblersTeaItem(300, 0, drinkItemNoItem()));
   public static final RegistryObject<Item> LONG_COFFEE = ITEMS.register(
      "long_coffee", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.LONG_COFFEE), true, false)
   );
   public static final RegistryObject<Item> LONG_APPLE_CIDER = ITEMS.register(
      "long_apple_cider", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.LONG_APPLE_CIDER), true, false)
   );
   public static final RegistryObject<Item> STRONG_GREEN_TEA = ITEMS.register(
      "strong_green_tea", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.STRONG_GREEN_TEA), true, false)
   );
   public static final RegistryObject<Item> STRONG_YELLOW_TEA = ITEMS.register(
      "strong_yellow_tea", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.STRONG_YELLOW_TEA), true, false)
   );
   public static final RegistryObject<Item> STRONG_BLACK_TEA = ITEMS.register(
      "strong_black_tea", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.STRONG_BLACK_TEA), true, false)
   );
   public static final RegistryObject<Item> STRONG_ROSE_HIP_TEA = ITEMS.register(
      "strong_rose_hip_tea", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.STRONG_ROSE_HIP_TEA), true, false)
   );
   public static final RegistryObject<Item> STRONG_PURULENT_TEA = ITEMS.register(
      "strong_purulent_tea", () -> new PurulentTeaItem(600, drinkItemNoItem().food(FRFoodValues.PURULENT_TEA))
   );
   public static final RegistryObject<Item> STRONG_GAMBLERS_TEA = ITEMS.register("strong_gamblers_tea", () -> new GamblersTeaItem(200, 1, drinkItemNoItem()));
   public static final RegistryObject<Item> STRONG_COFFEE = ITEMS.register(
      "strong_coffee", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.STRONG_COFFEE), true, false)
   );
   public static final RegistryObject<Item> STRONG_MELON_JUICE = ITEMS.register("strong_melon_juice", () -> new StrongMelonJuiceItem(drinkItemNoItem()));
   public static final RegistryObject<Item> STRONG_APPLE_CIDER = ITEMS.register(
      "strong_apple_cider", () -> new DrinkableItem(drinkItemNoItem().food(FRFoodValues.STRONG_APPLE_CIDER), true, false)
   );
   public static final RegistryObject<Item> STRONG_HOT_COCOA = ITEMS.register("strong_hot_cocoa", () -> new StrongHotCocoaItem(drinkItemNoItem()));
   public static final RegistryObject<Item> GREEN_TEA_COOKIE = ITEMS.register("green_tea_cookie", () -> new Item(foodItem(FRFoodValues.GREEN_TEA_COOKIE)));
   public static final RegistryObject<Item> NETHER_WART_SOURDOUGH = ITEMS.register(
      "nether_wart_sourdough", () -> new Item(foodItem(FRFoodValues.NETHER_WART_SOURDOUGH))
   );
   public static final RegistryObject<Item> BLACK_COD = ITEMS.register("black_cod", () -> new ConsumableItem(bowlFoodItem(FRFoodValues.BLACK_COD), true));
   public static final RegistryObject<Item> TEA_CURRY = ITEMS.register("tea_curry", () -> new ConsumableItem(bowlFoodItem(FRFoodValues.TEA_CURRY), true));
   public static final RegistryObject<Item> BLAZING_CHILI = ITEMS.register(
      "blazing_chili", () -> new ConsumableItem(bowlFoodItem(FRFoodValues.BLAZING_CHILLI), true)
   );
   public static final RegistryObject<Item> COFFEE_CAKE = ITEMS.register(
      "coffee_cake", () -> new BlockItem((Block)FRBlocks.COFFEE_CAKE.get(), basicItem().stacksTo(1))
   );
   public static final RegistryObject<Item> COFFEE_CAKE_SLICE = ITEMS.register("coffee_cake_slice", () -> new Item(foodItem(FRFoodValues.COFFEE_CAKE_SLICE)));
   public static final RegistryObject<Item> ROSE_HIP_PIE = ITEMS.register(
      "rose_hip_pie", () -> new BlockItem((Block)FRBlocks.ROSE_HIP_PIE.get(), new Properties())
   );
   public static final RegistryObject<Item> ROSE_HIP_PIE_SLICE = ITEMS.register("rose_hip_pie_slice", () -> new Item(foodItem(FRFoodValues.ROSE_HIP_PIE_SLICE)));

   public static Properties basicItem() {
      return new Properties();
   }

   public static Properties foodItem(FoodProperties food) {
      return new Properties().food(food);
   }

   public static Properties bowlFoodItem(FoodProperties food) {
      return new Properties().food(food).craftRemainder(Items.BOWL).stacksTo(16);
   }

   public static Properties drinkItem() {
      return new Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
   }

   public static Properties drinkItemNoItem() {
      return new Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16);
   }
}
