package me.smaks6.bedsteal.utilis;

import org.bukkit.ChatColor;

public final class ChatUtils {
    private ChatUtils() {
    }

    public static String addColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
