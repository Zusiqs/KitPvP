package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final KitPvP plugin;

    public PlayerJoin(KitPvP plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        plugin.userHandler.loadPlayer(player.getUniqueId());

        player.teleport(plugin.serverController.getServer().getSpawn());
        Utils.giveStartItems(player);
    }
}
