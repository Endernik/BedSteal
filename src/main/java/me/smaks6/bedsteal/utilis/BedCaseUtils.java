package me.smaks6.bedsteal.utilis;

import com.andrei1058.bedwars.api.arena.IArena;
import com.andrei1058.bedwars.api.arena.team.ITeam;
import me.smaks6.bedsteal.BedSteal;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.material.Bed;

import java.util.Arrays;
import java.util.List;

public final class BedCaseUtils {
    private static final List<BlockFace> BLOCK_FACES_AROUND_BED = Arrays.asList(
            BlockFace.UP,
            BlockFace.WEST,
            BlockFace.EAST,
            BlockFace.NORTH,
            BlockFace.SOUTH
    );
    private static final List<BlockFace> NEXT_TO_BED_FACES = Arrays.asList(
            BlockFace.DOWN,
            BlockFace.UP,
            BlockFace.WEST,
            BlockFace.EAST,
            BlockFace.NORTH,
            BlockFace.SOUTH
    );

    private BedCaseUtils() {

    }

    public static boolean isNextToBed(Location location) {
        Block block = location.getBlock();
        for (BlockFace nextToBedFace : NEXT_TO_BED_FACES) {
            if(block.getRelative(nextToBedFace).getType().equals(Material.BED_BLOCK)) {
                return true;
            }
        }
        return false;
    }

    public static void buildBedCaseFromLevel(int level, ITeam team) {
        Material material;
        switch (level) {
            case 0: material = Material.AIR; break;
            case 1: material = BedSteal.bedWarsAPI.getVersionSupport().woolMaterial(); break;
            case 2: material = Material.STAINED_CLAY; break;
            case 3: material = Material.valueOf(BedSteal.bedWarsAPI.getForCurrentVersion("WOOD", "WOOD", "OAK_WOOD")); break;
            case 4: material = Material.valueOf(BedSteal.bedWarsAPI.getForCurrentVersion("ENDER_STONE", "ENDER_STONE", "END_STONE")); break;
            default: material = Material.OBSIDIAN; break;
        }
        buildBedCase(material, team.getBed(), team.getArena());
        spawnFirework(team.getBed().clone().add(0, 2, 0));
    }

    public static void buildBedCase(Material material, Location bedLocation, IArena arena) {
        if(!bedLocation.getBlock().getType().equals(Material.BED_BLOCK)) {
            return;
        }
        Location secondBed = buildAroundBlock(material, bedLocation, arena);
        buildAroundBlock(material, secondBed, arena);
    }

    private static Location buildAroundBlock(Material material, Location center, IArena arena) {
        Block centerBlock = center.getBlock();
        Location secondBed = null;
        for (BlockFace blockFace : BLOCK_FACES_AROUND_BED) {
            Block relativeBlock = centerBlock.getRelative(blockFace);
            if(relativeBlock.getType().equals(Material.BED_BLOCK)) {
                secondBed = relativeBlock.getLocation();
                continue;
            }
            relativeBlock.setType(material);
            arena.addPlacedBlock(relativeBlock);
        }
        return secondBed;
    }

    private static void spawnFirework(Location location) {
        Firework f = location.getWorld().spawn(location, Firework.class);
        FireworkMeta fm = f.getFireworkMeta();

        fm.addEffect(FireworkEffect.builder()

                .flicker(true)
                .trail(true)
                .with(FireworkEffect.Type.STAR)
                .with(FireworkEffect.Type.BALL)
                .with(FireworkEffect.Type.BALL_LARGE)
                .withColor(Color.AQUA)
                .withColor(Color.YELLOW)
                .withColor(Color.RED)
                .withColor(Color.WHITE)
                .build());

        fm.setPower(0);
        f.setFireworkMeta(fm);
    }
}
