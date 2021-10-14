package eu.treppi.challenges.management;

import com.onarandombox.MultiverseCore.MultiverseCore;
import eu.treppi.challenges.Playerdata;
import eu.treppi.challenges.Timing;
import eu.treppi.challenges.WorldTimer;
import eu.treppi.challenges.szenario.Szenario;
import eu.treppi.challenges.szenario.WorldborderSzenario;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class WorldController {

    public static ArrayList<Szenario> szenarios = new ArrayList<>();

    public static World getWorld(Player p) {
        String uuid = p.getUniqueId().toString();
        return Bukkit.getWorld(uuid);
    }

    public static boolean hasWorld(Player p) {
        String uuid = p.getUniqueId().toString();
        return Bukkit.getWorld(uuid) != null;
    }

    public static void deleteWorld(Player p) {
        if(!hasWorld(p)) return;

        p.sendMessage("§7> Lösche Welt...");
        String uuid = p.getUniqueId().toString();

        MultiverseCore mv = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("multiverse-core");
        mv.deleteWorld(uuid);
        mv.deleteWorld(uuid+"_nether");
        mv.deleteWorld(uuid+"_end");
        mv.deleteWorld(uuid);

        p.sendMessage("§7> Löschvorgang abgeschlossen!");
        Timing.removeTimer(uuid);

    }

    public static void regenerateWorld(Player p, String generator) {
        deleteWorld(p);

        Playerdata.saveLocation(p, null);

        p.sendMessage("§7> generiere neue Welt...");
        String uuid = p.getUniqueId().toString();
        MultiverseCore mv = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("multiverse-core");
        mv.getMVWorldManager().addWorld(uuid, World.Environment.NORMAL, generator, WorldType.NORMAL, false, generator);
        mv.getMVWorldManager().addWorld(uuid+"_nether", World.Environment.NETHER, generator, WorldType.NORMAL, false, generator);
        mv.getMVWorldManager().addWorld(uuid+"_end", World.Environment.THE_END, generator, WorldType.NORMAL, false, generator);

        WorldTimer wt = new WorldTimer(uuid, 0, false);
        wt.challengefailed = false;
        wt.szenarioname = "worldborder";
        Timing.addTimer(wt);

        // link worlds
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvnp link nether "+uuid+" "+uuid+"_nether");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvnp link nether "+uuid+"_nether "+uuid);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvnp link end "+uuid+"_nether "+uuid+"_end");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvnp link end "+uuid+" "+uuid+"_end");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvnp link end "+uuid+"_end "+uuid);

        //link inventories
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvinv deletegroup "+uuid);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvinv creategroup "+uuid);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvinv addworld "+uuid+" "+uuid);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvinv addworld "+uuid+"_nether "+uuid);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvinv addworld "+uuid+"_end "+uuid);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mvinv addshares all "+uuid);

        p.sendMessage("§7> §afertig!");
    }

    public static void tryRegenerate(Player p) {
        // check for cooldown in later versions
        regenerateWorld(p, null);
    }

    public static void tryTeleportOwnWorld(Player p) {

        Location point = Playerdata.readLocation(p);
        if(!hasWorld(p)) {
            p.sendMessage("§cDu hast aktuell keine Welt!");
            return;
        }

        if(point != null) {
            p.teleport(point);
            return;
        }

        World w = getWorld(p);
        p.teleport(w.getSpawnLocation());
        p.setHealth(20);
        p.setMaxHealth(20);
        p.getInventory().clear();
        p.getActivePotionEffects().forEach(effect -> p.removePotionEffect(effect.getType()));
        p.setFoodLevel(20);
        p.setLevel(0);
        WorldborderSzenario.onEnterFirstTime(p, w);
    }
}
