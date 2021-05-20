package me.jeroenyt.kitpvp.handlers;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.models.UserModel;

import javax.sql.rowset.CachedRowSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class UserHandler {

    private final KitPvP plugin;

    public UserHandler(KitPvP plugin) {
        this.plugin = plugin;
    }

    private boolean playerExists(UUID uuid) {
        PreparedStatement statement = plugin.databaseHandler.prepareStatement("SELECT * FROM users WHERE uuid=?", uuid.toString());
        CachedRowSet result = plugin.databaseHandler.query(statement);
        if (result != null) {
            return true;
        }

        createPlayer(uuid);
        return false;
    }

    private void createPlayer(final UUID uuid) {
        PreparedStatement statement = plugin.databaseHandler.prepareStatement("INSERT INTO users " +
                "(uuid,kills,deaths) VALUES (?,?,?)",uuid.toString(),
                "0", "0");
        plugin.databaseHandler.execute(statement);
    }

    private UserModel getPlayer(final UUID uuid) {
        try {
            PreparedStatement statement = plugin.databaseHandler.prepareStatement("SELECT * FROM users WHERE uuid=?", uuid.toString());
            CachedRowSet result = plugin.databaseHandler.query(statement);
            int kills = result.getInt("kills");
            int deaths = result.getInt("deaths");

            return new UserModel(uuid, kills, deaths);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadPlayer(UUID uuid) {
        if(plugin.userHandler.playerExists(uuid)) {
            plugin.userController.addUser(plugin.userHandler.getPlayer(uuid));
        }else{
            plugin.userController.addUser(new UserModel(uuid, 0,0));
        }
    }
    public void savePlayer(UUID uuid) {
        UserModel user = plugin.userController.getUsers().stream().filter(player -> player.getUuid().equals(uuid)).findFirst().get();

        PreparedStatement statement = plugin.databaseHandler.prepareStatement("UPDATE users SET uuid=?, kills=?, deaths=? WHERE uuid=?"
                , uuid.toString(), Integer.toString(user.getKills()), Integer.toString(user.getDeaths()), uuid.toString());
        plugin.databaseHandler.execute(statement);
    }
}
