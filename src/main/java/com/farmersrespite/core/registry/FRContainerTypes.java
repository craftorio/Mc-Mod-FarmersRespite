package com.farmersrespite.core.registry;

import com.farmersrespite.common.block.entity.container.KettleContainer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FRContainerTypes {
    public static final DeferredRegister<MenuType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, "farmersrespite");

    public static final RegistryObject<MenuType<KettleContainer>> KETTLE = CONTAINER_TYPES
            .register("kettle", () -> IForgeMenuType.create(KettleContainer::new));
}
