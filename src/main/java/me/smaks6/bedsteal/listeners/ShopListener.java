package me.smaks6.bedsteal.listeners;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.configuration.ConfigPath;
import com.andrei1058.bedwars.api.events.shop.ShopBuyEvent;
import com.andrei1058.bedwars.api.events.shop.ShopOpenEvent;
import com.andrei1058.bedwars.api.language.Language;
import com.andrei1058.bedwars.api.language.Messages;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.items.CustomItemRegistry;
import me.smaks6.bedsteal.utilis.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopListener implements Listener {

    private Inventory inventory;
    private ItemStack[] contents;
    private ItemStack quickBuy;

    @EventHandler
    public void onShopOpen(InventoryOpenEvent e) {
        Player player = (Player) e.getPlayer();
        IArena arena = BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(player);
        if (arena == null) return;
        if (CanArenaUtil.get(arena)) {
            if (e.getView().getTitle().equals(Language.getMsg(player, Messages.SHOP_INDEX_NAME))) {
                if (inventory != null) {
                    if (!e.getInventory().contains(inventory.getItem(0), 0)) return;
                }
                inventory = e.getInventory();
                quickBuy = e.getInventory().getItem(0);
                int slot = BedSteal.getInstance().getConfig().getInt("Shop.Category.slot");
                e.getInventory().setItem(slot, BedSteal.bedWarsAPI.getVersionSupport().addCustomData(new ItemBuilder(Material.valueOf(BedSteal.getInstance().getConfig().getString("Shop.Category.material")), (short) BedSteal.getInstance().getConfig().getInt("Shop.Category.data")).setAmount(BedSteal.getInstance().getConfig().getInt("Shop.Category.amount")).setDisplayName(BedSteal.getPlayerConfigString((Player) e.getPlayer(), ".addons.bedsteal.Shop-Category-Name")).addLore(BedSteal.getPlayerConfigString((Player) e.getPlayer(), ".addons.bedsteal.Shop-Category-Lore")).addItemFlag(ItemFlag.HIDE_ENCHANTS).setGlow(BedSteal.getInstance().getConfig().getBoolean("Shop.Category.enchanted")).build(), "BEDSTEAL-CATEGORY-ITEM"));
            }
        }
    }

    @EventHandler
    public void onShopClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        IArena arena = BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(player);
        if (e.getCurrentItem() == null) return;
        if (arena == null) return;
        if (CanArenaUtil.get(arena)) {
            if (e.getView().getTitle().equals(Language.getMsg(player, Messages.SHOP_INDEX_NAME))) {
                if (inventory == null) return;
                if (e.getInventory().contains(inventory.getItem(0), 0)) {
                    String getCustomData = null;
                    try {
                        getCustomData = ItemBuilder.getCustomData(e.getCurrentItem());
                    } catch (Exception ex) {
                        return;
                    }
                    if (getCustomData.equals("BEDSTEAL-CATEGORY-ITEM")) {
                        InventoryUpdate.updateInventory((Player) e.getWhoClicked(), BedSteal.getPlayerConfigString((Player) e.getWhoClicked(), ".addons.bedsteal.Shop-Category-Title"));
                        for (int i = 9; i < 17; i++) {
                            e.getInventory().setItem(i, new ItemBuilder(Material.valueOf(BedSteal.bedWarsAPI.getConfigs().getShopConfig().getString(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_MATERIAL)), (short) BedSteal.bedWarsAPI.getConfigs().getShopConfig().getInt(ConfigPath.SHOP_SETTINGS_SEPARATOR_REGULAR_DATA)).setDisplayName(e.getInventory().getItem(i).getItemMeta().getDisplayName()).setLore(e.getInventory().getItem(i).getItemMeta().getLore()).build());
                        }
                        contents = e.getInventory().getContents();
                        for (int i = 18; i < 54; i++) {
                            e.getInventory().setItem(i, new ItemStack(Material.AIR));
                        }
                        e.getInventory().setItem(17, new ItemBuilder(Material.valueOf(BedSteal.bedWarsAPI.getConfigs().getShopConfig().getString(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_MATERIAL)), (short) BedSteal.bedWarsAPI.getConfigs().getShopConfig().getInt(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_DATA)).setDisplayName(e.getInventory().getItem(17).getItemMeta().getDisplayName()).setLore(e.getInventory().getItem(17).getItemMeta().getLore()).build());
                        e.getInventory().setItem(BedSteal.getInstance().getConfig().getInt("Shop.Items.shuriken.slot"), new ItemBuilder(Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Items.shuriken.material")), (short) BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.data"))
                                .setDisplayName(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.ShurikenTitle")))
                                .setLore(TextUtil.getColoredStringsLore(BedSteal.getPlayerConfigStrings(player, ".addons.bedsteal.ShurikenLore"), BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.cost"), TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, getStatusPath(player, BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.cost"))))))
                                .setAmount(BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.amount"))
                                .setGlow(BedSteal.getInstance().getConfigBoolean("Shop.Items.shuriken.enchanted")).addCustomData("SHURIKEN-BUY-ITEM")
                                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                                .build());

                        e.getInventory().setItem(BedSteal.getInstance().getConfig().getInt("Shop.Items.poisonous-sword.slot"), new ItemBuilder(Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Items.poisonous-sword.material")), (short) BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.data"))
                                .setDisplayName(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.PoisonSword")))
                                .setLore(TextUtil.getColoredStringsLore(BedSteal.getPlayerConfigStrings(player, ".addons.bedsteal.PoisonSwordLore"), BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.cost"), TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, getStatusPath(player, BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.cost"))))))
                                .setAmount(BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.amount"))
                                .setGlow(BedSteal.getInstance().getConfigBoolean("Shop.Items.poisonous-sword.enchanted")).addCustomData("POISONOUS-SWORD-BUY-ITEM")
                                .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                                .build());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onShopBuy(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        IArena arena = BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(player);
        if (arena == null) return;
        if (CanArenaUtil.get(arena)) {
            if (e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()) return;
            if (e.getSlot() == 0 && e.getCurrentItem().isSimilar(quickBuy)) {
                e.getInventory().setContents(contents);
                e.getInventory().setItem(9, new ItemBuilder(Material.valueOf(BedSteal.bedWarsAPI.getConfigs().getShopConfig().getString(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_MATERIAL)), (short) BedSteal.bedWarsAPI.getConfigs().getShopConfig().getInt(ConfigPath.SHOP_SETTINGS_SEPARATOR_SELECTED_DATA)).setDisplayName(e.getInventory().getItem(9).getItemMeta().getDisplayName()).setLore(e.getInventory().getItem(9).getItemMeta().getLore()).build());
                return;
            }
            if (ItemBuilder.getCustomData(e.getCurrentItem()).equals("POISONOUS-SWORD-BUY-ITEM")) {
                e.setCancelled(true);
                if (BedSteal.bedWarsAPI.getShopUtil().calculateMoney(player, Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Currency"))) >= BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.cost")) {
                    BedSteal.bedWarsAPI.getShopUtil().takeMoney(player, Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Currency")), BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.cost"));
                    player.getInventory().addItem(CustomItemRegistry.getAllItems().get(2).getItem(player));
                    e.getInventory().setItem(BedSteal.getInstance().getConfig().getInt("Shop.Items.poisonous-sword.slot"), new ItemBuilder(Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Items.poisonous-sword.material")), (short) BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.data"))
                            .setDisplayName(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.PoisonSword")))
                            .setLore(TextUtil.getColoredStringsLore(BedSteal.getPlayerConfigStrings(player, ".addons.bedsteal.PoisonSwordLore"), BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.cost"), TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, getStatusPath(player, BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.cost"))))))
                            .setAmount(BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.amount"))
                            .setGlow(BedSteal.getInstance().getConfigBoolean("Shop.Items.poisonous-sword.enchanted")).addCustomData("POISONOUS-SWORD-BUY-ITEM")
                            .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                            .build());
                    player.sendMessage(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.buy-success")).replace("{item}", e.getCurrentItem().getItemMeta().getDisplayName()));
                } else {
                    player.sendMessage(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.buy-error")).replace("{amount}", Integer.toString(BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.cost") - BedSteal.bedWarsAPI.getShopUtil().calculateMoney(player, Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Currency"))))));
                }
            } else if (ItemBuilder.getCustomData(e.getCurrentItem()).equals("SHURIKEN-BUY-ITEM")) {
                e.setCancelled(true);
                if (BedSteal.bedWarsAPI.getShopUtil().calculateMoney(player, Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Currency"))) >= BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.cost")) {
                    BedSteal.bedWarsAPI.getShopUtil().takeMoney(player, Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Currency")), BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.cost"));
                    player.getInventory().addItem(CustomItemRegistry.getAllItems().get(0).getItem(player));
                    e.getInventory().setItem(BedSteal.getInstance().getConfig().getInt("Shop.Items.shuriken.slot"), new ItemBuilder(Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Items.shuriken.material")), (short) BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.data"))
                            .setDisplayName(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.ShurikenTitle")))
                            .setLore(TextUtil.getColoredStringsLore(BedSteal.getPlayerConfigStrings(player, ".addons.bedsteal.ShurikenLore"), BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.cost"), TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, getStatusPath(player, BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.cost"))))))
                            .setAmount(BedSteal.getInstance().getConfigInt("Shop.Items.shuriken.amount"))
                            .setGlow(BedSteal.getInstance().getConfigBoolean("Shop.Items.shuriken.enchanted")).addCustomData("SHURIKEN-BUY-ITEM")
                            .addItemFlag(ItemFlag.HIDE_ATTRIBUTES)
                            .build());
                    player.sendMessage(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.buy-success")).replace("{item}", e.getCurrentItem().getItemMeta().getDisplayName()));
                } else {
                    player.sendMessage(TextUtil.getColoredString(BedSteal.getPlayerConfigString(player, ".addons.bedsteal.buy-error")).replace("{amount}", Integer.toString(BedSteal.getInstance().getConfigInt("Shop.Items.poisonous-sword.cost") - BedSteal.bedWarsAPI.getShopUtil().calculateMoney(player, Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Currency"))))));
                }
            }
        }
    }

    private String getStatusPath(Player player, int cost) {
        if (BedSteal.bedWarsAPI.getShopUtil().calculateMoney(player, Material.valueOf(BedSteal.getInstance().getConfigString("Shop.Currency"))) >= cost) {
            return ".addons.bedsteal.item-status-success";
        } else {
            return ".addons.bedsteal.item-status-err";
        }
    }
}
