package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.controllers.UserController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDeath implements Listener {

    private final KitPvP plugin;
    private final UserController userController;

    public PlayerDeath(KitPvP plugin, UserController userController) {
        this.plugin = plugin;
        this.userController = userController;
    }
    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setKeepInventory(true);

        userController.getUser(player.getUniqueId()).increaseDeaths();

        Player killer = event.getEntity().getKiller();

        if(killer != null) {
            userController.getUser(killer.getUniqueId()).increaseKills();
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(plugin, 1L);
    }
}
