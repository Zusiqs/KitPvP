package me.jeroenyt.kitpvp.controllers;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.models.DatabaseInfo;

public class DatabaseInfoController {

    private final DatabaseInfo databaseInfo;


    public DatabaseInfoController(KitPvP plugin) {

        if(plugin.getConfig().contains("database.host") && plugin.getConfig().contains("database.port") &&
                plugin.getConfig().contains("database.database") && plugin.getConfig().contains("database.username") &&
                plugin.getConfig().contains("database.password")) {

            String host = plugin.getConfig().getString("database.host");
            int port = plugin.getConfig().getInt("database.port");
            String database = plugin.getConfig().getString("database.database");
            String username = plugin.getConfig().getString("database.username");
            String password = plugin.getConfig().getString("database.password");

            databaseInfo = new DatabaseInfo(host, port,database, username, password);

            plugin.log("Credentials found");

            return;
        }

        databaseInfo = new DatabaseInfo("localhost", 3306, "database", "root", "password");
    }


    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }
}
