package eu.treppi.challenges;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MenuItems {
    public static final ItemStack REGENERATE_WORLD = Helper.createSimpleIcon(
            Material.GRASS_BLOCK, "§aWelt neu generieren", new String[]{"§7Klicke, um die Welt", "§7neu zu generieren!"}
    );
    public static final ItemStack TELEPORT_OWN_WORLD = Helper.createSimpleIcon(
            Material.ENDER_PEARL, "§aZur Welt teleportieren", new String[]{"§7Klicke, um dich zu deiner", "§7Welt zu teleportieren!"}
    );
}
