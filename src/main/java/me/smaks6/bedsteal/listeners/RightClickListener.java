package me.smaks6.bedsteal.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.items.CustomItem;
import me.smaks6.bedsteal.items.CustomItemRegistry;
import me.smaks6.bedsteal.items.UsableCustomItem;
import me.smaks6.bedsteal.items.impl.VictimBed;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Optional;

public class RightClickListener implements Listener {
    @EventHandler
    public void rightClick(PlayerInteractEvent event){
        if (BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(event.getPlayer()) != null && BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(event.getPlayer()).getStatus() == GameState.playing && CanArenaUtil.get(BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(event.getPlayer()))) {
        Player player = event.getPlayer();

        if(CustomItemRegistry.VICTIM_BED.victimBedToBeds(player.getItemInHand(), player) != 0){
            if(event.getClickedBlock() == null){
                CustomItemRegistry.VICTIM_BED.onUse(player, player.getLocation());
                return;
            }
            CustomItemRegistry.VICTIM_BED.onUse(player, event.getClickedBlock().getLocation());
            return;
        }

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            Optional<CustomItem> item = CustomItemRegistry.getItem(event.getItem());
            if (item.isPresent() && item.get() instanceof UsableCustomItem) {
                ((UsableCustomItem) item.get()).use(player);
            }
        }
        }
    }
}
