package pers.bibong.choptree2.impl.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import pers.bibong.choptree2.ChopTree2;
import pers.bibong.choptree2.impl.utils.BlockUtils;
import pers.bibong.choptree2.impl.utils.MessageHandle;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;

public class ChopTreeEvent implements Listener {
    private static final Set<UUID> chopTreePlayer = new HashSet<>();
    private static final int       MAX_DEPTH      = 3;

    @EventHandler
    public void onBlockBreakEvent(@NotNull BlockBreakEvent event) {

        Block block = event.getBlock();
        if (!BlockUtils.isLogs(block)) return;
        if (BlockUtils.notTree(block)) return;
        if (checkMark()) {
            if (BlockUtils.hasChopTree2PlacedByPlayersTag(block)) return;
        }

        Player player = event.getPlayer();
        if (!block.isValidTool(player.getInventory().getItemInMainHand())) return;
        if (chopTreePlayer.contains(player.getUniqueId())) return;

        chopTreePlayer.add(player.getUniqueId());
        this.chop(player, block);
    }

    private void chop(@NotNull Player player, Block block) {
        LinkedList<Block> logs = new LinkedList<>();
        chopRecursive(block, logs, 0);

        logs.forEach(player::breakBlock);
        chopTreePlayer.remove(player.getUniqueId());
    }

    private void chopRecursive(Block block, LinkedList<Block> logs, int depth) {
        if (!BlockUtils.isLogs(block) || logs.contains(block)) {
            return;
        }

        logs.add(block);

        for (BlockFace direction : BlockUtils.directions) {
            if (depth >= MAX_DEPTH) break;

            Block relative = block.getRelative(direction);
            chopRecursive(relative, logs, depth + 1);
        }
    }

    private boolean checkMark() {
        return ChopTree2.inst().config().getBoolean("CheckMark");
    }
}
