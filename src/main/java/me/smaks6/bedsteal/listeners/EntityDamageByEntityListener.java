package me.smaks6.bedsteal.listeners;

import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.items.CustomItem;
import me.smaks6.bedsteal.items.CustomItemRegistry;
import me.smaks6.bedsteal.items.HitableCustomItem;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.Optional;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if(event.isCancelled()) return;

        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player && CanArenaUtil.get(BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer((Player) event.getEntity()))) {
            Player damager = (Player) event.getDamager();
            Optional<CustomItem> item = CustomItemRegistry.getItem(damager.getItemInHand());
            if(item.isPresent() && item.get() instanceof HitableCustomItem) {
                ((HitableCustomItem) item.get()).hit(damager, (Player) event.getEntity());
            }
        }
    }
}
