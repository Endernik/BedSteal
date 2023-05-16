package me.smaks6.bedsteal.items;

import me.smaks6.bedsteal.items.impl.PoisonSword;
import me.smaks6.bedsteal.items.impl.Shuriken;
import me.smaks6.bedsteal.items.impl.VictimBed;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class CustomItemRegistry {
    public static final Shuriken SHURIKEN = new Shuriken();
    public static final VictimBed VICTIM_BED = new VictimBed();
    public static final PoisonSword POISON_SWORD = new PoisonSword();

    private static final List<CustomItem> customItems = Arrays.asList(
            SHURIKEN,
            VICTIM_BED,
            POISON_SWORD
    );


    private CustomItemRegistry() {
    }

    public static Optional<CustomItem> getItem(ItemStack itemStack) {
        return customItems.stream()
                .filter(customItem -> customItem.getItem(null).isSimilar(itemStack))
                .findFirst();
    }

    public static List<CustomItem> getAllItems() {
        return customItems;
    }
}
