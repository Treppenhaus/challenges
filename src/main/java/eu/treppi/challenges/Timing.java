package eu.treppi.challenges;

import eu.treppi.challenges.core.ChallengesPlugin;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;

public class Timing {
    private static final String PATH = "plugins/challenges/timers.yml";
    private static ArrayList<WorldTimer> worldtimer = loadFromConfig();


    public static void saveToConfig() {
        FileConfiguration config = getConfig();
        worldtimer.forEach( wt -> {
            config.set("timers."+wt.worldname+".time", wt.time);
            config.set("timers."+wt.worldname+".running", wt.running);
        });
        try {
            config.save(new File(PATH));
        }
        catch (Exception e) {
            // ignore
        }

    }

    public static void timingThread() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(ChallengesPlugin.getInstance(), () -> {
            worldtimer.forEach(wt -> {
                wt.tick();
                wt.display();
            });
        }, 20L, 20L);
    }

    public static WorldTimer getTimer(World world) {
        for(WorldTimer wt : worldtimer)
            if(wt.worldname.equals(world.getName()))
                return wt;

        return null;
    }

    private static ArrayList<WorldTimer> loadFromConfig() {
        ArrayList<WorldTimer> list = new ArrayList<>();
        FileConfiguration config = getConfig();

        config.getConfigurationSection("timers").getKeys(false).forEach( key -> {

            long time_ = config.getLong("timers."+key+".time");
            boolean running_ = config.getBoolean("timers."+key+".running");

            WorldTimer timer = new WorldTimer(key, time_, running_);
            list.add(timer);

        });
        return list;
    }

    private static FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(new File(PATH));
    }

    public static void addTimer(WorldTimer t) {
        worldtimer.add(t);
    }
}
