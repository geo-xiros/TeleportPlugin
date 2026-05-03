package me.george.plugin.commands;

import me.george.plugin.TeleportPlugin;

public class TeleportListWorldsCommand implements org.bukkit.command.CommandExecutor {

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command,
            String label, String[] args) {

        var worlds = TeleportPlugin.getInstance().getConfig().getStringList("worlds");
        sender.sendMessage("Teleportation is enabled in the following worlds:");
        for (var world : worlds) {
            sender.sendMessage("- " + world);
        }
        return true;
    }
}
