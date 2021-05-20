package me.jeroenyt.kitpvp;

import me.jeroenyt.kitpvp.commands.CommandManager;
import me.jeroenyt.kitpvp.controllers.*;
import me.jeroenyt.kitpvp.handlers.*;
import me.jeroenyt.kitpvp.listeners.*;
import me.jeroenyt.kitpvp.scoreboard.Board;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class KitPvP extends JavaPlugin {

    public UserController userController;
    public KitController kitController;
    public ServerController serverController;
    public InventoryController inventoryController;
    public DatabaseInfoController databaseInfoController;

    public CommandManager commandManager;

    public DatabaseHandler databaseHandler;
    public KitHandler kitHandler;
    public UserHandler userHandler;

    private void loadPlayers() {
        if(Bukkit.getOnlinePlayers().isEmpty()) return;

        for(Player player : Bukkit.getOnlinePlayers()) {
            userHandler.loadPlayer(player.getUniqueId());
        }
    }

    private void init() {
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }
    }

    @Override
    public void onEnable() {
        init();

        //database information
        databaseInfoController = new DatabaseInfoController(this);

        //database connection
        databaseHandler = new DatabaseHandler(this);

        //set up user and kit controllers
        userController = new UserController();
        kitController = new KitController();

        //kits and users
        kitHandler = new KitHandler(this);
        userHandler = new UserHandler(this);

        //server constants
        serverController = new ServerController(this);

        //inventories
        inventoryController = new InventoryController(this);

        //setup commands
        commandManager = new CommandManager(this);

        loadPlayers();

        new Board(this);

        //listeners
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawn(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockEvents(), this);
    }

    @Override
    public void onDisable() {
        kitHandler.saveKits();
    }

    public void log(String message) {
        boolean DEBUG = true;
        if(DEBUG) {
            System.out.println(message);
        }
    }

}
