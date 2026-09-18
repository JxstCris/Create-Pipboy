package net.minecraft.pipboy.procedures;

import net.minecraft.world.entity.Entity;

public class Savpnt3Procedure {
	public static void execute(double x, double z, Entity entity) {
		if (entity == null)
			return;
		entity.getPersistentData().putDouble("radar_p3_x", Math.round(x));
		entity.getPersistentData().putDouble("radar_p3_z", Math.round(z));
		entity.getPersistentData().putBoolean("player_p3_set", true);
	}
}