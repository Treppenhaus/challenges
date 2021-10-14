package eu.treppi.challenges;

import eu.treppi.challenges.core.ChallengesPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class ChallengeWon implements Listener {
    @EventHandler
    public void onEnderdragonKill(EntityDeathEvent e) {
        if(e.getEntity() instanceof EnderDragon) {

            World w = e.getEntity().getLocation().getWorld();
            WorldTimer timer = Timing.getTimer(w);

            timer.stop(null);
            w.getPlayers().forEach( p -> {
                ChallengesPlugin.send(p, "§aIhr habt die Challenge bestanden!");
                Location loc = p.getLocation();
                loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);

                p.sendTitle("§aErfolg", "§7Challenge bestanden", 10, 40, 10);
            });

        }
    }

}
