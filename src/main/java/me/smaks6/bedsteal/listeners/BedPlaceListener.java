package me.smaks6.bedsteal.listeners;

import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.utilis.BedCaseUtils;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BedPlaceListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {
if (CanArenaUtil.get(BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(event.getPlayer()))) {
    if (event.getBlock().getType().equals(Material.BED)
            || event.getBlock().getType().equals(Material.BED_BLOCK)
            || event.getBlock().getType().equals(Material.REDSTONE_WIRE)
            || BedCaseUtils.isNextToBed(event.getBlock().getLocation())
            && BedSteal.bedWarsAPI.getArenaUtil().isPlaying(event.getPlayer())) {
        event.setCancelled(true);
    }
}
    }
}
