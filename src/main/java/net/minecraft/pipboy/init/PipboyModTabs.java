
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.minecraft.pipboy.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.pipboy.PipboyMod;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class PipboyModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PipboyMod.MODID);
	public static final RegistryObject<CreativeModeTab> PIPBOY_TAB = REGISTRY.register("pipboy_tab",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.pipboy.pipboy_tab")).icon(() -> new ItemStack(PipboyModItems.PIPBOY_3000.get())).displayItems((parameters, tabData) -> {
				tabData.accept(PipboyModItems.PIPBOY_3000.get());
				tabData.accept(PipboyModItems.REDSTONE_CORE.get());
				tabData.accept(PipboyModItems.ENERGYZED_REDSTONECORE.get());
				tabData.accept(PipboyModItems.PIPBOYCASE.get());
				tabData.accept(PipboyModItems.UNFINISHEDPIPBOY.get());
				tabData.accept(PipboyModItems.STORAGECOMPONENT.get());
			})

					.build());
}
