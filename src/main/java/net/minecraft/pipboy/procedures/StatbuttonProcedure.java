package net.minecraft.pipboy.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.pipboy.network.PipboyModVariables;

public class StatbuttonProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			double _setval = 1;
			entity.getCapability(PipboyModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.pipboy_tab = _setval;
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
