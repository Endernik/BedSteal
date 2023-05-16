package me.smaks6.bedsteal.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerReSpawnEvent;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnPlayerListener implements Listener {
    @EventHandler
    public void onRespawn(PlayerReSpawnEvent event) {
        if (CanArenaUtil.get(event.getArena())) {
            Player player = event.getPlayer();
            player.setHealth(player.getMaxHealth());
        }
    }
}
