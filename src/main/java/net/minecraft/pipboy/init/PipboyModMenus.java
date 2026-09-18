
/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.minecraft.pipboy.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.common.extensions.IForgeMenuType;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.pipboy.world.inventory.GuipipboymainMenu;
import net.minecraft.pipboy.PipboyMod;

public class PipboyModMenus {
	public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, PipboyMod.MODID);
	public static final RegistryObject<MenuType<GuipipboymainMenu>> GUIPIPBOYMAIN = REGISTRY.register("guipipboymain", () -> IForgeMenuType.create(GuipipboymainMenu::new));
}
