package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerDeath implements Listener {

    private final KitPvP plugin;

    public PlayerDeath(KitPvP plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    private void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.setKeepInventory(true);

        plugin.userController.getUser(player.getUniqueId()).increaseDeaths();

        if(event.getEntity().getKiller() != null) {
            Player killer = event.getEntity().getKiller();
            plugin.userController.getUser(killer.getUniqueId()).increaseKills();
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().respawn();
            }
        }.runTaskLater(plugin, 1L);
    }
}
