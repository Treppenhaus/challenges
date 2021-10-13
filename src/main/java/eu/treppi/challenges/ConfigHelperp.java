package eu.treppi.challenges;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHelperp {
    public static FileConfiguration saveLocation(Location loc, FileConfiguration config, String path) {
        config.set(path+".loc.world", loc.getWorld().getName());
        config.set(path+".loc.x", loc.getX());
        config.set(path+".loc.y", loc.getY());
        config.set(path+".loc.z", loc.getZ());

        config.set(path+".loc.pitch", loc.getPitch());
        config.set(path+".loc.yaw", loc.getYaw());

        return config;
    }

    public static Location readLocation(FileConfiguration config, String path) {
        World world = Bukkit.getWorld(config.getString(path+".loc.world"));
        double x = config.getDouble(path+".loc.x");
        double y = config.getDouble(path+".loc.y");
        double z = config.getDouble(path+".loc.z");

        float pitch = Float.parseFloat(Double.toString(config.getDouble(path+".loc.pitch")));
        float yaw = Float.parseFloat(Double.toString(config.getDouble(path+".loc.yaw")));

        Location l = new Location(world, x, y, z);;
        l.setPitch(pitch);
        l.setYaw(yaw);

        return l;
    }
}
