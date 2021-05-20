package me.jeroenyt.kitpvp.commands.subcommands;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.commands.SubCommand;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.entity.Player;

public class SetSpawn extends SubCommand {

    private KitPvP plugin;

    @Override
    public void onCommand(KitPvP plugin, Player player, String[] args) {
        this.plugin = plugin;

        plugin.serverController.getServer().setSpawn(player.getLocation());
        player.sendMessage(Utils.format("&7U heeft de spawn met succes gezet"));

        plugin.getConfig().set("spawn", player.getLocation());
        plugin.saveConfig();
    }

    @Override
    public String name() {
        return plugin.commandManager.setspawn;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}