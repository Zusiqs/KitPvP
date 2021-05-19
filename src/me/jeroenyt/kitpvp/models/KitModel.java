package me.jeroenyt.kitpvp.models;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitModel {

    private final String name;
    private ItemStack[] armorContents;
    private ItemStack[] inventoryContents;
    private final Material item;
    private boolean update;

    public KitModel(String name, ItemStack[] armorContents, ItemStack[] inventoryContents, String item, boolean update){
        this.name = name;
        this.armorContents = armorContents;
        this.inventoryContents = inventoryContents;
        this.item = Material.getMaterial(item);
        this.update = update;
    }

    public String getName() {
        return name;
    }

    public ItemStack[] getArmorContents() {
        return armorContents;
    }

    public void setArmorContents(ItemStack[] armorContents) {
        this.armorContents = armorContents;
    }

    public ItemStack[] getInventoryContents() {
        return inventoryContents;
    }

    public void setInventoryContents(ItemStack[] inventoryContents) {
        this.inventoryContents = inventoryContents;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public Material getItem() {
        return item;
    }
}
