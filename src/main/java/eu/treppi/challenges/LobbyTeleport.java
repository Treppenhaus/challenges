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

        if(world.getName().equalsIgnoreCase("lobby")) {
            p.setGameMode(GameMode.ADVENTURE);
            ChallengesPlugin.send(p, "§7Du hast den Spawn betreten!");
        }


    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            WorldTimer timer = Timing.getTimer(p.getLocation().getWorld());
            if(timer.running) {
                Playerdata.saveLocation(p, p.getLocation());
            }
            p.teleport(getLobbyLocation());


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
