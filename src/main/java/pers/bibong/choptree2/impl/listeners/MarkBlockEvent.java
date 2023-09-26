package pers.bibong.choptree2.impl.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import org.jetbrains.annotations.NotNull;
import pers.bibong.choptree2.impl.utils.BlockUtils;

public class MarkBlockEvent implements Listener {

    @EventHandler
    public void onBlockPlaceEvent(@NotNull BlockPlaceEvent event) {
        BlockUtils.markersPlacedByPlayers(event.getBlock());
    }

}
