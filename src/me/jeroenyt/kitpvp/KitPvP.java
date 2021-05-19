package me.jeroenyt.kitpvp;

import me.jeroenyt.kitpvp.commands.CommandManager;
import me.jeroenyt.kitpvp.controllers.*;
import me.jeroenyt.kitpvp.handlers.DatabaseHandler;
import me.jeroenyt.kitpvp.handlers.KitHandler;
import me.jeroenyt.kitpvp.handlers.UserHandler;
import me.jeroenyt.kitpvp.listeners.InitializationListeners;
import me.jeroenyt.kitpvp.scoreboard.Board;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class KitPvP extends JavaPlugin {

    private static KitPvP plugin;

    public UserController userController;
    public KitController kitController;
    public ServerController serverController;
    public InventoryController inventoryController;
    public DatabaseInfoController databaseInfoController;

    public CommandManager commandManager;

    public DatabaseHandler databaseHandler;
    public KitHandler kitHandler;
    public UserHandler userHandler;

    @Override
    public void onEnable() {
        plugin = this;

        init();

        //database information
        databaseInfoController = new DatabaseInfoController(this);

        //database connection
        databaseHandler = new DatabaseHandler(this);

        //set up user and kit controllers
        userController = new UserController();
        kitController = new KitController();

        //kits and users
        kitHandler = new KitHandler();
        userHandler = new UserHandler();

        //server constants
        serverController = new ServerController(this);

        //inventories
        inventoryController = new InventoryController();

        //setup commands
        commandManager = new CommandManager(this);

        //initialize listeners
        new InitializationListeners(this);

        new Board();
    }

    @Override
    public void onDisable() {
        kitHandler.saveKits();
    }

    public static KitPvP getInstance(){
        return plugin;
    }

    public void log(String message){
        boolean DEBUG = true;
        if(DEBUG){
            System.out.println(message);
        }
    }

    private void init(){
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }
    }
}
