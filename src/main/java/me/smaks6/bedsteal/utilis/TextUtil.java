package me.smaks6.bedsteal.utilis;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TextUtil {
    private final static int CENTER_PX = 154;

    public static String getColoredString(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static List<String> getColoredStrings(List<String> input) {
        if (input.size() == 1 && input.get(0).equals("")) {
            return null;
        }
        List<String> ret = new ArrayList<String>();
        try {
            for (String line : input) {
                ret.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return ret;
    }

    public static List<String> getColoredStringsLore(List<String> input, int cost, String Status) {
        if (input.size() == 1 && input.get(0).equals("")) {
            return null;
        }
        List<String> ret = new ArrayList<String>();
        try {
            for (String line : input) {
                line = line.replace("{cost}", Integer.toString(cost)).replace("{status}", Status);
                ret.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return ret;
    }
}
