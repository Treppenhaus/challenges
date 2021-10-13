package eu.treppi.challenges;

import eu.treppi.challenges.core.ChallengesPlugin;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class GameController implements Listener {


    @EventHandler
    public void onLose(PlayerDeathEvent e) {
        Player p = e.getEntity();
        World w = p.getWorld();
        Location death = p.getLocation();
        p.teleport(death);

        WorldTimer wt = Timing.getTimer(w);
        if(wt != null) {
            wt.stop(p);
            wt.challengefailed = true;

            w.getPlayers().forEach(pl -> {

                pl.setGameMode(GameMode.SPECTATOR);
                ChallengesPlugin.send(pl, "§cDie Challenge ist §4fehlgeschlagen!");
                pl.sendTitle("", "§c✘", 3, 25, 5);
            });
        }
    }
}
