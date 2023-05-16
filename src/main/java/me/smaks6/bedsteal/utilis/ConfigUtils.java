package me.smaks6.bedsteal.utilis;

import me.smaks6.bedsteal.BedSteal;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public class ConfigUtils {
    private static final FileConfiguration configFile = BedSteal.getInstance().getConfig();

    public static String get(Config config) {
        return ChatUtils.addColors(configFile.getString(config.getConfigName()));
    }

    public static void set(Config config, Object value) {
        configFile.set(config.getConfigName(), value);
        saveAndReload();
    }
    public static List<String> getAsList(Config config){
        return Arrays.asList(ConfigUtils.get(Config.ShurikenDescription).split("/n"));
    }

    public static int getInt(Config poisonSwordPoisonDuration) {
        return configFile.getInt(poisonSwordPoisonDuration.getConfigName());
    }

    public static void saveAndReload() {
        BedSteal.getInstance().saveConfig();
        BedSteal.getInstance().reloadConfig();
    }
}
