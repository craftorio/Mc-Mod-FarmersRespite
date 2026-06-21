package umpaz.farmersrespite.common.registry;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid.Flowing;
import net.minecraftforge.fluids.ForgeFlowingFluid.Properties;
import net.minecraftforge.fluids.ForgeFlowingFluid.Source;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import umpaz.farmersrespite.common.fluid.TeaFluidType;

public class FRFluids {
   public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(Keys.FLUID_TYPES, "farmersrespite");
   public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, "farmersrespite");
   public static final RegistryObject<FluidType> GREEN_TEA_TYPE = FLUID_TYPES.register("green_tea_type", () -> new TeaFluidType(-6314437));
   public static final RegistryObject<FlowingFluid> GREEN_TEA = FLUIDS.register("green_tea", () -> new Source(FRFluids.GREEN_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_GREEN_TEA = FLUIDS.register("flowing_green_tea", () -> new Flowing(FRFluids.GREEN_TEA_PROPERTIES));
   public static final Properties GREEN_TEA_PROPERTIES = new Properties(GREEN_TEA_TYPE, GREEN_TEA, FLOWING_GREEN_TEA);
   public static final RegistryObject<FluidType> YELLOW_TEA_TYPE = FLUID_TYPES.register("yellow_tea_type", () -> new TeaFluidType(-5668031));
   public static final RegistryObject<FlowingFluid> YELLOW_TEA = FLUIDS.register("yellow_tea", () -> new Source(FRFluids.YELLOW_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_YELLOW_TEA = FLUIDS.register(
      "flowing_yellow_tea", () -> new Flowing(FRFluids.YELLOW_TEA_PROPERTIES)
   );
   public static final Properties YELLOW_TEA_PROPERTIES = new Properties(YELLOW_TEA_TYPE, YELLOW_TEA, FLOWING_YELLOW_TEA);
   public static final RegistryObject<FluidType> BLACK_TEA_TYPE = FLUID_TYPES.register("black_tea_type", () -> new TeaFluidType(-8962777));
   public static final RegistryObject<FlowingFluid> BLACK_TEA = FLUIDS.register("black_tea", () -> new Source(FRFluids.BLACK_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_BLACK_TEA = FLUIDS.register("flowing_black_tea", () -> new Flowing(FRFluids.BLACK_TEA_PROPERTIES));
   public static final Properties BLACK_TEA_PROPERTIES = new Properties(BLACK_TEA_TYPE, BLACK_TEA, FLOWING_BLACK_TEA);
   public static final RegistryObject<FluidType> ROSE_HIP_TEA_TYPE = FLUID_TYPES.register("rose_hip_tea_type", () -> new TeaFluidType(-4381399));
   public static final RegistryObject<FlowingFluid> ROSE_HIP_TEA = FLUIDS.register("rose_hip_tea", () -> new Source(FRFluids.ROSE_HIP_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_ROSE_HIP_TEA = FLUIDS.register(
      "flowing_rose_hip_tea", () -> new Flowing(FRFluids.ROSE_HIP_TEA_PROPERTIES)
   );
   public static final Properties ROSE_HIP_TEA_PROPERTIES = new Properties(ROSE_HIP_TEA_TYPE, ROSE_HIP_TEA, FLOWING_ROSE_HIP_TEA);
   public static final RegistryObject<FluidType> DANDELION_TEA_TYPE = FLUID_TYPES.register("dandelion_tea_type", () -> new TeaFluidType(-2058191));
   public static final RegistryObject<FlowingFluid> DANDELION_TEA = FLUIDS.register("dandelion_tea", () -> new Source(FRFluids.DANDELION_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_DANDELION_TEA = FLUIDS.register(
      "flowing_dandelion_tea", () -> new Flowing(FRFluids.DANDELION_TEA_PROPERTIES)
   );
   public static final Properties DANDELION_TEA_PROPERTIES = new Properties(DANDELION_TEA_TYPE, DANDELION_TEA, FLOWING_DANDELION_TEA);
   public static final RegistryObject<FluidType> PURULENT_TEA_TYPE = FLUID_TYPES.register("purulent_tea_type", () -> new TeaFluidType(-8248779));
   public static final RegistryObject<FlowingFluid> PURULENT_TEA = FLUIDS.register("purulent_tea", () -> new Source(FRFluids.PURULENT_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_PURULENT_TEA = FLUIDS.register(
      "flowing_purulent_tea", () -> new Flowing(FRFluids.PURULENT_TEA_PROPERTIES)
   );
   public static final Properties PURULENT_TEA_PROPERTIES = new Properties(PURULENT_TEA_TYPE, PURULENT_TEA, FLOWING_PURULENT_TEA);
   public static final RegistryObject<FluidType> GAMBLERS_TEA_TYPE = FLUID_TYPES.register("gamblers_tea_type", () -> new TeaFluidType(-10992588));
   public static final RegistryObject<FlowingFluid> GAMBLERS_TEA = FLUIDS.register("gamblers_tea", () -> new Source(FRFluids.GAMBLERS_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_GAMBLERS_TEA = FLUIDS.register(
      "flowing_gamblers_tea", () -> new Flowing(FRFluids.GAMBLERS_TEA_PROPERTIES)
   );
   public static final Properties GAMBLERS_TEA_PROPERTIES = new Properties(GAMBLERS_TEA_TYPE, GAMBLERS_TEA, FLOWING_GAMBLERS_TEA);
   public static final RegistryObject<FluidType> COFFEE_TYPE = FLUID_TYPES.register("coffee_type", () -> new TeaFluidType(-12047845));
   public static final RegistryObject<FlowingFluid> COFFEE = FLUIDS.register("coffee", () -> new Source(FRFluids.COFFEE_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_COFFEE = FLUIDS.register("flowing_coffee", () -> new Flowing(FRFluids.COFFEE_PROPERTIES));
   public static final Properties COFFEE_PROPERTIES = new Properties(COFFEE_TYPE, COFFEE, FLOWING_COFFEE);
   public static final RegistryObject<FluidType> APPLE_CIDER_TYPE = FLUID_TYPES.register("apple_cider_type", () -> new TeaFluidType(-3897274));
   public static final RegistryObject<FlowingFluid> APPLE_CIDER = FLUIDS.register("apple_cider", () -> new Source(FRFluids.APPLE_CIDER_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_APPLE_CIDER = FLUIDS.register(
      "flowing_apple_cider", () -> new Flowing(FRFluids.APPLE_CIDER_PROPERTIES)
   );
   public static final Properties APPLE_CIDER_PROPERTIES = new Properties(APPLE_CIDER_TYPE, APPLE_CIDER, FLOWING_APPLE_CIDER);
   public static final RegistryObject<FluidType> MELON_JUICE_TYPE = FLUID_TYPES.register("melon_juice_type", () -> new TeaFluidType(-2145741));
   public static final RegistryObject<FlowingFluid> MELON_JUICE = FLUIDS.register("melon_juice", () -> new Source(FRFluids.MELON_JUICE_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_MELON_JUICE = FLUIDS.register(
      "flowing_melon_juice", () -> new Flowing(FRFluids.MELON_JUICE_PROPERTIES)
   );
   public static final Properties MELON_JUICE_PROPERTIES = new Properties(MELON_JUICE_TYPE, MELON_JUICE, FLOWING_MELON_JUICE);
   public static final RegistryObject<FluidType> HOT_COCOA_TYPE = FLUID_TYPES.register("hot_cocoa_type", () -> new TeaFluidType(-7514822));
   public static final RegistryObject<FlowingFluid> HOT_COCOA = FLUIDS.register("hot_cocoa", () -> new Source(FRFluids.HOT_COCOA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_HOT_COCOA = FLUIDS.register("flowing_hot_cocoa", () -> new Flowing(FRFluids.HOT_COCOA_PROPERTIES));
   public static final Properties HOT_COCOA_PROPERTIES = new Properties(HOT_COCOA_TYPE, HOT_COCOA, FLOWING_HOT_COCOA);
   public static final RegistryObject<FluidType> LONG_GREEN_TEA_TYPE = FLUID_TYPES.register("long_green_tea_type", () -> new TeaFluidType(-6314437));
   public static final RegistryObject<FlowingFluid> LONG_GREEN_TEA = FLUIDS.register("long_green_tea", () -> new Source(FRFluids.LONG_GREEN_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_LONG_GREEN_TEA = FLUIDS.register(
      "flowing_long_green_tea", () -> new Flowing(FRFluids.LONG_GREEN_TEA_PROPERTIES)
   );
   public static final Properties LONG_GREEN_TEA_PROPERTIES = new Properties(LONG_GREEN_TEA_TYPE, LONG_GREEN_TEA, FLOWING_LONG_GREEN_TEA);
   public static final RegistryObject<FluidType> LONG_YELLOW_TEA_TYPE = FLUID_TYPES.register("long_yellow_tea_type", () -> new TeaFluidType(-5668031));
   public static final RegistryObject<FlowingFluid> LONG_YELLOW_TEA = FLUIDS.register("long_yellow_tea", () -> new Source(FRFluids.LONG_YELLOW_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_LONG_YELLOW_TEA = FLUIDS.register(
      "flowing_long_yellow_tea", () -> new Flowing(FRFluids.LONG_YELLOW_TEA_PROPERTIES)
   );
   public static final Properties LONG_YELLOW_TEA_PROPERTIES = new Properties(LONG_YELLOW_TEA_TYPE, LONG_YELLOW_TEA, FLOWING_LONG_YELLOW_TEA);
   public static final RegistryObject<FluidType> LONG_BLACK_TEA_TYPE = FLUID_TYPES.register("long_black_tea_type", () -> new TeaFluidType(-8962777));
   public static final RegistryObject<FlowingFluid> LONG_BLACK_TEA = FLUIDS.register("long_black_tea", () -> new Source(FRFluids.LONG_BLACK_TEA_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_LONG_BLACK_TEA = FLUIDS.register(
      "flowing_long_black_tea", () -> new Flowing(FRFluids.LONG_BLACK_TEA_PROPERTIES)
   );
   public static final Properties LONG_BLACK_TEA_PROPERTIES = new Properties(LONG_BLACK_TEA_TYPE, LONG_BLACK_TEA, FLOWING_LONG_BLACK_TEA);
   public static final RegistryObject<FluidType> LONG_ROSE_HIP_TEA_TYPE = FLUID_TYPES.register("long_rose_hip_tea_type", () -> new TeaFluidType(-4381399));
   public static final RegistryObject<FlowingFluid> LONG_ROSE_HIP_TEA = FLUIDS.register(
      "long_rose_hip_tea", () -> new Source(FRFluids.LONG_ROSE_HIP_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_LONG_ROSE_HIP_TEA = FLUIDS.register(
      "flowing_long_rose_hip_tea", () -> new Flowing(FRFluids.LONG_ROSE_HIP_TEA_PROPERTIES)
   );
   public static final Properties LONG_ROSE_HIP_TEA_PROPERTIES = new Properties(LONG_ROSE_HIP_TEA_TYPE, LONG_ROSE_HIP_TEA, FLOWING_LONG_ROSE_HIP_TEA);
   public static final RegistryObject<FluidType> LONG_DANDELION_TEA_TYPE = FLUID_TYPES.register("long_dandelion_tea_type", () -> new TeaFluidType(-2058191));
   public static final RegistryObject<FlowingFluid> LONG_DANDELION_TEA = FLUIDS.register(
      "long_dandelion_tea", () -> new Source(FRFluids.LONG_DANDELION_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_LONG_DANDELION_TEA = FLUIDS.register(
      "flowing_long_dandelion_tea", () -> new Flowing(FRFluids.LONG_DANDELION_TEA_PROPERTIES)
   );
   public static final Properties LONG_DANDELION_TEA_PROPERTIES = new Properties(LONG_DANDELION_TEA_TYPE, LONG_DANDELION_TEA, FLOWING_LONG_DANDELION_TEA);
   public static final RegistryObject<FluidType> LONG_PURULENT_TEA_TYPE = FLUID_TYPES.register("long_purulent_tea_type", () -> new TeaFluidType(-8248779));
   public static final RegistryObject<FlowingFluid> LONG_PURULENT_TEA = FLUIDS.register(
      "long_purulent_tea", () -> new Source(FRFluids.LONG_PURULENT_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_LONG_PURULENT_TEA = FLUIDS.register(
      "flowing_long_purulent_tea", () -> new Flowing(FRFluids.LONG_PURULENT_TEA_PROPERTIES)
   );
   public static final Properties LONG_PURULENT_TEA_PROPERTIES = new Properties(LONG_PURULENT_TEA_TYPE, LONG_PURULENT_TEA, FLOWING_LONG_PURULENT_TEA);
   public static final RegistryObject<FluidType> LONG_GAMBLERS_TEA_TYPE = FLUID_TYPES.register("long_gamblers_tea_type", () -> new TeaFluidType(-10992588));
   public static final RegistryObject<FlowingFluid> LONG_GAMBLERS_TEA = FLUIDS.register(
      "long_gamblers_tea", () -> new Source(FRFluids.LONG_GAMBLERS_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_LONG_GAMBLERS_TEA = FLUIDS.register(
      "flowing_long_gamblers_tea", () -> new Flowing(FRFluids.LONG_GAMBLERS_TEA_PROPERTIES)
   );
   public static final Properties LONG_GAMBLERS_TEA_PROPERTIES = new Properties(LONG_GAMBLERS_TEA_TYPE, LONG_GAMBLERS_TEA, FLOWING_LONG_GAMBLERS_TEA);
   public static final RegistryObject<FluidType> LONG_COFFEE_TYPE = FLUID_TYPES.register("long_coffee_type", () -> new TeaFluidType(-12047845));
   public static final RegistryObject<FlowingFluid> LONG_COFFEE = FLUIDS.register("long_coffee", () -> new Source(FRFluids.LONG_COFFEE_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_LONG_COFFEE = FLUIDS.register(
      "flowing_long_coffee", () -> new Flowing(FRFluids.LONG_COFFEE_PROPERTIES)
   );
   public static final Properties LONG_COFFEE_PROPERTIES = new Properties(LONG_COFFEE_TYPE, LONG_COFFEE, FLOWING_LONG_COFFEE);
   public static final RegistryObject<FluidType> LONG_APPLE_CIDER_TYPE = FLUID_TYPES.register("long_apple_cider_type", () -> new TeaFluidType(-3897274));
   public static final RegistryObject<FlowingFluid> LONG_APPLE_CIDER = FLUIDS.register(
      "long_apple_cider", () -> new Source(FRFluids.LONG_APPLE_CIDER_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_LONG_APPLE_CIDER = FLUIDS.register(
      "flowing_long_apple_cider", () -> new Flowing(FRFluids.LONG_APPLE_CIDER_PROPERTIES)
   );
   public static final Properties LONG_APPLE_CIDER_PROPERTIES = new Properties(LONG_APPLE_CIDER_TYPE, LONG_APPLE_CIDER, FLOWING_LONG_APPLE_CIDER);
   public static final RegistryObject<FluidType> STRONG_GREEN_TEA_TYPE = FLUID_TYPES.register("strong_green_tea_type", () -> new TeaFluidType(-6314437));
   public static final RegistryObject<FlowingFluid> STRONG_GREEN_TEA = FLUIDS.register(
      "strong_green_tea", () -> new Source(FRFluids.STRONG_GREEN_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_GREEN_TEA = FLUIDS.register(
      "flowing_strong_green_tea", () -> new Flowing(FRFluids.STRONG_GREEN_TEA_PROPERTIES)
   );
   public static final Properties STRONG_GREEN_TEA_PROPERTIES = new Properties(STRONG_GREEN_TEA_TYPE, STRONG_GREEN_TEA, FLOWING_STRONG_GREEN_TEA);
   public static final RegistryObject<FluidType> STRONG_YELLOW_TEA_TYPE = FLUID_TYPES.register("strong_yellow_tea_type", () -> new TeaFluidType(-5668031));
   public static final RegistryObject<FlowingFluid> STRONG_YELLOW_TEA = FLUIDS.register(
      "strong_yellow_tea", () -> new Source(FRFluids.STRONG_YELLOW_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_YELLOW_TEA = FLUIDS.register(
      "flowing_strong_yellow_tea", () -> new Flowing(FRFluids.STRONG_YELLOW_TEA_PROPERTIES)
   );
   public static final Properties STRONG_YELLOW_TEA_PROPERTIES = new Properties(STRONG_YELLOW_TEA_TYPE, STRONG_YELLOW_TEA, FLOWING_STRONG_YELLOW_TEA);
   public static final RegistryObject<FluidType> STRONG_BLACK_TEA_TYPE = FLUID_TYPES.register("strong_black_tea_type", () -> new TeaFluidType(-8962777));
   public static final RegistryObject<FlowingFluid> STRONG_BLACK_TEA = FLUIDS.register(
      "strong_black_tea", () -> new Source(FRFluids.STRONG_BLACK_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_BLACK_TEA = FLUIDS.register(
      "flowing_strong_black_tea", () -> new Flowing(FRFluids.STRONG_BLACK_TEA_PROPERTIES)
   );
   public static final Properties STRONG_BLACK_TEA_PROPERTIES = new Properties(STRONG_BLACK_TEA_TYPE, STRONG_BLACK_TEA, FLOWING_STRONG_BLACK_TEA);
   public static final RegistryObject<FluidType> STRONG_ROSE_HIP_TEA_TYPE = FLUID_TYPES.register("strong_rose_hip_tea_type", () -> new TeaFluidType(-4381399));
   public static final RegistryObject<FlowingFluid> STRONG_ROSE_HIP_TEA = FLUIDS.register(
      "strong_rose_hip_tea", () -> new Source(FRFluids.STRONG_ROSE_HIP_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_ROSE_HIP_TEA = FLUIDS.register(
      "flowing_strong_rose_hip_tea", () -> new Flowing(FRFluids.STRONG_ROSE_HIP_TEA_PROPERTIES)
   );
   public static final Properties STRONG_ROSE_HIP_TEA_PROPERTIES = new Properties(STRONG_ROSE_HIP_TEA_TYPE, STRONG_ROSE_HIP_TEA, FLOWING_STRONG_ROSE_HIP_TEA);
   public static final RegistryObject<FluidType> STRONG_PURULENT_TEA_TYPE = FLUID_TYPES.register("strong_purulent_tea_type", () -> new TeaFluidType(-8248779));
   public static final RegistryObject<FlowingFluid> STRONG_PURULENT_TEA = FLUIDS.register(
      "strong_purulent_tea", () -> new Source(FRFluids.STRONG_PURULENT_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_PURULENT_TEA = FLUIDS.register(
      "flowing_strong_purulent_tea", () -> new Flowing(FRFluids.STRONG_PURULENT_TEA_PROPERTIES)
   );
   public static final Properties STRONG_PURULENT_TEA_PROPERTIES = new Properties(STRONG_PURULENT_TEA_TYPE, STRONG_PURULENT_TEA, FLOWING_STRONG_PURULENT_TEA);
   public static final RegistryObject<FluidType> STRONG_GAMBLERS_TEA_TYPE = FLUID_TYPES.register("strong_gamblers_tea_type", () -> new TeaFluidType(-10992588));
   public static final RegistryObject<FlowingFluid> STRONG_GAMBLERS_TEA = FLUIDS.register(
      "strong_gamblers_tea", () -> new Source(FRFluids.STRONG_GAMBLERS_TEA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_GAMBLERS_TEA = FLUIDS.register(
      "flowing_strong_gamblers_tea", () -> new Flowing(FRFluids.STRONG_GAMBLERS_TEA_PROPERTIES)
   );
   public static final Properties STRONG_GAMBLERS_TEA_PROPERTIES = new Properties(STRONG_GAMBLERS_TEA_TYPE, STRONG_GAMBLERS_TEA, FLOWING_STRONG_GAMBLERS_TEA);
   public static final RegistryObject<FluidType> STRONG_COFFEE_TYPE = FLUID_TYPES.register("strong_coffee_type", () -> new TeaFluidType(-12047845));
   public static final RegistryObject<FlowingFluid> STRONG_COFFEE = FLUIDS.register("strong_coffee", () -> new Source(FRFluids.STRONG_COFFEE_PROPERTIES));
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_COFFEE = FLUIDS.register(
      "flowing_strong_coffee", () -> new Flowing(FRFluids.STRONG_COFFEE_PROPERTIES)
   );
   public static final Properties STRONG_COFFEE_PROPERTIES = new Properties(STRONG_COFFEE_TYPE, STRONG_COFFEE, FLOWING_STRONG_COFFEE);
   public static final RegistryObject<FluidType> STRONG_APPLE_CIDER_TYPE = FLUID_TYPES.register("strong_apple_cider_type", () -> new TeaFluidType(-3897274));
   public static final RegistryObject<FlowingFluid> STRONG_APPLE_CIDER = FLUIDS.register(
      "strong_apple_cider", () -> new Source(FRFluids.STRONG_APPLE_CIDER_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_APPLE_CIDER = FLUIDS.register(
      "flowing_strong_apple_cider", () -> new Flowing(FRFluids.STRONG_APPLE_CIDER_PROPERTIES)
   );
   public static final Properties STRONG_APPLE_CIDER_PROPERTIES = new Properties(STRONG_APPLE_CIDER_TYPE, STRONG_APPLE_CIDER, FLOWING_STRONG_APPLE_CIDER);
   public static final RegistryObject<FluidType> STRONG_MELON_JUICE_TYPE = FLUID_TYPES.register("strong_melon_juice_type", () -> new TeaFluidType(-2145741));
   public static final RegistryObject<FlowingFluid> STRONG_MELON_JUICE = FLUIDS.register(
      "strong_melon_juice", () -> new Source(FRFluids.STRONG_MELON_JUICE_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_MELON_JUICE = FLUIDS.register(
      "flowing_strong_melon_juice", () -> new Flowing(FRFluids.STRONG_MELON_JUICE_PROPERTIES)
   );
   public static final Properties STRONG_MELON_JUICE_PROPERTIES = new Properties(STRONG_MELON_JUICE_TYPE, STRONG_MELON_JUICE, FLOWING_STRONG_MELON_JUICE);
   public static final RegistryObject<FluidType> STRONG_HOT_COCOA_TYPE = FLUID_TYPES.register("strong_hot_cocoa_type", () -> new TeaFluidType(-7514822));
   public static final RegistryObject<FlowingFluid> STRONG_HOT_COCOA = FLUIDS.register(
      "strong_hot_cocoa", () -> new Source(FRFluids.STRONG_HOT_COCOA_PROPERTIES)
   );
   public static final RegistryObject<FlowingFluid> FLOWING_STRONG_HOT_COCOA = FLUIDS.register(
      "flowing_strong_hot_cocoa", () -> new Flowing(FRFluids.STRONG_HOT_COCOA_PROPERTIES)
   );
   public static final Properties STRONG_HOT_COCOA_PROPERTIES = new Properties(STRONG_HOT_COCOA_TYPE, STRONG_HOT_COCOA, FLOWING_STRONG_HOT_COCOA);
}
