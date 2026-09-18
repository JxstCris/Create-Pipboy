
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.minecraft.pipboy.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.pipboy.PipboyMod;

public class PipboyModSounds {
	public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, PipboyMod.MODID);
	public static final RegistryObject<SoundEvent> STATIC_01 = REGISTRY.register("static_01", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("pipboy", "static_01")));
	public static final RegistryObject<SoundEvent> STATIC_02 = REGISTRY.register("static_02", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("pipboy", "static_02")));
	public static final RegistryObject<SoundEvent> STATIC_03 = REGISTRY.register("static_03", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("pipboy", "static_03")));
	public static final RegistryObject<SoundEvent> BUTTON_SELECT = REGISTRY.register("button_select", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("pipboy", "button_select")));
}
