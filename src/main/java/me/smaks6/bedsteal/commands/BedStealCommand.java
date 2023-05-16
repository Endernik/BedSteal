package me.smaks6.bedsteal.commands;

import com.andrei1058.bedwars.api.arena.IArena;
import me.smaks6.bedsteal.BedSteal;
import me.smaks6.bedsteal.items.CustomItem;
import me.smaks6.bedsteal.items.CustomItemRegistry;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import me.smaks6.bedsteal.utilis.Config;
import me.smaks6.bedsteal.utilis.ConfigUtils;
import me.smaks6.bedsteal.utilis.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class BedStealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1) {
            sender.sendMessage("Choose option: items, hologramcalc");
            sender.sendMessage("Hologramcal - stand where the hologram must be, and write this command, the effect you see, after restart server");
            return false;
        }

        if(args[0].equalsIgnoreCase("giveitem")) {
            if(args.length != 3) {
                sender.sendMessage("command use: /bedsteal giveitem [nick] [0/1/2]");
                return false;
            }


            Player player = Bukkit.getPlayer(args[1]);
            if(player == null) {
                sender.sendMessage("Player not exist");
                return false;
            }
            IArena arena1 = BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(player);
            if (arena1 == null) return false;
            if (!CanArenaUtil.get(arena1)) return false;
            CustomItem item;
            try {
                item = CustomItemRegistry.getAllItems().get(Integer.parseInt(args[2]));
            }catch (IndexOutOfBoundsException | NumberFormatException exception){
                return false;
            }
            if(item == null) {
                sender.sendMessage("Item not exist");
                return false;
            }
            player.getInventory().addItem(item.getItem(player));
            return true;
        }
        if(!(sender instanceof Player)){
            System.out.println("You are console!");
            return false;
        }
        Player senderPlayer = (Player) sender;

        if(args[0].equalsIgnoreCase("reload")) {
            Bukkit.getPluginManager().disablePlugin(BedSteal.getInstance());
            BedSteal.getInstance().reloadConfig();
            Bukkit.getPluginManager().enablePlugin(BedSteal.getInstance());
            senderPlayer.sendMessage(ChatColor.GREEN + "Reload!");
        }

        if(args[0].equalsIgnoreCase("items")) {
            IArena arena2 = BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(senderPlayer);
            if (arena2 == null) return false;
            if (!CanArenaUtil.get(arena2)) return false;
            for (CustomItem customItem : CustomItemRegistry.getAllItems()) {
                senderPlayer.getInventory().addItem(customItem.getItem(senderPlayer));
            }
            senderPlayer.getInventory().addItem(CustomItemRegistry.VICTIM_BED.getItem(5, senderPlayer));
            senderPlayer.getInventory().addItem(CustomItemRegistry.VICTIM_BED.getItem(100, senderPlayer));
        }else if(args[0].equalsIgnoreCase("hologramcalc")) {
            IArena arena3 = BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(senderPlayer);
            if (arena3 == null) return false;
            if (!CanArenaUtil.get(arena3)) return false;
            IArena arena = BedSteal.bedWarsAPI.getArenaUtil().getArenaByPlayer(senderPlayer);
            Location bedLocation = arena.getTeam(senderPlayer).getBed();
            Location playerLocation = senderPlayer.getLocation();

            int x = Math.abs(bedLocation.getBlockX() - playerLocation.getBlockX());
            int y = Math.abs(bedLocation.getBlockY() - playerLocation.getBlockY());
            int z = Math.abs(bedLocation.getBlockZ() - playerLocation.getBlockZ());
            senderPlayer.sendMessage(x + " " + y + " " + z);
            ConfigUtils.set(Config.BedHologramXMove, x);
            ConfigUtils.set(Config.BedHologramYMove, y);
            ConfigUtils.set(Config.BedHologramZMove, z);
            ArmorStand armorStand = (ArmorStand) bedLocation.getWorld().spawnEntity(LocationUtils.getCenter(bedLocation.clone().add(x, y, z)), EntityType.ARMOR_STAND);
            armorStand.setMarker(true);
            armorStand.setCustomName("Test new hologram");
            armorStand.setCustomNameVisible(true);
            armorStand.setGravity(false);

            Bukkit.getScheduler().runTaskLater(BedSteal.getInstance(), new Runnable() {
                @Override
                public void run() {
                    armorStand.remove();
                }
            }, 100L);
        }
        return false;
    }
}
