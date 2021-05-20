package me.jeroenyt.kitpvp.models;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitModel {

    private final String name;
    private final ItemStack[] armorContents;
    private final ItemStack[] inventoryContents;
    private final Material item;
    private final boolean update;

    public KitModel(String name, ItemStack[] armorContents, ItemStack[] inventoryContents, Material material, boolean update) {
        this.name = name;
        this.armorContents = armorContents;
        this.inventoryContents = inventoryContents;
        this.item = material;
        this.update = update;
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public ItemStack[] getInventoryContents() {
        return inventoryContents;
    }

    public boolean isUpdate() {
        return update;
    }

    public Material getItem() {
        return item;
    }
}
