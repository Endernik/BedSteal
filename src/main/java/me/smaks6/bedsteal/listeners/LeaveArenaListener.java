package me.smaks6.bedsteal.listeners;

import com.andrei1058.bedwars.api.events.player.PlayerLeaveArenaEvent;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LeaveArenaListener implements Listener {
    @EventHandler
    public void leaveArena(PlayerLeaveArenaEvent event) {
        if (CanArenaUtil.get(event.getArena())) {
            event.getPlayer().setMaxHealth(20);
        }
    }
}
