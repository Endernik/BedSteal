package me.smaks6.bedsteal.items.impl;

import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.items.CustomItem;
import me.smaks6.bedsteal.items.HitableCustomItem;
import me.smaks6.bedsteal.utilis.Config;
import me.smaks6.bedsteal.utilis.ConfigUtils;
import me.smaks6.bedsteal.utilis.TextUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;

public class PoisonSword implements CustomItem, HitableCustomItem {
    @Override
    public ItemStack getItem(Player player) {
        ItemStack poisonSword = new ItemStack(Material.IRON_SWORD);
        ItemMeta itemMeta = poisonSword.getItemMeta();
        itemMeta.setDisplayName(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.PoisonSword")));
//        itemMeta.setLore(ConfigUtils.getAsList(Config.PoisonSwordDescription));
        itemMeta.spigot().setUnbreakable(true);
        poisonSword.setItemMeta(itemMeta);
        return poisonSword;
    }

    @Override
    public void hit(Player hitter, Player victim) {
        victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, ConfigUtils.getInt(Config.PoisonSwordPoisonDuration), 1));
    }
}
