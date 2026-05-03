package me.george.plugin.teleportMenu;

import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import me.george.plugin.TeleportPlugin;
import net.kyori.adventure.text.Component;

public class TeleportGUI {

    public static final NamespacedKey worldNameKey = new NamespacedKey(TeleportPlugin.getInstance(), "world");

    public static Inventory createMenu(String currentWorld) {
        FileConfiguration config = TeleportPlugin.getInstance().getConfig();
        List<String> worlds = config.getStringList("worlds");
        int size = Math.max(9, ((worlds.size() + 8) / 9) * 9); // round up to nearest 9
        Inventory inv = Bukkit.createInventory(new TeleportMenuHolder(), size,
                Component.text("Teleport Menu"));

        int slot = 0;
        for (String world : worlds) {
            Material mat = Material.COMPASS;
            ItemStack item = new ItemStack(mat);
            ItemMeta meta = item.getItemMeta();
            if (world.equals(currentWorld)) {
                continue; // skip adding the current world to the menu
            }
            meta.displayName(Component.text("§aTeleport to '" + world + "'"));
            meta.lore(List.of(Component.text("§7Click to teleport to '" + world + "'")));
            meta.getPersistentDataContainer().set(worldNameKey, PersistentDataType.STRING, world);

            item.setItemMeta(meta);
            inv.setItem(slot++, item);
        }
        return inv;
    }

}
