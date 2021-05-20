package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.controllers.ServerController;
import me.jeroenyt.kitpvp.handlers.UserHandler;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final UserHandler userHandler;
    private final ServerController serverController;

    public PlayerJoin(UserHandler userHandler, ServerController serverController) {
        this.userHandler = userHandler;
        this.serverController = serverController;
    }
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        userHandler.loadPlayer(player.getUniqueId());
        player.teleport(serverController.getServer().getSpawn());
        Utils.giveStartItems(player);
    }
}
