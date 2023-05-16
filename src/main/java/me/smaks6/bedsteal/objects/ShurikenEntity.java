package me.smaks6.bedsteal.objects;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.utilis.Config;
import me.smaks6.bedsteal.utilis.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.List;

import static me.smaks6.bedsteal.BedSteal.bedWarsAPI;
import static me.smaks6.bedsteal.BedSteal.getInstance;

public class ShurikenEntity {
    private static final ItemStack ITEM_ON_HEAD = new ItemStack(Material.valueOf(ConfigUtils.get(Config.ShurikenItem)));
    private ArmorStand shuriken;
    private ITeam shurikenTeam;
    private IArena shurikenArena;


    public ShurikenEntity(Player player) {
        shurikenArena = bedWarsAPI.getArenaUtil().getArenaByPlayer(player);
        if(shurikenArena == null) {
            player.sendMessage("You can use it only during gameplay!");
            return;
        }
        Location playerLocation = player.getLocation();
        shuriken = playerLocation.getWorld().spawn(player.getLocation().subtract(0, 1, 0), ArmorStand.class);
        shuriken.setRightArmPose(new EulerAngle(BedSteal.getInstance().getConfig().getDouble("ShurikenItemValues.value-x"), BedSteal.getInstance().getConfig().getDouble("ShurikenItemValues.value-y"), BedSteal.getInstance().getConfig().getDouble("ShurikenItemValues.value-z")));
        shuriken.setGravity(false);
        shuriken.setItemInHand(ITEM_ON_HEAD);
        shuriken.setArms(true);
        shuriken.setVisible(BedSteal.getInstance().getConfig().getBoolean("ShurikenItemValues.visible"));
        shurikenTeam = shurikenArena.getTeam(player);
    }

    private void move(double distance) {
        Location loc = shuriken.getLocation();
        Vector dir = loc.getDirection();
        dir.normalize();
        dir.multiply(distance);
        loc.add(dir);
        shuriken.teleport(loc);
    }

    public void moveRunnable() {
        if(shurikenArena == null) return;
        new BukkitRunnable() {

            @Override
            public void run() {
                if (shuriken.isDead()) cancel();
                remove();
            }
        }.runTaskLater(BedSteal.getInstance(), 2400L);

        new BukkitRunnable() {

            @Override
            public void run() {
                List<Entity> nearbyEntities = shuriken.getNearbyEntities(BedSteal.getInstance().getConfig().getDouble("ShurikenHitBoxRadius.hitbox-x"), BedSteal.getInstance().getConfig().getDouble("ShurikenHitBoxRadius.hitbox-y"), BedSteal.getInstance().getConfig().getDouble("ShurikenHitBoxRadius.hitbox-z"));
                for (Entity nearbyEntity : nearbyEntities) {
                    if(nearbyEntity instanceof  Player) {
                        Player nearbyPlayer = (Player) nearbyEntity;
                        if(!shurikenTeam.isMember(nearbyPlayer) && shurikenArena.isPlayer(nearbyPlayer)){
                            nearbyPlayer.damage(BedSteal.getInstance().getConfig().getInt("ShurikenDamage"));
                            Vector knockbackVector = nearbyPlayer.getLocation().getDirection().multiply(-BedSteal.getInstance().getConfig().getDouble("ShurikenKnockback.knockback-distance")*BedSteal.getInstance().getConfig().getDouble("ShurikenKnockback.knockback-strength")).setY(BedSteal.getInstance().getConfig().getDouble("ShurikenKnockback.knockback-y"));
                            nearbyPlayer.setVelocity(knockbackVector);
                            remove();
                            cancel();
                        }
                    }
                }
                move(0.7);
                if(!shuriken.getLocation().add(0, 2, 0).getBlock().getType().isTransparent()) {
                    remove();
                    cancel();
                }
            }
        }.runTaskTimer(BedSteal.getInstance(), 0L, 1L);
    }

    public void remove() {
        shuriken.remove();
    }

}
