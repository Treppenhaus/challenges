package eu.treppi.challenges.core;

import com.onarandombox.MultiverseCore.commands.SpawnCommand;
import eu.treppi.challenges.GameController;
import eu.treppi.challenges.LobbyTeleport;
import eu.treppi.challenges.SetspawnCommand;
import eu.treppi.challenges.Timing;
import eu.treppi.challenges.commands.MenuCommand;
import eu.treppi.challenges.commands.TimerCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ChallengesPlugin extends JavaPlugin {
    private static ChallengesPlugin instance;
    public void onEnable() {
        getLogger().info("Challenges Plugin enabled!");
        registerCommands();
        registerListeners();
        instance = this;

        Timing.timingThread();
    }
    public void onDisable() {
        getLogger().info("Challenges Plugin disabled!");
        Timing.saveToConfig();
    }

    public static ChallengesPlugin getInstance() {
        return instance;
    }

    private void registerCommands() {
        getCommand("menu").setExecutor(new MenuCommand());
        getCommand("timer").setExecutor(new TimerCommand());
        getCommand("spawn").setExecutor(new LobbyTeleport());
        getCommand("setspawn").setExecutor(new SetspawnCommand());
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new MenuCommand(), this);
        pm.registerEvents(new GameController(), this);
        pm.registerEvents(new LobbyTeleport(), this);
    }

    public static void send(Player p, String msg) {
        p.sendMessage("§9challenges§7 | "+msg);
    }
}
