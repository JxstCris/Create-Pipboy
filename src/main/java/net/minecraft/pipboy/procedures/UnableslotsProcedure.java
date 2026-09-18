package net.minecraft.pipboy.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.pipboy.network.PipboyModVariables;

public class UnableslotsProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return (entity.getCapability(PipboyModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new PipboyModVariables.PlayerVariables())).pipboy_tab != 0;
	}
}
