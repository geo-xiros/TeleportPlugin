package me.george.plugin.teleportMenu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class TeleportMenuHolder implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return null; // Not used, as Bukkit manages the inventory instance
    }
}
