package me.jeroenyt.kitpvp.commands.subcommands;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.commands.SubCommand;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SetKit extends SubCommand {

    private KitPvP plugin;

    @Override
    public void onCommand(KitPvP plugin, Player player, String[] args) {
        this.plugin = plugin;

        if(args.length < 2) {
            player.sendMessage(Utils.format("&7Verkeerde invoer"));
            return;
        }

        if(player.getItemInHand().getType().equals(Material.AIR) || player.getItemInHand() == null) {
            player.sendMessage(Utils.format("Dit item kan niet gebruikt worden als display item"));
            return;
        }

        String name = args[1];
        ItemStack[] armor = player.getInventory().getArmorContents();
        ItemStack[] inventory = player.getInventory().getContents();

        if(plugin.kitController.kitExists(name)) {
            player.sendMessage(Utils.format("&7De kit: &c" + name + " &7bestaat al"));
        } else {
            plugin.kitController.addKit(name, armor, inventory, player.getItemInHand().getType().toString(), true);
            player.sendMessage(Utils.format("&7Kit met succes aangemaakt"));
        }
    }

    @Override
    public String name() {
        return plugin.commandManager.setkit;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
