package me.jeroenyt.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class InitializationListeners {

    public InitializationListeners(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), plugin);
        Bukkit.getPluginManager().registerEvents(new BlockEvents(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawn(), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(), plugin);
    }
}
