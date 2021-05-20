package me.jeroenyt.kitpvp.commands;

import me.jeroenyt.kitpvp.KitPvP;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    public SubCommand() {

    }

    public abstract void onCommand(KitPvP plugin, Player player, String[] args);

    public abstract String name();
    public abstract String[] aliases();
}
