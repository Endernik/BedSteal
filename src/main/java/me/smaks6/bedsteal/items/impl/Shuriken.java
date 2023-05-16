package me.smaks6.bedsteal.items.impl;

import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.items.CustomItem;
import me.smaks6.bedsteal.items.CustomItemRegistry;
import me.smaks6.bedsteal.items.UsableCustomItem;
import me.smaks6.bedsteal.objects.ShurikenEntity;
import me.smaks6.bedsteal.utilis.Config;
import me.smaks6.bedsteal.utilis.ConfigUtils;
import me.smaks6.bedsteal.utilis.TextUtil;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Shuriken implements CustomItem, UsableCustomItem {

    @Override
    public void use(Player player) {
        ShurikenEntity shurikenEntity = new ShurikenEntity(player);
        shurikenEntity.moveRunnable();
        ItemStack shurikenItem = CustomItemRegistry.SHURIKEN.getItem(player);
        shurikenItem.setAmount(player.getItemInHand().getAmount()-1);
        player.setItemInHand(shurikenItem);
    }

    @Override
    public ItemStack getItem(Player player) {
        ItemStack shuriken = new ItemStack(Material.NETHER_STAR);
        ItemMeta itemMeta = shuriken.getItemMeta();
        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        itemMeta.setDisplayName(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.ShurikenTitle")));
//        itemMeta.setLore(ConfigUtils.getAsList(Config.ShurikenDescription));
        shuriken.setItemMeta(itemMeta);

        return shuriken;
    }
}
