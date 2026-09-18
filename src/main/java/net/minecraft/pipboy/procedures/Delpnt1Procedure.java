package net.minecraft.pipboy.procedures;

import net.minecraft.world.entity.Entity;

public class Delpnt1Procedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putBoolean("player_p1_set", false);
	}
}