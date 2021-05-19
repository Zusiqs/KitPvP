package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        KitPvP plugin = KitPvP.getInstance();
        Player player = event.getPlayer();

        if(player.getItemInHand() == null || player.getItemInHand().getType().equals(Material.AIR)) return;

        if(player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.format("&cKit Selection"))){
            Inventory inv = plugin.inventoryController.getInventory("&fKit Selection");

            player.openInventory(inv);
        }
    }
}
