package me.jeroenyt.kitpvp.handlers;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.controllers.UserController;
import me.jeroenyt.kitpvp.models.UserModel;

import javax.sql.rowset.CachedRowSet;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class UserHandler {

    private final DatabaseHandler databaseHandler;
    private final UserController userController;

    public UserHandler(DatabaseHandler databaseHandler, UserController userController) {
        this.databaseHandler = databaseHandler;
        this.userController = userController;
    }

    private boolean playerExists(UUID uuid) {
        PreparedStatement statement = databaseHandler.prepareStatement("SELECT * FROM users WHERE uuid=?", uuid.toString());
        CachedRowSet result = databaseHandler.query(statement);
        if (result != null) {
            return true;
        }

        createPlayer(uuid);
        return false;
    }

    private void createPlayer(final UUID uuid) {
        PreparedStatement statement = databaseHandler.prepareStatement("INSERT INTO users " +
                "(uuid,kills,deaths) VALUES (?,?,?)",uuid.toString(),
                "0", "0");
        databaseHandler.execute(statement);
    }

    private UserModel getPlayer(final UUID uuid) {
        try {
            PreparedStatement statement = databaseHandler.prepareStatement("SELECT * FROM users WHERE uuid=?", uuid.toString());
            CachedRowSet result = databaseHandler.query(statement);
            int kills = result.getInt("kills");
            int deaths = result.getInt("deaths");

            return new UserModel(uuid, kills, deaths);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void loadPlayer(UUID uuid) {
        if(playerExists(uuid)) {
            userController.addUser(getPlayer(uuid));
        }else{
            userController.addUser(new UserModel(uuid, 0,0));
        }
    }
    public void savePlayer(UUID uuid) {
        UserModel user = userController.getUsers().stream().filter(player -> player.getUuid().equals(uuid)).findFirst().get();

        PreparedStatement statement = databaseHandler.prepareStatement("UPDATE users SET uuid=?, kills=?, deaths=? WHERE uuid=?"
                , uuid.toString(), Integer.toString(user.getKills()), Integer.toString(user.getDeaths()), uuid.toString());
        databaseHandler.execute(statement);
    }
}
