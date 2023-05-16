package me.smaks6.bedsteal.utilis;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.HashMap;

public final class BedsUtils {
    private BedsUtils() {
    }

    private static final HashMap<String, Integer> bedsCounter = new HashMap<>();

    private static String bedsCounterName(ITeam team) {
        return team.getName()+team.getArena().getArenaName();
    }

    public static void initArena(IArena arena) {
        for (ITeam team : arena.getTeams()) {
            bedsCounter.put(bedsCounterName(team), 1);
            BedCaseUtils.buildBedCaseFromLevel(1, team);
        }
    }

    public static int getTeamBeds(ITeam team) {
        return bedsCounter.get(bedsCounterName(team));
    }

    public static void destroyArena(IArena arena) {
        for (ITeam team : arena.getTeams()) {
            bedsCounter.remove(bedsCounterName(team));
        }
    }
    public static void setTeamBeds(ITeam team, int value) {
        BedCaseUtils.buildBedCaseFromLevel(value, team);
        bedsCounter.replace(bedsCounterName(team), value);
        for (Player member : team.getMembers()) {
            int health = 20+(2*(value-1 < 0 ? 0 : value-1));
            member.setMaxHealth(health);
            member.setHealthScaled(false);
            member.setHealth(health);
        }
        BedsHologramRegistry.update(team);
    }

    public static void stealBed(ITeam team) {
        setTeamBeds(team, getTeamBeds(team)-1);
        BedsHologramRegistry.update(team);
    }

    public static void addBed(ITeam team){
        addBeds(team, 1);
    }

    public static void addBeds(ITeam team, int beds) {
        setTeamBeds(team, getTeamBeds(team)+beds);
    }
}
