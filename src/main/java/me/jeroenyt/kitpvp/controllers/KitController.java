package me.jeroenyt.kitpvp.controllers;

import me.jeroenyt.kitpvp.models.KitModel;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitController {

    private final List<KitModel> kits;

    public KitController() {
        kits = new ArrayList<>();
    }

    public void addKit(String name, ItemStack[] armor, ItemStack[] inventory, String item, boolean update) {
        KitModel kit = new KitModel(name, armor, inventory, item, update);
        kits.add(kit);
    }

    public List<KitModel> getKits() {
        return kits;
    }

    public KitModel getKit(String name) {
        return kits.stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findAny().get();
    }

    public boolean kitExists(String name) {
        return kits.stream().anyMatch(k -> k.getName().equalsIgnoreCase(name));
    }

}
