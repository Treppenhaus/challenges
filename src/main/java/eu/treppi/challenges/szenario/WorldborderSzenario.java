package eu.treppi.challenges.szenario;

import eu.treppi.challenges.Timing;
import eu.treppi.challenges.WorldTimer;
import eu.treppi.challenges.core.ChallengesPlugin;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

import java.util.Random;

public class WorldborderSzenario implements Listener {

    static String name = "worldborder";


    public static void onEnterFirstTime(Player p, World w) {
        WorldTimer timer = Timing.getTimer(w);

        if(timer.szenarioname.equalsIgnoreCase(name)) {
            WorldBorder border = w.getWorldBorder();
            border.setCenter(p.getLocation().getBlock().getLocation());
            int r = p.getLevel();
            border.setSize(r, 1);
        }

    }

    public static void mobSpawns() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ChallengesPlugin.getInstance(), () -> {
            Bukkit.getWorlds().stream().filter(w -> {

                WorldTimer timer = Timing.getTimer(w);
                try {
                    if(timer.szenarioname.equalsIgnoreCase(name))
                        return true;
                }catch (Exception e) {

                }
                return false;

            }).forEach(w -> {
                w.getPlayers().forEach(p -> {
                    int x = randInt(10, 20);
                    int z = randInt(10, 20);
                    if(new Random().nextBoolean()) x *= -1;
                    if(new Random().nextBoolean()) z *= -1;

                    int y = 256;
                    for(int asd = 0; w.getBlockAt(x, y, z).getType() != Material.AIR; y--) {}
                    Location spawn = p.getLocation().add(x, y, z);

                    EntityType[] types = new EntityType[] {
                            EntityType.ZOMBIE,
                            EntityType.CREEPER,
                            EntityType.SKELETON
                    };

                    w.spawnEntity(spawn, types[randInt(0, types.length - 1)]);
                    p.sendMessage("spawned something at "+spawn.getX()+ " "+spawn.getY() + " "+spawn.getZ());

                });
            });
        }, 20L, 20L);
    }

    public static int randInt(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent e) {
        Player p = e.getPlayer();
        World w = p.getWorld();
        WorldTimer timer = Timing.getTimer(w);

        if(timer.szenarioname.equals(name)) {
            int r = p.getLevel();
            WorldBorder border = w.getWorldBorder();

            border.setSize(r, 1);
        }
    }


}
