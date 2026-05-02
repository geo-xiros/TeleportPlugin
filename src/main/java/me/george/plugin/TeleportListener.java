
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

import net.kyori.adventure.text.Component;

public class TeleportListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        // Only handle our custom GUI
        if (!event.getView().title().equals(Component.text("Teleport Menu"))) {
            Bukkit.getLogger().info("Not our GUI, ignoring click event.");
            return;
        }

        ItemStack item = event.getCurrentItem();
        if (item == null || item.getType() == Material.AIR) {
            Bukkit.getLogger().info("Clicked item is null or air, ignoring click event.");
            return;
        }

        Material type = item.getType();

        // Prevent taking the item out of the chest
        event.setCancelled(true);
        

        // Teleport based on item type
        if (type == Material.COMPASS) {
            teleportToWorld(player, "map");
        }

        if (type == Material.CLOCK) {
            teleportToWorld(player, "world");
        }

        // log type of item clicked for debugging
        Bukkit.getLogger().info("Player " + player.getName() + " clicked on item of type: " + type);
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

        player.teleport(loc);
        player.sendMessage("Teleported to world: " + worldName);
    }

}
