package me.jeroenyt.kitpvp;

import com.zaxxer.hikari.HikariDataSource;
import me.jeroenyt.kitpvp.commands.CommandManager;
import me.jeroenyt.kitpvp.controllers.*;
import me.jeroenyt.kitpvp.handlers.*;
import me.jeroenyt.kitpvp.listeners.*;
import me.jeroenyt.kitpvp.models.DatabaseInfoModel;
import me.jeroenyt.kitpvp.scoreboard.Board;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

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
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            if (!new File(this.getDataFolder(), "config.yml").exists()) {
                this.saveDefaultConfig();
            }
        });
    }

    private static KitPvP plugin;
    @Override
    public void onEnable() {
        plugin = this;
        init();

        //database information
        databaseInfoController = new DatabaseInfoController(this);

        DatabaseInfoModel dbInfo = databaseInfoController.getDatabaseInfo();

        //database connection
        databaseHandler = new DatabaseHandler("com.mysql.jdbc.Driver", "jdbc:mysql://" +
                dbInfo.getHost() + ":" + dbInfo.getPort() + "/" + dbInfo.getDatabase(), dbInfo.getUser(), dbInfo.getPassword(), this);
        databaseHandler.connect();

        //set up user and kit controllers
        userController = new UserController();
        kitController = new KitController();

        //kits and users
        userHandler = new UserHandler(this);
        kitHandler = new KitHandler(this);

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

    public static KitPvP getInstance(){
        return plugin;
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
