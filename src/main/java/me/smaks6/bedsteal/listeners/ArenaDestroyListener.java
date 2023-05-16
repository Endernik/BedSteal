package me.smaks6.bedsteal.listeners;

import com.andrei1058.bedwars.api.events.gameplay.GameEndEvent;
import me.smaks6.bedsteal.utilis.BedsHologramRegistry;
import me.smaks6.bedsteal.utilis.BedsUtils;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArenaDestroyListener implements Listener {
    @EventHandler
    public void onArenaDestroy(GameEndEvent event) {
        if (CanArenaUtil.get(event.getArena())) {
            BedsHologramRegistry.unregister(event.getArena());
            BedsUtils.destroyArena(event.getArena());
        }
    }
}
