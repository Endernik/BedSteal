package me.smaks6.bedsteal.items;

import org.bukkit.entity.Player;

public interface HitableCustomItem {
    void hit(Player hitter, Player victim);
}
