package me.jeroenyt.kitpvp.inventories;

import me.jeroenyt.kitpvp.controllers.InventoryController;
import me.jeroenyt.kitpvp.controllers.KitController;
import me.jeroenyt.kitpvp.models.CustomInventory;
import me.jeroenyt.kitpvp.models.Kit;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitSelection {

    public KitSelection(InventoryController controller, KitController kitController) {
        String name = "Kit Selection";
        String title = Utils.format("&fKit Selection");
        List<ItemStack> items = new ArrayList<>();

        for(Kit kit : kitController.getKits()) {
            ItemStack item = Utils.createItem(kit.getItem(), "&c" + kit.getName(), Utils.format("&7Klik hier om deze kit te kiezen"));
            items.add(item);
        }

        if(items.size() < 1) {
            ItemStack item = Utils.createItem(Material.BEDROCK, "&cGeen kits beschikbaar", Utils.format("&7Klik hier om deze kit te kiezen"));
            items.add(item);
        }
        CustomInventory customInventory = new CustomInventory(name, title, items);

        controller.addInventory(customInventory);
    }
}
