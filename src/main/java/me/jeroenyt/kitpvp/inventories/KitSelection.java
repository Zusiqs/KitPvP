package me.jeroenyt.kitpvp.inventories;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.controllers.InventoryController;
import me.jeroenyt.kitpvp.models.InventoryModel;
import me.jeroenyt.kitpvp.models.KitModel;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitSelection {

    public KitSelection(InventoryController controller, KitPvP plugin) {
        String name = "Kit Selection";
        String title = Utils.format("&fKit Selection");
        List<ItemStack> items = new ArrayList<>();

        for(KitModel kit : plugin.kitController.getKits()) {
            ItemStack item = Utils.createGuiItem(kit.getItem(), "&c" + kit.getName(), Utils.format("&7Klik hier om deze kit te kiezen"));
            items.add(item);
        }

        if(items.size() < 1) {
            ItemStack item = Utils.createGuiItem(Material.BEDROCK, "&cGeen kits beschikbaar", Utils.format("&7Klik hier om deze kit te kiezen"));
            items.add(item);
        }
        InventoryModel inventory = new InventoryModel(name, title, items);

        controller.addInventory(inventory);
    }
}
