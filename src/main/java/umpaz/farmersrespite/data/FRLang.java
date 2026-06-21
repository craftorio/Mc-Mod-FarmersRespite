package umpaz.farmersrespite.data;

import java.util.HashSet;
import java.util.Set;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.registry.FRBlocks;
import umpaz.farmersrespite.common.registry.FRFluids;
import umpaz.farmersrespite.common.registry.FRItems;

public class FRLang extends LanguageProvider {
   public FRLang(PackOutput output) {
      super(output, "farmersrespite", "en_us");
   }

   protected void addTranslations() {
      Set<RegistryObject<Block>> blocks = new HashSet<>(FRBlocks.BLOCKS.getEntries());
      Set<RegistryObject<Item>> items = new HashSet<>(FRItems.ITEMS.getEntries());
      blocks.remove(FRBlocks.SMALL_TEA_BUSH);
      blocks.remove(FRBlocks.COFFEE_BUSH);
      blocks.forEach(b -> {
         String name = ((Block)b.get()).getDescriptionId().replaceFirst("block.farmersrespite.", "");
         name = toTitleCase(this.correctBlockItemName(name), "_").replaceAll("Of", "of");
         this.add(((Block)b.get()).getDescriptionId(), name);
      });
      items.removeIf(i -> i.get() instanceof BlockItem);
      items.remove(FRItems.COFFEE_CAKE_SLICE);
      items.remove(FRItems.GAMBLERS_TEA);
      items.remove(FRItems.LONG_GAMBLERS_TEA);
      items.remove(FRItems.STRONG_GAMBLERS_TEA);
      items.forEach(i -> {
         String name = ((Item)i.get()).getDescriptionId().replaceFirst("item.farmersrespite.", "");
         name = toTitleCase(this.correctBlockItemName(name), "_").replaceAll("Of", "of");
         this.add(((Item)i.get()).getDescriptionId(), name);
      });
      this.add("farmersrespite.itemGroup.main", "Farmer's Respite");
      this.add("farmersdelight.tooltip.purulent_tea", "Extends 1 effect by 15 seconds");
      this.add("farmersdelight.tooltip.long_purulent_tea", "Extends 1 effect by 15 seconds");
      this.add("farmersdelight.tooltip.strong_purulent_tea", "Extends 1 effect by 30 seconds");
      this.add("farmersdelight.tooltip.strong_melon_juice", "Instant Health");
      this.add("farmersdelight.tooltip.strong_hot_cocoa", "Clears 2 Harmful Effects");
      this.add("farmersdelight.tooltip.gamblers_tea", "50% chance of glowing or wither");
      this.add("farmersdelight.tooltip.long_gamblers_tea", "50% chance of glowing or wither");
      this.add("farmersdelight.tooltip.strong_gamblers_tea", "50% chance of glowing and night vision or wither");
      this.add("item.farmersrespite.gamblers_tea", "Gambler's Tea");
      this.add("item.farmersrespite.long_gamblers_tea", "Gambler's Tea");
      this.add("item.farmersrespite.strong_gamblers_tea", "Gambler's Tea");
      this.add("item.farmersrespite.coffee_cake_slice", "Slice of Coffee Cake");
      this.add("block.farmersrespite.small_tea_bush", "Tea Seeds");
      this.add("block.farmersrespite.coffee_bush", "Coffee Beans");
      this.add("farmersrespite.container.kettle", "Kettle");
      this.add("farmersrespite.container.kettle.no_water", "No Water");
      this.add("farmersrespite.container.kettle.has_single_water", "Contains 1 glass of water");
      this.add("farmersrespite.container.kettle.has_many_water", "Contains %s glasses of water");
      this.add("farmersrespite.container.recipe_book.brewable", "Showing Brewable");
      this.add("farmersrespite.subtitles.kettle.whistle", "Kettle whistles");
      this.add("effect.farmersrespite.caffeinated", "Caffeinated");
      this.add("farmersrespite.jei.brewing", "Brewing");
      this.add("death.attack.farmersrespite.wither_root", "%1$s wasn't careful while gardening");
      this.add("death.attack.farmersrespite.wither_root.player", "%2$s withered away due to %1$s's careless gardening");
      this.add("farmersrespite.advancement.get_tea_seeds", "Handle With Care");
      this.add("farmersrespite.advancement.get_coffee_beans", "Invasive Species");
      this.add("farmersrespite.advancement.stunt_tea_bush", "Tasty Topiary");
      this.add(
         "farmersrespite.advancement.get_tea_seeds.desc", "Tea bushes oxidize with time! Plant them on grass and keep an eye on them to time your harvests!"
      );
      this.add(
         "farmersrespite.advancement.get_coffee_beans.desc",
         "Coffee doesn't grow well in the overworld! Plant it near some more typical crops so it can feed on them and grow."
      );
      this.add("farmersrespite.advancement.stunt_tea_bush.desc", "Stunt a tea bushes' growth with an axe to keep it safe for decoration!");
      Set<RegistryObject<FluidType>> fluidTypes = new HashSet<>(FRFluids.FLUID_TYPES.getEntries());
      fluidTypes.remove(FRFluids.GAMBLERS_TEA_TYPE);
      fluidTypes.remove(FRFluids.LONG_GAMBLERS_TEA_TYPE);
      fluidTypes.remove(FRFluids.STRONG_GAMBLERS_TEA_TYPE);
      fluidTypes.forEach(f -> {
         String name = ((FluidType)f.get()).getDescriptionId().toString().replaceFirst("fluid_type.farmersrespite.", "").replaceFirst("_type", "");
         name = toTitleCase(this.correctBlockItemName(name), "_").replaceAll("Of", "of");
         this.add(((FluidType)f.get()).getDescriptionId().toString(), name);
      });
      this.add("fluid_type.farmersrespite.gamblers_tea_type", "Gambler's Tea");
      this.add("fluid_type.farmersrespite.long_gamblers_tea_type", "Gambler's Tea");
      this.add("fluid_type.farmersrespite.strong_gamblers_tea_type", "Gambler's Tea");
      this.add("generic.unit.millibuckets", "mB");
   }

   public String getName() {
      return "Lang Entries";
   }

   public static String toTitleCase(String givenString, String regex) {
      String[] stringArray = givenString.split(regex);
      StringBuilder stringBuilder = new StringBuilder();

      for (String string : stringArray) {
         stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
      }

      return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
   }

   public String correctBlockItemName(String name) {
      if (!name.endsWith("_bricks") && name.contains("bricks")) {
         name = name.replaceFirst("bricks", "brick");
      }

      if ((name.contains("_fence") || name.contains("_button")) && name.contains("planks")) {
         name = name.replaceFirst("_planks", "");
      }

      if (name.startsWith("long_")) {
         name = name.replaceFirst("long_", "");
      }

      if (name.startsWith("strong_")) {
         name = name.replaceFirst("strong_", "");
      }

      return name;
   }
}
