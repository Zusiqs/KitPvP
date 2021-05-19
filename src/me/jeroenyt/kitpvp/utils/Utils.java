package me.jeroenyt.kitpvp.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class Utils {

    public static String format(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(Utils.format(name));
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    public static void giveStartItems(Player player){
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[player.getInventory().getArmorContents().length]);

        player.getInventory().setItem(4, createGuiItem(Material.WATCH, "&cKit Selection", Utils.format("Hiermee kan je kits kiezen")));
    }
}
