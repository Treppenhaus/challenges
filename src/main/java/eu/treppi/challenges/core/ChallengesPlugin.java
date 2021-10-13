package eu.treppi.challenges.core;

import org.bukkit.plugin.java.JavaPlugin;

public class ChallengesPlugin extends JavaPlugin {
    public void onEnable() {
        getLogger().info("Challenges Plugin enabled!");
    }
    public void onDisable() {
        getLogger().info("Challenges Plugin disabled!");
    }
}
