package net.minecraft.pipboy.procedures;

import net.minecraft.world.level.LevelAccessor;

public class DaysCounterProcedure {
	public static String execute(LevelAccessor world) {
		return "Days: " + new java.text.DecimalFormat("0000").format(world.dayTime() / 24000d);
	}
}