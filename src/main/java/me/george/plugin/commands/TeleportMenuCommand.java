package me.george.plugin.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.george.plugin.TeleportGUI;
import org.bukkit.Bukkit;

public class TeleportMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        if (args.length > 0) {
            return false; // let vanilla /tp handle it
        }

        // Open the teleport menu for the player
        Bukkit.getLogger().info("Opening teleport GUI for player: " + player.getName());
        player.openInventory(TeleportGUI.createMenu(player.getWorld().getName()));        

        return true;
    }
}
