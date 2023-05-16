package me.smaks6.bedsteal.utilis;

import org.bukkit.Location;

public final class LocationUtils {
    private LocationUtils() {
    }

    //https://bukkit.org/threads/get-the-centre-of-a-block.302039/
    public static Location getCenter(Location loc) {
//        System.out.println(loc);
        return new Location(loc.getWorld(),
                getRelativeCord(loc.getBlockX()),
                getRelativeCord(loc.getBlockY()),
                getRelativeCord(loc.getBlockZ()));
    }

    private static double getRelativeCord(int i) {
        double d = i;
        d = d < 0 ? d - .5 : d + .5;
        return d;
    }
}
