package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.controllers.UserController;
import me.jeroenyt.kitpvp.handlers.UserHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuit implements Listener {

    private final UserHandler userHandler;
    private final UserController userController;

    public PlayerQuit(UserHandler userHandler, UserController userController) {
        this.userHandler = userHandler;
        this.userController = userController;
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        removePlayer(player.getUniqueId());
    }

    private void removePlayer(UUID uuid) {
        userHandler.savePlayer(uuid);
        userController.removeUser(uuid);
    }
}
