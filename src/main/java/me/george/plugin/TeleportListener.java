package me.george.plugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import me.george.plugin.teleportMenu.TeleportGUI;
import me.george.plugin.teleportMenu.TeleportMenuHolder;

public class TeleportListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        if (event.getClickedInventory() == null) {
            Bukkit.getLogger().info("Clicked inventory is null, ignoring click event.");
            return;
        }

        if (!(event.getClickedInventory().getHolder() instanceof TeleportMenuHolder)) {
            Bukkit.getLogger().info("Clicked inventory is not our TeleportMenuHolder, ignoring click event.");
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) {
            Bukkit.getLogger().info("Clicked item is null or air, ignoring click event.");
            return;
        }

        // Prevent taking the item out of the chest
        event.setCancelled(true);

        // Get the world name from the item's persistent data container
        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        var meta = item.getItemMeta();
        var pdc = meta.getPersistentDataContainer();
        if (!pdc.has(TeleportGUI.worldNameKey, PersistentDataType.STRING)) {
            return;
        }

        String worldName = pdc.get(TeleportGUI.worldNameKey, PersistentDataType.STRING);
        if (worldName == null) {
            return;
        }

        teleportToWorld(player, worldName);
        Bukkit.getLogger().info("Player " + player.getName() + " clicked to teleport to world: " + worldName);
    }

    private void teleportToWorld(Player player, String worldName) {

        World world = Bukkit.getWorld(worldName);

        if (world == null) {
            player.sendMessage("World '" + worldName + "' is not loaded, trying to load it...");

            // Load custom world "map"
            WorldCreator creator = new WorldCreator(worldName);
            world = creator.createWorld();
        }

        if (world == null) {
            player.sendMessage("Failed to load world: " + worldName);
            return;
        }

        Location loc = world.getSpawnLocation();

        if (player.teleport(loc)) {
            player.sendMessage("Teleported to world: " + worldName);
        } else {
            player.sendMessage("Failed to teleport to world: " + worldName);
        }
    }
}
