package me.smaks6.bedsteal.utilis;

import com.andrei1058.bedwars.api.arena.IArena;
import me.smaks6.bedsteal.BedSteal;
import org.bukkit.World;

public class CanArenaUtil {

    public static boolean get(IArena arena) {
        for (String s :BedSteal.getInstance().getConfig().getStringList("EnabledArenas")) {
            if (arena.getWorldName().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
