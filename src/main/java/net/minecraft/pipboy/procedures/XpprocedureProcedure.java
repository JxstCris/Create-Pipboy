package net.minecraft.pipboy.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class XpprocedureProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return "" + (entity instanceof Player _plr ? _plr.experienceLevel : 0);
	}
}
