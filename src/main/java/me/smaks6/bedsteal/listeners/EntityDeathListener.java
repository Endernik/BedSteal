package me.smaks6.bedsteal.listeners;

import com.andrei1058.bedwars.api.arena.GameState;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

import static me.smaks6.bedsteal.utilis.EMobGenerator.*;

public class EntityDeathListener implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (customEntities.containsKey(event.getEntity())) {
            if (customEntities.get(event.getEntity()).getArena().getStatus() != GameState.playing) return;
            if (CanArenaUtil.get(customEntities.get(event.getEntity()).getArena())) {
                event.getDrops().clear();
                event.setDroppedExp(0);
                if (event.getEntity().getKiller() != null && event.getEntity().getKiller() instanceof Player) {
                    ConfigurationSection section = BedSteal.getInstance().getConfig().getConfigurationSection("MobGenerator.death-commands");
                    Random random = new Random();
                    int randomNumber = random.nextInt(100) + 1;
                    for (String key : section.getKeys(false)) {
                        if (randomNumber <= section.getInt(key + ".possibility")) {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), section.getString(key + ".command").replace("{player}", event.getEntity().getKiller().getName()));
                        }
                    }
                }
            }
        }
    }
}
