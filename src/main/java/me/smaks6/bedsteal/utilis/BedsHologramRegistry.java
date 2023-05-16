package me.smaks6.bedsteal.utilis;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.smaks6.bedsteal.objects.BedCounterHologram;

import java.util.HashMap;

public final class BedsHologramRegistry {
    private static final HashMap<String, BedCounterHologram> bedsHolograms = new HashMap<>();
    private BedsHologramRegistry() {
    }

    private static String bedsHologramsName(ITeam team) {
        return team.getName()+team.getArena().getArenaName();
    }

    public static void register(ITeam team) {
        BedCounterHologram bedCounterHologram = new BedCounterHologram(team);
        bedCounterHologram.spawn();
        bedsHolograms.put(bedsHologramsName(team), bedCounterHologram);
    }

    public static void register(IArena arena) {
        for (ITeam team : arena.getTeams()) {
            if (!team.isBedDestroyed()) {
                register(team);
            }
        }
    }

    public static void unregister(IArena arena) {
        for (ITeam team : arena.getTeams()) {
            unregister(team);
        }
    }

    public static void unregister(ITeam team) {
        BedCounterHologram bedCounterHologram = bedsHolograms.get(bedsHologramsName(team));
        if(bedCounterHologram != null) {
            bedCounterHologram.destroy();
        }
        bedsHolograms.remove(bedsHologramsName(team));
    }

    public static void update(ITeam team) {
        bedsHolograms.get(bedsHologramsName(team)).update();
    }
}
