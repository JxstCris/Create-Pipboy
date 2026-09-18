package net.minecraft.pipboy.procedures;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class DaysCounterProcedure {

    public static String execute(LevelAccessor world) {

        long days = 0;

        if (world instanceof Level level) {
            days = level.getDayTime() / 24000L;
        }

        return "Days: " + String.format("%04d", days);

    }

}