
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.minecraft.pipboy.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.world.item.Item;
import net.minecraft.pipboy.item.UnfinishedpipboyItem;
import net.minecraft.pipboy.item.StoragecomponentItem;
import net.minecraft.pipboy.item.RedstoneCoreItem;
import net.minecraft.pipboy.item.PipboycaseItem;
import net.minecraft.pipboy.item.Pipboy3000Item;
import net.minecraft.pipboy.item.EnergyzedRedstoneCoreItem;
import net.minecraft.pipboy.PipboyMod;

public class PipboyModItems {
	public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, PipboyMod.MODID);
	public static final RegistryObject<Item> PIPBOY_3000 = REGISTRY.register("pipboy_3000", () -> new Pipboy3000Item());
	public static final RegistryObject<Item> REDSTONE_CORE = REGISTRY.register("redstone_core", () -> new RedstoneCoreItem());
	public static final RegistryObject<Item> ENERGYZED_REDSTONECORE = REGISTRY.register("energyzed_redstonecore", () -> new EnergyzedRedstoneCoreItem());
	public static final RegistryObject<Item> PIPBOYCASE = REGISTRY.register("pipboycase", () -> new PipboycaseItem());
	public static final RegistryObject<Item> UNFINISHEDPIPBOY = REGISTRY.register("unfinishedpipboy", () -> new UnfinishedpipboyItem());
	public static final RegistryObject<Item> STORAGECOMPONENT = REGISTRY.register("storagecomponent", () -> new StoragecomponentItem());
}
