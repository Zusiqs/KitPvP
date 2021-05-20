package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerQuit implements Listener {

    private final KitPvP plugin;

    public PlayerQuit(KitPvP plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();

        removePlayer(player.getUniqueId());
    }

    private void removePlayer(UUID uuid){
        plugin.userHandler.savePlayer(uuid);
        plugin.userController.removeUser(uuid);
    }
}
