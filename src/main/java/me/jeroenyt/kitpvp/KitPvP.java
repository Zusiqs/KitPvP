package me.jeroenyt.kitpvp;

import me.jeroenyt.kitpvp.commands.CommandManager;
import me.jeroenyt.kitpvp.controllers.*;
import me.jeroenyt.kitpvp.handlers.*;
import me.jeroenyt.kitpvp.listeners.*;
import me.jeroenyt.kitpvp.models.DatabaseInfo;
import me.jeroenyt.kitpvp.scoreboard.Board;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class KitPvP extends JavaPlugin {

    private DatabaseHandler databaseHandler;
    private KitHandler kitHandler;
    private UserHandler userHandler;

    public CommandManager commandManager;

    private static KitPvP instance;

    public static KitPvP getInstance() {
        return instance;
    }

    private void loadPlayers() {
        if(Bukkit.getOnlinePlayers().isEmpty()) return;

        for(Player player : Bukkit.getOnlinePlayers()) {
            userHandler.loadPlayer(player.getUniqueId());
        }
    }

    private void init() {
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (!new File(this.getDataFolder(), "config.yml").exists()) {
                this.saveDefaultConfig();
            }
        });
    }

    @Override
    public void onEnable() {
        init();

        instance = this;

        //database information
        DatabaseInfoController databaseInfoController = new DatabaseInfoController(this);

        DatabaseInfo dbInfo = databaseInfoController.getDatabaseInfo();

        //database connection
        databaseHandler = new DatabaseHandler("com.mysql.jdbc.Driver", "jdbc:mysql://" +
                dbInfo.getHost() + ":" + dbInfo.getPort() + "/" + dbInfo.getDatabase(), dbInfo.getUser(), dbInfo.getPassword(), this);
        databaseHandler.connect();

        //set up user and kit controllers
        UserController userController = new UserController();
        KitController kitController = new KitController();

        //kits and users
        userHandler = new UserHandler(databaseHandler, userController);
        kitHandler = new KitHandler(databaseHandler, kitController);

        //server constants
        ServerController serverController = new ServerController(this);

        //inventories
        InventoryController inventoryController = new InventoryController(kitController);

        //setup commands
        commandManager = new CommandManager(this, serverController, kitController);

        loadPlayers();

        new Board(this, userController);

        //listeners
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(userHandler, serverController), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(userHandler, userController), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(this, userController), this);
        Bukkit.getPluginManager().registerEvents(new PlayerRespawn(serverController), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClick(kitController), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteract(inventoryController), this);
        Bukkit.getPluginManager().registerEvents(new BlockEvents(), this);

    }

    @Override
    public void onDisable() {
        kitHandler.saveKits();

        databaseHandler.disconnect();
    }

    public void log(String message) {
        boolean DEBUG = true;
        if(DEBUG) {
            System.out.println(message);
        }
    }

}
