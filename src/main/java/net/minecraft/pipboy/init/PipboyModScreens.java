/*
 *	MCreator note: This file will be REGENERATED on each build.
 */
package net.minecraft.pipboy.init;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.api.distmarker.Dist;

import net.minecraft.pipboy.client.gui.GuipipboymainScreen;

@EventBusSubscriber(Dist.CLIENT)
public class PipboyModScreens {
	@SubscribeEvent
	public static void clientLoad(RegisterMenuScreensEvent event) {
		event.register(PipboyModMenus.GUIPIPBOYMAIN.get(), GuipipboymainScreen::new);
	}

	public interface ScreenAccessor {
		void updateMenuState(int elementType, String name, Object elementState);
	}
}