package me.george.plugin;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


public class TeleportPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TeleportListener(), this);
        getLogger().info("TeleportPlugin enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TeleportPlugin disabled!");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {

        // if not a valid player
        if (!(sender instanceof Player player)) {
            return false; // Let vanilla handle it (e.g. console usage)
        }
        
        // If any arguments are provided
        if (args.length > 0) {
            return false; // let vanilla /tp handle it
        }

        // if label is not "tpgui"
        if (!cmd.getName().equalsIgnoreCase("tpgui")) {
            return false; // Let vanilla handle it
        }

        getLogger().info("Opening teleport GUI for player: " + player.getName());

        // Open the teleport GUI
        player.openInventory(TeleportGUI.createMenu(player.getWorld().getName()));
        
        return true; // You handled it
    }
}
