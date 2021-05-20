package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.controllers.ServerController;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawn implements Listener {

    private final ServerController serverController;

    public PlayerRespawn(ServerController serverController) {
        this.serverController = serverController;
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    private void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        event.setRespawnLocation(serverController.getServer().getSpawn());
        Utils.giveStartItems(player);

    }
}
