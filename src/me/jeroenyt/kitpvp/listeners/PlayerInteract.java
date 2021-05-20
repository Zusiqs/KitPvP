package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteract implements Listener {

    private final KitPvP plugin;

    public PlayerInteract(KitPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR)) return;

        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.format("&cKit Selection"))) {
                Inventory inv = plugin.inventoryController.getInventory("&fKit Selection");

                player.openInventory(inv);
            }
        }
    }
}
