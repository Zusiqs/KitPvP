package me.jeroenyt.kitpvp.models;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class InventoryModel {

    private String name;
    private final String title;
    private final List<ItemStack> items;

    public InventoryModel(String name, String title, List<ItemStack> items) {
        this.name = name;
        this.title = title;
        this.items = items;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
