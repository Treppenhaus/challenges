package eu.treppi.challenges;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class WorldTimer {
    public String worldname, szenarioname;
    public long time;
    public boolean running;
    public boolean challengefailed;

    public WorldTimer(String name, long time, boolean running) {
        this.worldname = name;
        this.time = time;
        this.running = running;
    }

    public void tick() {
        if(running) {
            // check for players
            int players = Bukkit.getWorlds().stream().filter(world -> world.getName().contains(worldname)).mapToInt(world -> world.getPlayers().size()).sum();
            if(players > 0) {
                time += 1000;
            }
        }
    }

    public void display() {
        getWorlds().forEach(w -> {
            w.getPlayers().forEach(p -> {
                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getDisplay()));
            });
        });
    }

    public String getDisplay() {
        long calctime = time;

        long days = TimeUnit.MILLISECONDS.toDays(calctime);
        calctime -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(calctime);
        calctime -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(calctime);
        calctime -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(calctime);

        String timestring = days > 0 ? days + "d " : ""
                + (hours > 0 ? hours + "h " : "")
                + (minutes > 0 ? minutes + "m " : "")
                + seconds + "s";


        return "§7Timer: "+ (running ? "§e§l" : "§c") + timestring + (running ? "§r" : " §7(GESTOPPT)");
    }

    public Stream<World> getWorlds() {
        return Bukkit.getWorlds().stream().filter(world -> world.getName().contains(worldname));
    }

    public void start(CommandSender who) {
        if(challengefailed) {
            if(who != null) who.sendMessage("§cDer Timer kann nicht mehr gestartet werden, da die Challenge fehlgeschlagen ist!");
            return;
        }

        if(!running) {
            running = true;
            if(who != null) who.sendMessage("§aTimer gestartet!");
        }
        else if(who != null) who.sendMessage("§6Der Timer ist bereits gestartet!");
    }

    public void stop(CommandSender who) {
        if(running) {
            running = false;
            if(who != null) who.sendMessage("§aTimer §cgestoppt§a!");
        }
        else if(who != null) who.sendMessage("§cDer Timer war bereits gestoppt!");
    }

    public void toggle(CommandSender who) {
        if(running) stop(who);
        else start(who);
    }
}
