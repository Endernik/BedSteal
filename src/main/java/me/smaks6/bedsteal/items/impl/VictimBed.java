package me.smaks6.bedsteal.items.impl;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.items.CustomItem;
import me.smaks6.bedsteal.items.CustomItemRegistry;
import me.smaks6.bedsteal.utilis.BedsUtils;
import me.smaks6.bedsteal.utilis.Config;
import me.smaks6.bedsteal.utilis.ConfigUtils;
import me.smaks6.bedsteal.utilis.TextUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class VictimBed implements CustomItem {

    public void onUse(Player player, Location clickedBed) {
        ITeam team = BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(player).getTeam(player);
        Location bed = team.getBed();
        if(clickedBed.distance(bed) < 2 && !team.isBedDestroyed()){
            int beds = CustomItemRegistry.VICTIM_BED.victimBedToBeds(player.getItemInHand(), player);
            player.setItemInHand(null);
            BedsUtils.addBeds(team, beds);
            player.getInventory().addItem(new ItemStack(Material.REDSTONE, 10*beds));
        }
    }

    public ItemStack getItem(int beds, Player player) {
        ItemStack bed = new ItemStack(Material.BED);
        ItemMeta itemMeta = bed.getItemMeta();
        itemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, beds, true);
        itemMeta.setDisplayName(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.VictimBedTitle")));
        itemMeta.setLore(TextUtil.getColoredStrings(Arrays.asList(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.VictimBedDescription").split("/n"))));
        bed.setItemMeta(itemMeta);
        return bed;
    }

    public int victimBedToBeds(ItemStack bed, Player player) {
        int beds = bed.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        if(bed.equals(getItem(beds, player))) {
            return beds;
        }
        return 0;
    }

    @Override
    public ItemStack getItem(Player player) {
        return getItem(1, player);
    }
}
