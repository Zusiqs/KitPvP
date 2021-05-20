package me.jeroenyt.kitpvp.controllers;

import me.jeroenyt.kitpvp.models.Kit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitController {

    private final List<Kit> kits;

    public KitController() {
        kits = new ArrayList<>();
    }

    public void addKit(String name, ItemStack[] armor, ItemStack[] inventory, Material material, boolean update) {
        Kit kit = new Kit(name, armor, inventory, material, update);
        kits.add(kit);
    }

    public List<Kit> getKits() {
        return kits;
    }

    public Kit getKit(String name) {
        return kits.stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findAny().get();
    }

    public boolean kitExists(String name) {
        return kits.stream().anyMatch(k -> k.getName().equalsIgnoreCase(name));
    }

}
