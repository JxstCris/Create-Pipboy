/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.minecraft.pipboy.init;

import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.pipboy.PipboyMod;
import net.minecraft.core.registries.Registries;

public class PipboyModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, PipboyMod.MODID);
	public static final DeferredHolder<SoundEvent, SoundEvent> STIMPAK_USE = REGISTRY.register("stimpak_use", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("pipboy", "stimpak_use")));
	public static final DeferredHolder<SoundEvent, SoundEvent> STATIC_01 = REGISTRY.register("static_01", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("pipboy", "static_01")));
	public static final DeferredHolder<SoundEvent, SoundEvent> STATIC_02 = REGISTRY.register("static_02", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("pipboy", "static_02")));
	public static final DeferredHolder<SoundEvent, SoundEvent> STATIC_03 = REGISTRY.register("static_03", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("pipboy", "static_03")));
	public static final DeferredHolder<SoundEvent, SoundEvent> BUTTON_SELECT = REGISTRY.register("button_select", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("pipboy", "button_select")));
}