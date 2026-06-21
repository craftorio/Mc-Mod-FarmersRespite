package umpaz.farmersrespite.common.registry;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import umpaz.farmersrespite.common.block.entity.container.KettleMenu;

public class FRMenuTypes {
   public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "farmersrespite");
   public static final RegistryObject<MenuType<KettleMenu>> KETTLE = MENU_TYPES.register("kettle", () -> IForgeMenuType.create(KettleMenu::new));
}
