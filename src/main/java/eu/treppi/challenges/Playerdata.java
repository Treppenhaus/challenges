package eu.treppi.challenges;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class Playerdata {

    public static void saveLocation(Player p, Location loc) {
        FileConfiguration data = getData(p);
        ConfigHelperp.saveLocation(loc, data, "lastchallengelocation");
        saveData(p, data);
    }

    public static Location readLocation(Player p) {

        FileConfiguration data = getData(p);
        if(!data.contains("lastchallengelocation") || data.get("lastchallengelocation") == null) return null;
        else return ConfigHelperp.readLocation(data, "lastchallengelocation");
    }

    public static FileConfiguration getData(Player p) {
        String uuid = p.getUniqueId().toString();
        return YamlConfiguration.loadConfiguration(new File("plugins/challenges/playerdata/"+uuid+".yml"));
    }

    public static void saveData(Player p, FileConfiguration playerdata) {
        try {
            String uuid = p.getUniqueId().toString();
            playerdata.save(new File("plugins/challenges/playerdata/"+uuid+".yml"));
        }catch (Exception ignored) {

        }
    }
}