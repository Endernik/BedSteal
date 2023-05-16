package me.smaks6.bedsteal.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerReJoinEvent;
import me.smaks6.bedsteal.utilis.BedsUtils;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReJoinArenaPlayerListener implements Listener {

    @EventHandler
    public void onPlayerRejoinArena(PlayerReJoinEvent event) {
        if (CanArenaUtil.get(event.getArena())) {
            Player player = event.getPlayer();
            int bedsValue = BedsUtils.getTeamBeds(event.getArena().getTeam(player));
            player.setMaxHealth(20 + (bedsValue * 2));
        }
    }

}
