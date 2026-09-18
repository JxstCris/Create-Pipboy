/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.minecraft.pipboy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;

import net.minecraft.world.item.Item;
import net.minecraft.pipboy.item.inventory.Pipboy3000InventoryCapability;
import net.minecraft.pipboy.item.*;
import net.minecraft.pipboy.PipboyMod;

@EventBusSubscriber
public class PipboyModItems {
	public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(PipboyMod.MODID);
	public static final DeferredItem<Item> PIPBOY_3000;
	public static final DeferredItem<Item> REDSTONE_CORE;
	public static final DeferredItem<Item> ENERGYZED_REDSTONE_CORE;
	public static final DeferredItem<Item> PIPBOYCASE;
	public static final DeferredItem<Item> UNFINISHEDPIPBOY;
	public static final DeferredItem<Item> STORAGE_COMPONENT;
	static {
		PIPBOY_3000 = REGISTRY.register("pipboy_3000", Pipboy3000Item::new);
		REDSTONE_CORE = REGISTRY.register("redstone_core", RedstoneCoreItem::new);
		ENERGYZED_REDSTONE_CORE = REGISTRY.register("energyzed_redstone_core", EnergyzedRedsoneCoreItem::new);
		PIPBOYCASE = REGISTRY.register("pipboycase", PipboycaseItem::new);
		UNFINISHEDPIPBOY = REGISTRY.register("unfinishedpipboy", UnfinishedpipboyItem::new);
		STORAGE_COMPONENT = REGISTRY.register("storage_component", StoragecomponentItem::new);
	}

	// Start of user code block custom items
	// End of user code block custom items
	@SubscribeEvent
	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerItem(Capabilities.ItemHandler.ITEM, (stack, context) -> new Pipboy3000InventoryCapability(stack), PIPBOY_3000.get());
	}
}