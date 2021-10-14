package eu.treppi.challenges;

import eu.treppi.challenges.core.ChallengesPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class Build implements Listener {
    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        WorldTimer wt = Timing.getTimer(e.getPlayer().getWorld());
        if(wt == null) return;
        if(!wt.running) {
            e.setCancelled(true);
            ChallengesPlugin.send(e.getPlayer(), "§7Du kannst nichts bauen, während der Timer gestoppt ist.");
        }
    }

    @EventHandler
    public void onbreak(BlockBreakEvent e) {
        WorldTimer wt = Timing.getTimer(e.getPlayer().getWorld());
        if(wt == null) return;
        if(!wt.running) {
            e.setCancelled(true);
            ChallengesPlugin.send(e.getPlayer(), "§7Du kannst nichts abbauen, während der Timer gestoppt ist.");
        }

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {

        WorldTimer wt = Timing.getTimer(e.getEntity().getWorld());
        if(wt == null) return;
        if(!wt.running) {
            e.setCancelled(true);
            if(e.getDamager() instanceof Player p) {
                ChallengesPlugin.send(p, "§7Du kannst keinen Schaden verursachen, während der Timer gestoppt ist.");
            }
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        WorldTimer wt = Timing.getTimer(e.getEntity().getWorld());
        if(wt != null) {
            if(!wt.running) {
                e.setCancelled(true);
            }
        }
    }

}
