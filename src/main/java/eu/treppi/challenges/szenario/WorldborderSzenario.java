package eu.treppi.challenges.szenario;

import eu.treppi.challenges.ConfigHelperp;
import eu.treppi.challenges.Playerdata;
import eu.treppi.challenges.Timing;
import eu.treppi.challenges.WorldTimer;
import eu.treppi.challenges.management.WorldController;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.network.protocol.game.PacketPlayOutWorldEvent;
import net.minecraft.world.level.border.WorldBorder;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class WorldborderSzenario implements Listener {

    static String name = "worldborder";


    public static void onEnterFirstTime(Player p, World w) {
        WorldTimer timer = Timing.getTimer(w);

        if(timer.szenarioname.equalsIgnoreCase(name)) {
            int r = Math.max(1, p.getLevel());
            Location center = p.getLocation();
            setWorldborderCenter(p, center);
            sendWorldBorder(p, r, center);

            w.setTime(12000L);
        }

    }

    public static void sendWorldBorder(Player player, double size, Location centerLocation) {
        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) centerLocation.getWorld()).getHandle();
        worldBorder.setCenter(centerLocation.getBlockX(), centerLocation.getBlockZ());

        worldBorder.setWarningDistance(0);
        worldBorder.setWarningTime(0);
        worldBorder.transitionSizeBetween(size - 1d, size, 20);
        worldBorder.setSize(size);

        ((CraftPlayer) player).getHandle().b.sendPacket(new ClientboundInitializeBorderPacket(worldBorder));
    }

    public static void sendWorldBorder(Player p) {
        int r = Math.max(1, p.getLevel());
        Location center = getWorldborderCenter(p);
        sendWorldBorder(p, r, center);
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        WorldTimer timer = Timing.getTimer(w);

        if(timer.szenarioname.equals(name)) {
            int r = Math.max(1, p.getLevel());
            Location center = getWorldborderCenter(p);
            sendWorldBorder(p, r, center);
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();

        WorldTimer timer = Timing.getTimer(w);

        if(timer.szenarioname.equals(name)) {
            if(w.getName().contains(p.getUniqueId().toString())) {
                sendWorldBorder(p);
            }
        }
    }

    public static void setWorldborderCenter(Player p, Location loc) {
        FileConfiguration plaeyrdata = Playerdata.getData(p);
        ConfigHelperp.saveLocation(loc, plaeyrdata, "worldborder.center");
        Playerdata.saveData(p, plaeyrdata);
    }

    public static Location getWorldborderCenter(Player p) {
        FileConfiguration plaeyrdata = Playerdata.getData(p);
        return ConfigHelperp.readLocation(plaeyrdata, "worldborder.center");
    }

}
