package eu.treppi.challenges.commands;

import eu.treppi.challenges.MenuItems;
import eu.treppi.challenges.management.WorldController;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class MenuCommand implements CommandExecutor, Listener {
    private static final String INVENTORY_NAME = "§9§lChallenges Menü";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            p.openInventory(getMenuInventory(p));
        }
        return false;
    }



    private Inventory getMenuInventory(Player p) {
        Inventory inv = Bukkit.createInventory(null, 9*3, INVENTORY_NAME);
        inv.setItem(10, MenuItems.REGENERATE_WORLD);
        inv.setItem(11, MenuItems.TELEPORT_OWN_WORLD);
        return inv;
    }

    @EventHandler
    public void onItemselect(InventoryClickEvent e) {
        if(e.getView().getTitle().equalsIgnoreCase(INVENTORY_NAME)) {
            Player p = (Player) e.getWhoClicked();
            e.setCancelled(true);
            if(e.getCurrentItem() == null) return;

            ItemStack current = e.getCurrentItem();
            if(current.equals(MenuItems.REGENERATE_WORLD)) {
                WorldController.tryRegenerate(p);
            }
            else if(current.equals(MenuItems.TELEPORT_OWN_WORLD)) {
                WorldController.tryTeleportOwnWorld(p);
            }

        }
    }
}
