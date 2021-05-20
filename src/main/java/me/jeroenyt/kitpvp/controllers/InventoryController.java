package me.jeroenyt.kitpvp.controllers;

import me.jeroenyt.kitpvp.inventories.KitSelection;
import me.jeroenyt.kitpvp.models.CustomInventory;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryController {

    private final List<CustomInventory> customInventories;
    private final List<org.bukkit.inventory.Inventory> inventories;

    private void createInventories() {
        for(CustomInventory inv : customInventories) {
            int size = (int) Math.ceil(inv.getItems().size() / 4.0) * 9;

            Inventory inventory = Bukkit.createInventory(null, size, inv.getTitle());

            int count = 1;
            for(ItemStack item : inv.getItems()) {
                inventory.setItem(count, item);
                count += 2;
            }

            getInventories().add(inventory);
        }
    }

    private List<org.bukkit.inventory.Inventory> getInventories() {
        return inventories;
    }

    public InventoryController(KitController kitController) {
        customInventories = new ArrayList<>();
        inventories = new ArrayList<>();

        //init inventory
        new KitSelection(this, kitController);

        createInventories();
    }

    public void addInventory(CustomInventory inv) {
        customInventories.add(inv);
    }

    public Inventory getInventory(String title) {
        return inventories.stream().filter(inv -> inv.getTitle().equalsIgnoreCase(Utils.format(title))).findAny().get();
    }

}
