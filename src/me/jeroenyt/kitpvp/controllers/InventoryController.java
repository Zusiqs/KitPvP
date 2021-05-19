package me.jeroenyt.kitpvp.controllers;

import me.jeroenyt.kitpvp.inventories.KitSelection;
import me.jeroenyt.kitpvp.models.InventoryModel;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryController {

    private final List<InventoryModel> inventoryModels;
    private final List<Inventory> inventories;

    public InventoryController(){
        inventories = new ArrayList<>();
        inventoryModels = new ArrayList<>();

        //init inventory
        new KitSelection(this);

        createInventories();
    }

    private void createInventories() {
        for(InventoryModel inv : inventoryModels){
            int size = (int) Math.ceil(inv.getItems().size() / 4.0) * 9;

            Inventory inventory = Bukkit.createInventory(null, size, inv.getTitle());

            int count = 1;
            for(ItemStack item : inv.getItems()){
                inventory.setItem(count, item);
                count += 2;
            }

            getInventories().add(inventory);
        }
    }

    public void addInventory(InventoryModel inv){
        inventoryModels.add(inv);
    }

    public InventoryModel getInventoryModel(String inventory){
        return inventoryModels.stream().filter(inv -> inv.getName().equalsIgnoreCase(inventory)).findAny().get();
    }

    public void addItem(String name, ItemStack item){
        inventoryModels.stream().filter(inv -> inv.getName().equalsIgnoreCase(name)).findAny().get().getItems().add(item);
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public Inventory getInventory(String title){
        return inventories.stream().filter(inv -> inv.getTitle().equalsIgnoreCase(Utils.format(title))).findAny().get();
    }

}
