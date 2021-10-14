package eu.treppi.challenges.commands;

import eu.treppi.challenges.Timing;
import eu.treppi.challenges.WorldTimer;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TimerCommand implements CommandExecutor {
    private static final String SYNTAX = "§e/timer toggle | stop | start";
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            String uuid = p.getUniqueId().toString();
            World w = p.getLocation().getWorld();

            if(!w.getName().contains(uuid)) {
                p.sendMessage("§cDiese Welt gehört dir nicht!");
                return false;
            }

            if(args.length == 0) {
                p.sendMessage(SYNTAX);
                return false;
            }

            WorldTimer wt = Timing.getTimer(w);
            if(args[0].equalsIgnoreCase("start") || args[0].equalsIgnoreCase("resume") || args[0].equalsIgnoreCase("begin")) wt.start(sender);
            else if(args[0].equalsIgnoreCase("stop") || args[0].equalsIgnoreCase("hold")) wt.stop(sender);
            else if(args[0].equalsIgnoreCase("toggle")) wt.toggle(sender);

        }

        return false;
    }
}
