package me.jeroenyt.kitpvp.handlers;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.models.DatabaseInfoModel;
import org.bukkit.Bukkit;

import java.sql.*;
import java.util.List;

public class DatabaseHandler {

    private Connection connection;

    private Connection getConnection() {
        return connection;
    }

    private void setConnection(Connection connection) {
        this.connection = connection;
    }

    public DatabaseHandler(KitPvP plugin) {
        DatabaseInfoModel dbInfo = plugin.databaseInfoController.getDatabaseInfo();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            try {
                if (getConnection() != null && !getConnection().isClosed() && getConnection().isValid(0)) {
                    return;
                }
                Class.forName("com.mysql.jdbc.Driver");
                setConnection(DriverManager.getConnection("jdbc:mysql://" + dbInfo.getHost() + ":" + dbInfo.getPort() + "/"
                        + dbInfo.getDatabase() + "?autoReconnect=true",dbInfo.getUser(), dbInfo.getPassword()));

                Bukkit.getConsoleSender().sendMessage("Mysql connected");

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

    }

    public ResultSet sqlQuery(String sql, List<String> values) {
        try {

            PreparedStatement query = getConnection().prepareStatement(sql);
            if(values != null) {
                for (int i = 0; i < values.size(); i++) {
                    String value = values.get(i);
                    if (Character.isDigit(value.charAt(0)) && value.length() < 30) {
                        query.setInt(i + 1, Integer.parseInt(value));
                    } else {
                        query.setString(i + 1, value);
                    }
                }
                if (sql.contains("INSERT") || sql.contains("UPDATE")) {
                    query.executeUpdate();
                    return null;
                }
            }
            return query.executeQuery();
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
