package net.minecraft.pipboy.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.pipboy.network.PipboyModVariables;

public class ConditionshowitemProcedure {
	public static boolean execute(Entity entity) {
		if (entity == null)
			return false;
		return entity.getData(PipboyModVariables.PLAYER_VARIABLES).pipboy_tab == 0;
	}
}