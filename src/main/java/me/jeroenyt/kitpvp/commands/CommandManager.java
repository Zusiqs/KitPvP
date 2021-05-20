package me.jeroenyt.kitpvp.commands;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.commands.subcommands.SetKit;
import me.jeroenyt.kitpvp.commands.subcommands.SetSpawn;
import me.jeroenyt.kitpvp.controllers.KitController;
import me.jeroenyt.kitpvp.controllers.ServerController;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private final ArrayList<SubCommand> commands;
    private final KitPvP plugin;
    private KitController kitController;
    private ServerController serverController;

    public CommandManager(KitPvP plugin, ServerController serverController, KitController kitController) {
        commands = new ArrayList<>();
        this.plugin = plugin;
        this.serverController = serverController;
        this.kitController = kitController;
        setup();
    }

    public String main = "kitpvp";

    public String setspawn = "setspawn";
    public String setkit = "setkit";

    public void setup() {
        plugin.getCommand(main).setExecutor(this);

        this.commands.add(new SetSpawn(plugin, serverController));
        this.commands.add(new SetKit(kitController));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Utils.format("&cJe bent geen speler"));
            return true;
        }

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase(main)) {
            if (args.length == 0) {
                //TODO: Add help message
                return true;
            }
            if (!player.isOp()) {
                player.sendMessage(Utils.format("&cJe hebt hier geen permissies voor"));
                return true;
            }

            SubCommand target = this.get(args[0]);

            if (target == null) {
                //TODO: Help Message
                player.sendMessage(Utils.format("&cHet command dat je ingevuld hebt is niet juist"));
                return true;
            }

            try {
                target.onCommand(player, args);
                return true;
            } catch (Exception e) {
                player.sendMessage(Utils.format("&7Er is iets fout gegaan!"));
                e.printStackTrace();
            }

        }
        return false;
    }

    private SubCommand get(String name) {

        for (SubCommand scommand : this.commands) {
            if (scommand.name().equals(name)) {
                return scommand;
            }

            String[] aliases;
            int length = (aliases = scommand.aliases()).length;

            for (int var5 = 0; var5 < length; ++var5) {
                String alias = aliases[var5];
                if (name.equalsIgnoreCase(alias)) {
                    return scommand;
                }
            }
        }
        return null;
    }
}
