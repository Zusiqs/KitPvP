package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import org.bukkit.Bukkit;

public class InitializationListeners {

    public InitializationListeners(KitPvP plugin){
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawn(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new BlockEvents(), plugin);
    }
}
