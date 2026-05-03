package me.george.plugin;

import org.bukkit.plugin.java.JavaPlugin;
import me.george.plugin.commands.TeleportAddWorldCommand;
import me.george.plugin.commands.TeleportListWorldsCommand;
import me.george.plugin.commands.TeleportMenuCommand;
import me.george.plugin.commands.TeleportRemoveWorldCommand;


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

        // add TeleportMenuCommand as the executor for the /teleportmenu command
        this.getCommand("teleportmenu").setExecutor(new TeleportMenuCommand());
        this.getCommand("teleportaddworld").setExecutor(new TeleportAddWorldCommand());
        this.getCommand("teleportremoveworld").setExecutor(new TeleportRemoveWorldCommand());
        this.getCommand("teleportlistworlds").setExecutor(new TeleportListWorldsCommand());

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("TeleportPlugin disabled!");
    }
}
