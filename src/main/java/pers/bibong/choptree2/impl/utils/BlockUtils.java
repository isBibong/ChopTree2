package pers.bibong.choptree2.impl.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import pers.bibong.choptree2.ChopTree2;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {

    public static final BlockFace[] directions = {
            BlockFace.UP,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST,
            BlockFace.NORTH_EAST,
            BlockFace.NORTH_WEST,
            BlockFace.SOUTH_EAST,
            BlockFace.SOUTH_WEST
    };

    private static final List<Material> logs   = new ArrayList<>();
    private static final List<Material> leaves = new ArrayList<>();

    public static boolean notTree(Block block) {
        Block highestLog     = getHighestLog(block);
        int   numberOfLeaves = 0;
        for (BlockFace direction : directions) {
            Block nearbyBlock = highestLog.getRelative(direction);

            if (isLeaves(nearbyBlock)) {
                ++numberOfLeaves;
            }
        }

        return numberOfLeaves < 2;
    }

    public static Block getHighestLog(Block block) {
        boolean isLogs = true;
        while (isLogs) {
            int iteration = 0;
            for (BlockFace direction : directions) {
                Block relative = block.getRelative(direction);
                if (isLogs(relative)) {
                    block = relative;
                    break;
                }

                if (iteration == directions.length - 1) {
                    isLogs = false;
                    break;
                }

                iteration++;
            }
        }
        return block;
    }

    public static void initLogs() {
        logs.clear();
        List<String> typeNames = ChopTree2.inst().setting().getStringList("Logs");
        for (String typeName : typeNames) {
            Material material = Material.getMaterial(typeName);
            logs.add(material);
        }
    }

    public static boolean isLogs(@NotNull Block block) {
        return logs.contains(block.getType());
    }

    public static void initLeaves() {
        leaves.clear();
        List<String> typeNames = ChopTree2.inst().setting().getStringList("Leaves");
        for (String typeName : typeNames) {
            Material material = Material.getMaterial(typeName);
            leaves.add(material);
        }
    }

    public static boolean isLeaves(@NotNull Block block) {
        return leaves.contains(block.getType());
    }

    public static void markersPlacedByPlayers(@NotNull Block block) {
        if (block.getState() instanceof TileState) {
            TileState               tileState     = (TileState) block.getState();
            PersistentDataContainer dataContainer = tileState.getPersistentDataContainer();
            dataContainer.set(new NamespacedKey(ChopTree2.inst(), "choptree2_placedByPlayers"),
                    PersistentDataType.STRING, "true");
            tileState.update();
        }
    }

    public static boolean hasChopTree2PlacedByPlayersTag(@NotNull Block block) {
        if (block.getState() instanceof TileState) {
            TileState               tileState     = (TileState) block.getState();
            PersistentDataContainer dataContainer = tileState.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(JavaPlugin.getPlugin(ChopTree2.class), "choptree2_placedByPlayers");
            return dataContainer.has(key, PersistentDataType.STRING);
        }

        return false;
    }

}
