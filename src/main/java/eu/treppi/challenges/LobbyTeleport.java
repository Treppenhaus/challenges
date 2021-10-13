package eu.treppi.challenges;

import eu.treppi.challenges.core.ChallengesPlugin;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class LobbyTeleport implements Listener, CommandExecutor {
    private static final String PATH = "plugins/challenges/config.yml";
    @EventHandler
    public void onPlayerLobby(PlayerTeleportEvent e) {

        Player p = e.getPlayer();
        Location newLocation = e.getTo();
        Location oldLocation = e.getFrom();
        World world = newLocation.getWorld();

        WorldTimer wt = Timing.getTimer(oldLocation.getWorld());
        if(wt != null) {
            if(wt.getWorld().getPlayers().stream().noneMatch(pl -> pl.getGameMode() == GameMode.SURVIVAL)) {
                ChallengesPlugin.send(p, "§cDa du der letzte Spieler warst, der die Welt verlassen hat, wurde der Timer gestoppt.");
                wt.stop(p);
            }

            wt = Timing.getTimer(newLocation.getWorld());
            if(wt.getWorld().getPlayers().stream().filter(pl -> pl.getGameMode() == GameMode.SURVIVAL).count() == 1) {
                if(!wt.challengefailed) {
                    ChallengesPlugin.send(p, "§aDa du der erste Spieler in dieser Welt bist, wurde der Timer gestartet!");
                    p.setGameMode(GameMode.SURVIVAL);
                    wt.start(p);
                }
                else {
                    p.sendTitle("", "§c✘", 10, 20, 10);
                    p.setGameMode(GameMode.SPECTATOR);
                }


            }
        }

        if(world.getName().equalsIgnoreCase("lobby")) {
            p.setGameMode(GameMode.ADVENTURE);
            ChallengesPlugin.send(p, "§7Du hast den Spawn betreten!");
        }


    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player) {
            ((Player) sender).teleport(getLobbyLocation());
        }
        return false;
    }

    public static Location getLobbyLocation() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(PATH));
        return ConfigHelperp.readLocation(config, "spawn");
    }

    public static void setLobbyLocation(Location loc) {
        FileConfiguration config = YamlConfiguration.loadConfiguration(new File(PATH));
        ConfigHelperp.saveLocation(loc, config, "spawn");
        try {
            config.save(new File(PATH));
        }catch (Exception e) {
            // ignored
        }

    }
}
