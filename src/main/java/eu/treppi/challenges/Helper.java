package eu.treppi.challenges;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Helper {
    public static ItemStack createSimpleIcon(Material material, String name, String[] lore) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        stack.setItemMeta(meta);

        return stack;
    }
}
