/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.minecraft.pipboy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.pipboy.PipboyMod;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

public class PipboyModTabs {
	public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, PipboyMod.MODID);
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PIPBOY = REGISTRY.register("pipboy",
			() -> CreativeModeTab.builder().title(Component.translatable("item_group.pipboy.pipboy")).icon(() -> new ItemStack(PipboyModItems.PIPBOY_3000.get())).displayItems((parameters, tabData) -> {
				tabData.accept(PipboyModItems.PIPBOYCASE.get());
				tabData.accept(PipboyModItems.PIPBOY_3000.get());
				tabData.accept(PipboyModItems.REDSTONE_CORE.get());
				tabData.accept(PipboyModItems.STORAGE_COMPONENT.get());
				tabData.accept(PipboyModItems.ENERGYZED_REDSTONE_CORE.get());
			}).build());
}