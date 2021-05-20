package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.models.KitModel;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    private final KitPvP plugin;

    public InventoryClick(KitPvP plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    private void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();

        if(!player.getInventory().contains(Material.WATCH)) return;

        if (inv == null) return;

        ItemStack is = event.getCurrentItem();
        if (is == null) return;

        if (is.getItemMeta() == null) return;

        event.setCancelled(true);
        player.updateInventory();

        if (inv.getName().equals(Utils.format("&fKit Selection"))) {
            String name = is.getItemMeta().getDisplayName().substring(2);

            KitModel kit = plugin.kitController.getKit(name);

            player.getInventory().setContents(kit.getInventoryContents());
            player.getInventory().setArmorContents(kit.getArmorContents());
            player.closeInventory();
        }
    }
}
