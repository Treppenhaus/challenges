package eu.treppi.challenges.management;

import com.onarandombox.MultiverseCore.MultiverseCore;
import eu.treppi.challenges.Timing;
import eu.treppi.challenges.WorldTimer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

public class WorldController {

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

        p.sendMessage("§7> Löschvorgang abgeschlossen!");

    }

    public static void regenerateWorld(Player p, String generator) {
        deleteWorld(p);

        p.sendMessage("§7> generiere neue Welt...");
        String uuid = p.getUniqueId().toString();
        MultiverseCore mv = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("multiverse-core");
        mv.getMVWorldManager().addWorld(uuid, World.Environment.NORMAL, generator, WorldType.NORMAL, false, generator);

        Timing.addTimer(new WorldTimer(uuid, 0, false));

        p.sendMessage("§7> §afertig!");
    }

    public static void tryRegenerate(Player p) {
        // check for cooldown in later versions
        regenerateWorld(p, null);
    }

    public static void tryTeleportOwnWorld(Player p) {
        if(!hasWorld(p)) {
            p.sendMessage("§cDu hast aktuell keine Welt!");
            return;
        }
        World w = getWorld(p);
        p.teleport(w.getSpawnLocation());
    }
}
