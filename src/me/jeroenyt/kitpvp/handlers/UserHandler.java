package me.jeroenyt.kitpvp.handlers;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.models.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class UserHandler {

    private final KitPvP plugin;

    public UserHandler(KitPvP plugin){
        this.plugin = plugin;
    }

    private boolean playerExists(UUID uuid) {
        try {
            ResultSet resultSet = plugin.databaseHandler.sqlQuery("SELECT * FROM users WHERE uuid=?", Arrays.asList(uuid.toString()));
            assert resultSet != null;
            if (resultSet.next()) {
                return true;
            }

            createPlayer(uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void createPlayer(final UUID uuid) {
        plugin.databaseHandler.sqlQuery("INSERT INTO users " +
                "(uuid,kills,deaths) VALUES (?,?,?)", Arrays.asList(uuid.toString(),
                "0", "0"));
    }

    private UserModel getPlayer(final UUID uuid){
        try {
            ResultSet result = plugin.databaseHandler.sqlQuery("SELECT * FROM users WHERE uuid=?", Arrays.asList(uuid.toString()));
            if (result != null) {
                while (result.next()) {
                    int kills = result.getInt("kills");
                    int deaths = result.getInt("deaths");

                    return new UserModel(uuid, kills, deaths);
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public void loadPlayer(UUID uuid){
        if(plugin.userHandler.playerExists(uuid)){
            plugin.userController.addUser(plugin.userHandler.getPlayer(uuid));
        }else{
            plugin.userController.addUser(new UserModel(uuid, 0,0));
        }
    }
    public void savePlayer(UUID uuid){
        UserModel user = plugin.userController.getUsers().stream().filter(player -> player.getUuid().equals(uuid)).findFirst().get();

        plugin.databaseHandler.sqlQuery("UPDATE users SET uuid=?, kills=?, deaths=? WHERE uuid=?"
                , Arrays.asList(uuid.toString(), Integer.toString(user.getKills()), Integer.toString(user.getDeaths()), uuid.toString()));
    }
}
