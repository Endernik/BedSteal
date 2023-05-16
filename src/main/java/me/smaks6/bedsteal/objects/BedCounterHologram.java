package me.smaks6.bedsteal.objects;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.utilis.*;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

public final class BedCounterHologram {
    private static final int HOLOGRAM_MOVE_X = ConfigUtils.getInt(Config.BedHologramXMove);
    private static final int HOLOGRAM_MOVE_Y = ConfigUtils.getInt(Config.BedHologramYMove);
    private static final int HOLOGRAM_MOVE_Z = ConfigUtils.getInt(Config.BedHologramZMove);
    private final Location bedLocation;
    private final ITeam team;
    private ArmorStand armorStand;

    public BedCounterHologram(ITeam team) {
        this.bedLocation = team.getBed();
        this.team = team;
    }

    public void spawn() {
        armorStand = (ArmorStand) bedLocation.getWorld().spawnEntity(
                bedLocation.getBlock().getLocation().add(+0.5, 1, +0.5),
                EntityType.ARMOR_STAND
        ); //.clone().add(0, 1, 0)
        armorStand.setVisible(false);
        armorStand.setVisible(false);
        armorStand.setGravity(false);
        armorStand.setCustomName(generateText());
        armorStand.setCustomNameVisible(true);
        armorStand.setMarker(true);
    }

    public void update() {
        armorStand.setCustomName(generateText());
    }

    public void destroy() {
        armorStand.remove();
    }

    private String generateText() {
        int teamBeds = BedsUtils.getTeamBeds(team);
        String message = TextUtil.getColoredString(BedSteal.bedWarsAPI.getDefaultLang().getYml().getString(".addons.bedsteal.BedsHologramText"));
        return message.replaceAll("\\{beds}", String.valueOf(teamBeds));
    }


}
