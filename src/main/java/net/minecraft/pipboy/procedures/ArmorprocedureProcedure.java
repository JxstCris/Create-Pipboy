package net.minecraft.pipboy.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

public class ArmorprocedureProcedure {
	public static String execute(Entity entity) {
		if (entity == null)
			return "";
		return "" + (entity instanceof LivingEntity _livEnt ? _livEnt.getArmorValue() : 0);
	}
}
