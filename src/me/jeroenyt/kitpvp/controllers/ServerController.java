package me.jeroenyt.kitpvp.controllers;

import me.jeroenyt.kitpvp.models.ServerModel;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

public class ServerController {

    private final ServerModel server;

    public ServerController(Plugin plugin){
        Location location;

        if(plugin.getConfig().contains("spawn")) {
            location = (Location) plugin.getConfig().get("spawn");
        } else {
            location = Bukkit.getWorld("world").getSpawnLocation();
        }

        server = new ServerModel(location);

    }

    public ServerModel getServer() {
        return server;
    }

}
