package me.smaks6.bedsteal.utilis;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.generator.GeneratorType;
import com.andrei1058.bedwars.api.arena.generator.IGenerator;
import me.smaks6.bedsteal.BedSteal;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static me.smaks6.bedsteal.utilis.RadiusUtil.sphereAround;

public class EMobGenerator {
    private static int limit = BedSteal.getInstance().getConfig().getInt("MobGenerator.settings.limit");

    public static final HashMap<LivingEntity, IGenerator> customEntities = new HashMap<>();

    public static void init(IArena arena) {
        for (IGenerator gen : arena.getOreGenerators()) {
            if (gen.getType() == GeneratorType.EMERALD) {
                for (int i = 0; i < limit; i++) {
                    ArrayList<Location> loc = Sphere(gen.getLocation());
                    Random r = new Random();
                    createEntity(loc.get(r.nextInt(loc.size())), EntityType.valueOf(getConfigString("MobGenerator.type")), gen);
                }
            }
        }
    }

    public static void createEntity(Location location, EntityType entityType, IGenerator gen) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, entityType);
        entity.setCanPickupItems(false);
        entity.setMaxHealth(BedSteal.getInstance().getConfig().getDouble("MobGenerator.health"));
        EntityEquipment equipment = entity.getEquipment();
        if(entity instanceof Ageable) {
            Ageable castedEntity = (Ageable) entity;
            castedEntity.setAdult();
        }
        entity.setCustomName(TextUtil.getColoredString(BedSteal.bedWarsAPI.getDefaultLang().getString(".addons.bedsteal.MobGenerator-Name")));
        entity.setCustomNameVisible(true);
        // helmet
        ItemBuilder helmet = new ItemBuilder(Material.valueOf(getConfigString("MobGenerator.equipments.armor-helmet.material")));
        for (String enchant : BedSteal.getInstance().getConfig().getStringList("MobGenerator.equipments.armor-helmet.enchantments")) {
            if (Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")) == null) {
                BedSteal.getInstance().getLogger().severe("Invalid enchantment");
                break;
            }
            helmet.addEnchant(Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")), Integer.parseInt(StringUtils.substringAfterLast(enchant, "_")));
        }

        // chestplate
        ItemBuilder chestplate = new ItemBuilder(Material.valueOf(getConfigString("MobGenerator.equipments.armor-chestplate.material")));
        for (String enchant : BedSteal.getInstance().getConfig().getStringList("MobGenerator.equipments.armor-chestplate.enchantments")) {
            if (Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")) == null) {
                BedSteal.getInstance().getLogger().severe("Invalid enchantment");
                break;
            }
            chestplate.addEnchant(Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")), Integer.parseInt(StringUtils.substringAfterLast(enchant, "_")));
        }

        // leggings
        ItemBuilder leggings = new ItemBuilder(Material.valueOf(getConfigString("MobGenerator.equipments.armor-leggings.material")));
        for (String enchant : BedSteal.getInstance().getConfig().getStringList("MobGenerator.equipments.armor-leggings.enchantments")) {
            if (Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")) == null) {
                BedSteal.getInstance().getLogger().severe("Invalid enchantment");
                break;
            }
            leggings.addEnchant(Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")), Integer.parseInt(StringUtils.substringAfterLast(enchant, "_")));
        }

        // boots
        ItemBuilder boots = new ItemBuilder(Material.valueOf(getConfigString("MobGenerator.equipments.armor-boots.material")));
        for (String enchant : BedSteal.getInstance().getConfig().getStringList("MobGenerator.equipments.armor-boots.enchantments")) {
            if (Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")) == null) {
                BedSteal.getInstance().getLogger().severe("Invalid enchantment");
                break;
            }
            boots.addEnchant(Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")), Integer.parseInt(StringUtils.substringAfterLast(enchant, "_")));
        }

        // held-item
        ItemBuilder heldItem = new ItemBuilder(Material.valueOf(getConfigString("MobGenerator.equipments.held-item.material")));
        for (String enchant : BedSteal.getInstance().getConfig().getStringList("MobGenerator.equipments.held-item.enchantments")) {
            if (Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")) == null) {
                BedSteal.getInstance().getLogger().severe("Invalid enchantment");
                break;
            }
            heldItem.addEnchant(Enchantment.getByName(StringUtils.substringBeforeLast(enchant, "_")), Integer.parseInt(StringUtils.substringAfterLast(enchant, "_")));
        }
        
        equipment.setHelmet(helmet.build());
        equipment.setChestplate(chestplate.build());
        equipment.setLeggings(leggings.build());
        equipment.setBoots(boots.build());
        equipment.setItemInHand(heldItem.build());

        customEntities.put(entity, gen);
    }

    public static String getConfigString(String path) {
        return BedSteal.getInstance().getConfig().getString(path);
    }

    public static ArrayList<Location> Sphere(Location location) {
        ArrayList<Location> loc = new ArrayList<>();
        Set<Block> block = sphereAround(location, BedSteal.getInstance().getConfig().getInt("MobGenerator.settings.radius"));
        if (loc.size() < 35) {
            for (Block blocks : block) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    if (blocks.getWorld().getBlockAt(blocks.getLocation()).getLocation().getBlock().getType() == Material.AIR) {
                        loc.add(blocks.getWorld().getBlockAt(blocks.getLocation()).getLocation());
                    } else {
                        loc.add(blocks.getWorld().getBlockAt(blocks.getLocation()).getLocation().add(0, 1, 0));
                    }
                } else {
                    continue;
                }
            }
        }
        return loc;
    }
}
