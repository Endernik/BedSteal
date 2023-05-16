package me.smaks6.bedsteal;

import com.andrei1058.bedwars.api.BedWars;
import com.andrei1058.bedwars.api.language.Language;
import me.smaks6.bedsteal.commands.BedStealCommand;
import me.smaks6.bedsteal.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class BedSteal extends JavaPlugin {
    public static BedWars bedWarsAPI;
    private static BedSteal instance;

    @Override
    public void onEnable() {
        bedWarsAPI = Bukkit.getServicesManager().getRegistration(BedWars.class).getProvider();
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        setupLang();
        Bukkit.getServer().getPluginManager().registerEvents(new ArenaDestroyListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ArenaStartListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BedDestroyListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new LeaveArenaListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ReJoinArenaPlayerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new RightClickListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BedPlaceListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new RespawnPlayerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ShopListener(), this);
        getCommand("bedsteal").setExecutor(new BedStealCommand());
        Logger.getAnonymousLogger().log(Level.INFO, "BedSteal start!");
    }

    public static String getPlayerConfigString(Player p, String path) {
        if (p == null) return bedWarsAPI.getDefaultLang().getYml().getString(path);
        return bedWarsAPI.getPlayerLanguage(p).getYml().getString(path);
    }

    public static List<String> getPlayerConfigStrings(Player p, String path) {
        if (p == null) return bedWarsAPI.getDefaultLang().getYml().getStringList(path);
        return bedWarsAPI.getPlayerLanguage(p).getYml().getStringList(path);
    }

    public String getConfigString(String path) {
        return getConfig().getString(path);
    }

    public int getConfigInt(String path) {
        return getConfig().getInt(path);
    }

    public Boolean getConfigBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    private static void setupLang() {
        for (Language language : Language.getLanguages()) {
            if (language.getYml().getString("name").equals("Polski")) {
                if (!language.exists(".addons.bedsteal.VictimBedTitle"))
                    language.set(".addons.bedsteal.VictimBedTitle", "&cŁóżko przeciwnika");
                if (!language.exists(".addons.bedsteal.VictimBedDescription"))
                    language.set(".addons.bedsteal.VictimBedDescription", "&7Kliknij prawym na swoje łóżko");
                if (!language.exists(".addons.bedsteal.BedsHologramText"))
                    language.set(".addons.bedsteal.BedsHologramText", "&7Drużyna posiada {beds} poziom łóżka!");
                if (!language.exists(".addons.bedsteal.ShurikenTitle"))
                    language.set(".addons.bedsteal.ShurikenTitle", "&cShuriken");
                if (!language.exists(".addons.bedsteal.ShurikenDescription"))
                    language.set(".addons.bedsteal.ShurikenDescription", "&7Kliknij prawym by rzucić w przeciwnika!");
                if (!language.exists(".addons.bedsteal.ShurikenLore"))
                    language.set(".addons.bedsteal.ShurikenLore", Arrays.asList("&7Cost: &6{cost}", "", "&7Kliknij prawym by rzucić w przeciwnika!", "", "{status}"));
                if (!language.exists(".addons.bedsteal.MobGenerator-Name"))
                    language.set(".addons.bedsteal.MobGenerator-Name", "&aSzmaragdowy Obrońca");
                if (!language.exists(".addons.bedsteal.PoisonSword"))
                    language.set(".addons.bedsteal.PoisonSword", "&cMiecz zatrucia");
                if (!language.exists(".addons.bedsteal.PoisonSwordDescription"))
                    language.set(".addons.bedsteal.PoisonSwordDescription", "&7Udrze gracza by go otruć");
                if (!language.exists(".addons.bedsteal.PoisonSwordLore"))
                    language.set(".addons.bedsteal.PoisonSwordLore", Arrays.asList("&7Cost: &6{cost}", "", "&7Udrze gracza by go otruć", "", "{status}"));
                if (!language.exists(".addons.bedsteal.Shop-Category-Title"))
                    language.set(".addons.bedsteal.Shop-Category-Title", "Kradzież łóżka");
                if (!language.exists(".addons.bedsteal.Shop-Category-Name"))
                    language.set(".addons.bedsteal.Shop-Category-Name", "&aKradzież łóżka");
                if (!language.exists(".addons.bedsteal.Shop-Category-Lore"))
                    language.set(".addons.bedsteal.Shop-Category-Lore", "&eClick to view!");
                if (!language.exists(".addons.bedsteal.buy-error"))
                    language.set(".addons.bedsteal.buy-error", "&cYou don't have enough Redstone! Need {amount} more");
                if (!language.exists(".addons.bedsteal.buy-success"))
                    language.set(".addons.bedsteal.buy-success", "&aYou purchased &6{item}");
                if (!language.exists(".addons.bedsteal.item-status-err"))
                    language.set(".addons.bedsteal.item-status-err", "&cYou don't have enough Redstone!");
                if (!language.exists(".addons.bedsteal.item-status-success"))
                    language.set(".addons.bedsteal.item-status-success", "&eClick to purchase!");
            } else if (language.getYml().getString("name").equals("English")) {
                if (!language.exists(".addons.bedsteal.VictimBedTitle"))
                    language.set(".addons.bedsteal.VictimBedTitle", "&cOpponent's bed");
                if (!language.exists(".addons.bedsteal.VictimBedDescription"))
                    language.set(".addons.bedsteal.VictimBedDescription", "&7Right click on your bed!");
                if (!language.exists(".addons.bedsteal.BedsHologramText"))
                    language.set(".addons.bedsteal.BedsHologramText", "&7Team has {beds} bed level!");
                if (!language.exists(".addons.bedsteal.ShurikenTitle"))
                    language.set(".addons.bedsteal.ShurikenTitle", "&cShuriken");
                if (!language.exists(".addons.bedsteal.ShurikenDescription"))
                    language.set(".addons.bedsteal.ShurikenDescription", "&7Right click to throw!");
                if (!language.exists(".addons.bedsteal.ShurikenLore"))
                    language.set(".addons.bedsteal.ShurikenLore", Arrays.asList("&7Cost: &6{cost}", "", "&7Right click to throw!", "", "{status}"));
                if (!language.exists(".addons.bedsteal.MobGenerator-Name"))
                    language.set(".addons.bedsteal.MobGenerator-Name", "&aEmerald Defender");
                if (!language.exists(".addons.bedsteal.PoisonSword"))
                    language.set(".addons.bedsteal.PoisonSword", "&cPoisoned Sword");
                if (!language.exists(".addons.bedsteal.PoisonSwordDescription"))
                    language.set(".addons.bedsteal.PoisonSwordDescription", "&7Hit a player to poison him");
                if (!language.exists(".addons.bedsteal.PoisonSwordLore"))
                    language.set(".addons.bedsteal.PoisonSwordLore", Arrays.asList("&7Cost: &6{cost}", "", "&7Hit a player to poison him", "", "{status}"));
                if (!language.exists(".addons.bedsteal.Shop-Category-Title"))
                    language.set(".addons.bedsteal.Shop-Category-Title", "BedSteal");
                if (!language.exists(".addons.bedsteal.Shop-Category-Name"))
                    language.set(".addons.bedsteal.Shop-Category-Name", "&aBedSteal");
                if (!language.exists(".addons.bedsteal.Shop-Category-Lore"))
                    language.set(".addons.bedsteal.Shop-Category-Lore", "&eClick to view!");
                if (!language.exists(".addons.bedsteal.buy-error"))
                    language.set(".addons.bedsteal.buy-error", "&cYou don't have enough Redstone! Need {amount} more");
                if (!language.exists(".addons.bedsteal.buy-success"))
                    language.set(".addons.bedsteal.buy-success", "&aYou purchased &6{item}");
                if (!language.exists(".addons.bedsteal.item-status-err"))
                    language.set(".addons.bedsteal.item-status-err", "&cYou don't have enough Redstone!");
                if (!language.exists(".addons.bedsteal.item-status-success"))
                    language.set(".addons.bedsteal.item-status-success", "&eClick to purchase!");
            } else {
                if (!language.exists(".addons.bedsteal.VictimBedTitle"))
                    language.set(".addons.bedsteal.VictimBedTitle", "&cOpponent's bed");
                if (!language.exists(".addons.bedsteal.VictimBedDescription"))
                    language.set(".addons.bedsteal.VictimBedDescription", "&7Right click on your bed!");
                if (!language.exists(".addons.bedsteal.BedsHologramText"))
                    language.set(".addons.bedsteal.BedsHologramText", "&7Team has {beds} bed level!");
                if (!language.exists(".addons.bedsteal.ShurikenTitle"))
                    language.set(".addons.bedsteal.ShurikenTitle", "&cShuriken");
                if (!language.exists(".addons.bedsteal.ShurikenDescription"))
                    language.set(".addons.bedsteal.ShurikenDescription", "&7Right click to throw!");
                if (!language.exists(".addons.bedsteal.ShurikenLore"))
                    language.set(".addons.bedsteal.ShurikenLore", Arrays.asList("&7Cost: &6{cost}", "", "&7Right click to throw!", "", "{status}"));
                if (!language.exists(".addons.bedsteal.MobGenerator-Name"))
                    language.set(".addons.bedsteal.MobGenerator-Name", "&aEmerald Defender");
                if (!language.exists(".addons.bedsteal.PoisonSword"))
                    language.set(".addons.bedsteal.PoisonSword", "&cPoisoned Sword");
                if (!language.exists(".addons.bedsteal.PoisonSwordDescription"))
                    language.set(".addons.bedsteal.PoisonSwordDescription", "&7Hit a player to poison him");
                if (!language.exists(".addons.bedsteal.PoisonSwordLore"))
                    language.set(".addons.bedsteal.PoisonSwordLore", Arrays.asList("&7Cost: &6{cost}", "", "&7Hit a player to poison him", "", "{status}"));
                if (!language.exists(".addons.bedsteal.Shop-Category-Title"))
                    language.set(".addons.bedsteal.Shop-Category-Title", "BedSteal");
                if (!language.exists(".addons.bedsteal.Shop-Category-Name"))
                    language.set(".addons.bedsteal.Shop-Category-Name", "&aBedSteal");
                if (!language.exists(".addons.bedsteal.Shop-Category-Lore"))
                    language.set(".addons.bedsteal.Shop-Category-Lore", "&eClick to view!");
                if (!language.exists(".addons.bedsteal.buy-error"))
                    language.set(".addons.bedsteal.buy-error", "&cYou don't have enough Redstone! Need {amount} more");
                if (!language.exists(".addons.bedsteal.buy-success"))
                    language.set(".addons.bedsteal.buy-success", "&aYou purchased &6{item}");
                if (!language.exists(".addons.bedsteal.item-status-err"))
                    language.set(".addons.bedsteal.item-status-err", "&cYou don't have enough Redstone!");
                if (!language.exists(".addons.bedsteal.item-status-success"))
                    language.set(".addons.bedsteal.item-status-success", "&eClick to purchase!");
            }
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().stream().forEach(player -> player.setMaxHealth(20));
    }

    public static BedSteal getInstance() {
        return instance;
    }
}
