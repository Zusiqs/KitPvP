package me.jeroenyt.kitpvp;

import me.jeroenyt.kitpvp.commands.CommandManager;
import me.jeroenyt.kitpvp.controllers.*;
import me.jeroenyt.kitpvp.handlers.DatabaseHandler;
import me.jeroenyt.kitpvp.handlers.KitHandler;
import me.jeroenyt.kitpvp.handlers.UserHandler;
import me.jeroenyt.kitpvp.listeners.InitializationListeners;
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

        //initialize listeners
        new InitializationListeners(this);

        loadPlayer();

        new Board(this);

    }

    @Override
    public void onDisable() {
        kitHandler.saveKits();
    }

    public void log(String message){
        boolean DEBUG = true;
        if(DEBUG){
            System.out.println(message);
        }
    }

    private void loadPlayer(){
        if(Bukkit.getOnlinePlayers().isEmpty()) return;

        for(Player player : Bukkit.getOnlinePlayers()){
            userHandler.loadPlayer(player.getUniqueId());
        }
    }
    private void init(){
        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }
    }
}
