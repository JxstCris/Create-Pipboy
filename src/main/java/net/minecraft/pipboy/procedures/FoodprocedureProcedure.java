package net.minecraft.pipboy.procedures;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

public class FoodprocedureProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return (entity instanceof Player _plr ? _plr.getFoodData().getFoodLevel() : 0) + "/20";
	}
}
