package me.smaks6.bedsteal.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import com.andrei1058.bedwars.api.events.gameplay.GameStateChangeEvent;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.utilis.BedsHologramRegistry;
import me.smaks6.bedsteal.utilis.BedsUtils;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import me.smaks6.bedsteal.utilis.EMobGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.*;

import static me.smaks6.bedsteal.utilis.EMobGenerator.*;

public class ArenaStartListener implements Listener {
    @EventHandler
    public void onArenaStart(GameStateChangeEvent event) {
        if(event.getNewState().equals(GameState.playing) && CanArenaUtil.get(event.getArena())) {
                Bukkit.getScheduler().runTaskTimer(BedSteal.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        Map<LivingEntity, IGenerator> customEntities2 = new HashMap<>(customEntities);
                        for (Map.Entry<LivingEntity, IGenerator> loc : customEntities2.entrySet()) {
                            if (loc.getKey().isDead()) {
                                customEntities.remove(loc.getKey(), loc.getValue());
                                ArrayList<Location> locNew = Sphere(loc.getValue().getLocation());
                                Random r = new Random();
                                createEntity(locNew.get(r.nextInt(locNew.size())), EntityType.valueOf(getConfigString("MobGenerator.type")), loc.getValue());
                            }
                        }
                    }
                }, 0, 20*4);
            BedsUtils.initArena(event.getArena());
            BedsHologramRegistry.register(event.getArena());
            EMobGenerator.init(event.getArena());
        }
    }
}
