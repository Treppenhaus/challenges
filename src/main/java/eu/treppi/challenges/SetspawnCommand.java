package eu.treppi.challenges;

import eu.treppi.challenges.core.ChallengesPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetspawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player && sender.hasPermission("challenges.admin")) {
            Player p = (Player) sender;
            LobbyTeleport.setLobbyLocation(p.getLocation());
            ChallengesPlugin.send(p, "§aSpawn set!");
        }
        return false;
    }
}
