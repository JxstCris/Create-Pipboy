package net.minecraft.pipboy.procedures;

import net.minecraft.world.entity.Entity;

public class Delpnt3Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putBoolean("player_p3_set", false);
	}
}