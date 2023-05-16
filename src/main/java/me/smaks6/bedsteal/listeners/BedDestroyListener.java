package me.smaks6.bedsteal.listeners;

import com.andrei1058.bedwars.api.arena.team.ITeam;
import com.andrei1058.bedwars.api.events.player.PlayerBedBreakEvent;
import me.smaks6.bedsteal.items.CustomItemRegistry;
import me.smaks6.bedsteal.utilis.BedsHologramRegistry;
import me.smaks6.bedsteal.utilis.BedsUtils;
import me.smaks6.bedsteal.utilis.CanArenaUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class BedDestroyListener implements Listener {
    @EventHandler
    public void bedDestroy(PlayerBedBreakEvent event){
        if (CanArenaUtil.get(event.getArena())) {
            Location bedLocation = event.getVictimTeam().getBed();
            Player destroyerPlayer = event.getPlayer();
            ITeam team = event.getVictimTeam();
            int teamBeds = BedsUtils.getTeamBeds(team);
//        if(teamBeds > 1) {
//            team.setBedDestroyed(false);
//            BedsUtils.stealBed(team);
//            destroyerPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 5));
//            System.out.println(team.getBed());
//
//            //spawn bed
//            placeBed(bedLocation.add(0, -1, 0), locationFaceToBlockFace(team.getSpawn()));
//        }else{
//            BedsHologramRegistry.unregister(team);
//        }
//        destroyerPlayer.getInventory().addItem(CustomItemRegistry.VICTIM_BED.getItem());

            BedsUtils.setTeamBeds(team, 0);
            BedsHologramRegistry.unregister(team);
            destroyerPlayer.getInventory().addItem(CustomItemRegistry.VICTIM_BED.getItem(teamBeds, destroyerPlayer));
        }
        }

    private static void placeBed(Location loc, BlockFace face) {

        BlockState bedFoot = loc.getBlock().getState();
        BlockState bedHead = bedFoot.getBlock().getRelative(face.getOppositeFace()).getState();

        bedFoot.setType(Material.BED_BLOCK);
        bedHead.setType(Material.BED_BLOCK);

        bedFoot.setRawData((byte) face.ordinal());
        bedHead.setRawData((byte) (face.ordinal()+8));

        bedFoot.update(true, false);
        bedHead.update(true, true);

    }

    private static BlockFace locationFaceToBlockFace(Location location) {
        int yaw = (int) location.getYaw();

        if((-45 <= yaw) && (yaw <= 45)) {
            return BlockFace.NORTH;
        }else if((45 < yaw) && (yaw < 135)) {
            return BlockFace.EAST;
        }else if((135 <= yaw) && (yaw < -135)) {
            return BlockFace.SOUTH;
        }

        return BlockFace.WEST;
    }
}
