package me.jeroenyt.kitpvp.handlers;
import com.sun.rowset.CachedRowSetImpl;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.*;

public class DatabaseHandler {

    private HikariDataSource dataSource;
    private final JavaPlugin plugin;

    public DatabaseHandler(String className, String jdbcURL, String username, String password, JavaPlugin plugin) {
        this.plugin = plugin;

        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(jdbcURL);

        config.setUsername(username);
        config.setPassword(password);

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(0);

        try {
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        isConnected();
    }

    public void disconnect() {
        dataSource.close();
    }

    public CachedRowSet query(final PreparedStatement preparedStatement) {
        CachedRowSet rowSet = null;

        if (isConnected()) {
            try {
                ExecutorService exe = Executors.newCachedThreadPool();

                Future<CachedRowSet> future = exe.submit(() -> {
                    try {
                        ResultSet resultSet = preparedStatement.executeQuery();

                        CachedRowSet cachedRowSet = new CachedRowSetImpl();
                        cachedRowSet.populate(resultSet);
                        resultSet.close();

                        preparedStatement.getConnection().close();

                        if (cachedRowSet.next()) {
                            return cachedRowSet;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    return null;
                });

                if (future.get() != null) {
                    rowSet = future.get();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return rowSet;
    }

    public void execute(final PreparedStatement preparedStatement) {
        if (isConnected()) {
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    preparedStatement.execute();

                    preparedStatement.getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public PreparedStatement prepareStatement(String query, String... vars) {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);

            int x = 0;

            if (query.contains("?") && vars.length != 0) {
                for (String var : vars) {
                    x++;
                    preparedStatement.setString(x, var);
                }
            }

            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean isConnected() {
        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }


}