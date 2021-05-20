package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.controllers.InventoryController;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener {

    private final InventoryController inventoryController;

    public PlayerInteract(InventoryController inventoryController) {
        this.inventoryController = inventoryController;
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack item = player.getItemInHand();

        if (item == null || item.getType().equals(Material.AIR)) return;

        if (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) {
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(Utils.format("&cKit Selection"))) {

                Inventory inv = inventoryController.getInventory("&fKit Selection");
                player.openInventory(inv);
            }
        }
    }
}
