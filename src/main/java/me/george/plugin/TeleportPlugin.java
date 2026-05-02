package me.george.plugin;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TeleportPlugin extends JavaPlugin {

    private static TeleportPlugin instance;

    public static TeleportPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new TeleportListener(), this);
        getLogger().info("TeleportPlugin enabled!");
        instance = this;
        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("TeleportPlugin disabled!");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label,
            String[] args) {
                
        var commandName = cmd.getName().toLowerCase();
        getLogger().info("Received command: " + commandName + " from sender: " + sender.getName() + " with args: "
                + String.join(", ", args));

        if (commandName.equals("teleportmenu")) {
            return openTeleportGUI(sender, args);
        }

        // /teleportaddworld <worldname> command for ops or console
        if (commandName.equals("teleportaddworld")) {
            return addWorldToList(sender, args);
        }

        // /teleportremoveworld <worldname> command for ops or console
        if (commandName.equals("teleportremoveworld")) {
            return removeWorldFromList(sender, args);
        }

        // /teleportlistworlds command to list all worlds in the config
        if (commandName.equals("teleportlistworlds")) {
            return listWorlds(sender);
        }
        return false;
    }

    private boolean listWorlds(CommandSender sender) {
        List<String> worlds = getConfig().getStringList("worlds");
        if (worlds.isEmpty()) {
            sender.sendMessage("§eNo worlds configured for teleportation.");
            return true;
        }
        sender.sendMessage("§aWorlds available for teleportation:");
        for (String world : worlds) {
            sender.sendMessage("§7- " + world);
        }
        return true;
    }

    private boolean removeWorldFromList(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§eUsage: /teleportremoveworld <worldname>");
            return true;
        }
        if (sender instanceof Player player && !player.isOp()) {
            player.sendMessage("§cYou must be an operator to use this command.");
            return true;
        }
        String worldName = args[0];
        List<String> worlds = getConfig().getStringList("worlds");
        if (!worlds.contains(worldName)) {
            sender.sendMessage("§eWorld not found in the list.");
            return true;
        }
        worlds.remove(worldName);
        getConfig().set("worlds", worlds);
        saveConfig();
        sender.sendMessage("§aWorld '" + worldName + "' removed from teleport menu!");
        return true;
    }

    private boolean addWorldToList(CommandSender sender, String[] args) {
            if (args.length != 1) {
                sender.sendMessage("§eUsage: /teleportaddworld <worldname>");
                return true;
            }
        if (sender instanceof Player player && !player.isOp()) {
            player.sendMessage("§cYou must be an operator to use this command.");
            return true;
        }
        String worldName = args[0];
        List<String> worlds = getConfig().getStringList("worlds");
        if (worlds.contains(worldName)) {
            sender.sendMessage("§eWorld already in the list.");
            return true;
        }
        worlds.add(worldName);
        getConfig().set("worlds", worlds);
        saveConfig();
        sender.sendMessage("§aWorld '" + worldName + "' added to teleport menu!");
        return true;
    }

    private boolean openTeleportGUI(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command.");
            return true;
        }

        if (args.length > 0) {
            return false; // let vanilla /tp handle it
        }

        getLogger().info("Opening teleport GUI for player: " + player.getName());
        player.openInventory(TeleportGUI.createMenu(player.getWorld().getName()));

        return true;
    }
}
