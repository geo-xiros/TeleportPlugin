package me.george.plugin;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import net.kyori.adventure.text.Component;

public class TeleportGUI {


public static Inventory createMenu(String currentWorld) {
    Inventory inv = Bukkit.createInventory((org.bukkit.inventory.InventoryHolder) null, 9, Component.text("Teleport Menu"));

    if(currentWorld.equals("map")) {
        ItemStack compass = new ItemStack(Material.CLOCK);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.displayName(Component.text("§aTeleport to Main World"));
        compassMeta.lore(List.of(Component.text("§7Click to teleport to the 'map' world")));
        compass.setItemMeta(compassMeta);
        inv.setItem(0, compass);
    } else {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta compassMeta = compass.getItemMeta();
        compassMeta.displayName(Component.text("§aTeleport to Map"));
        compassMeta.lore(List.of(Component.text("§7You are currently in the main world")));
        compass.setItemMeta(compassMeta);
        inv.setItem(0, compass);
    }

    return inv;
}

}
