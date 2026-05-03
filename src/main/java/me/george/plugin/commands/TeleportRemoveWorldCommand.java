package me.george.plugin.commands;

import me.george.plugin.TeleportPlugin;

public class TeleportRemoveWorldCommand implements org.bukkit.command.CommandExecutor {

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command,
            String label, String[] args) {

        if (!sender.hasPermission("teleportplugin.removeworld")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /teleportremoveworld <worldname>");
            return true;
        }

        var worldName = args[0];
        var config = TeleportPlugin.getInstance().getConfig();
        var worlds = config.getStringList("worlds");

        if (!worlds.contains(worldName)) {
            sender.sendMessage("World " + worldName + " is not in the teleportation list.");
            return true;
        }

        worlds.remove(worldName);
        config.set("worlds", worlds);
        TeleportPlugin.getInstance().saveConfig();

        sender.sendMessage("World " + worldName + " has been removed from the teleportation list.");
        return true;
    }

}
