package me.jeroenyt.kitpvp.handlers;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.models.KitModel;
import me.jeroenyt.kitpvp.utils.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;

import javax.sql.rowset.CachedRowSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class KitHandler {

    private final KitPvP plugin;
    private final DatabaseHandler dbHandler;

    private void createKit(KitModel kit) {
        PreparedStatement statement = dbHandler.prepareStatement("INSERT INTO kits (name, armor, inventory) VALUES (?,?,?)",
                kit.getName(), ConvertToString(kit.getArmorContents()), ConvertToString(kit.getInventoryContents()));
        dbHandler.execute(statement);
    }

    private void updateKit(KitModel kit) {
        if (kit.isUpdate()) {
            PreparedStatement statement =
                    dbHandler.prepareStatement("UPDATE kits SET armor=?, inventory=? WHERE name=?",
                            ConvertToString(kit.getArmorContents()), ConvertToString(kit.getInventoryContents()), kit.getName());
            dbHandler.execute(statement);
        }
    }

    private void loadKits() {
        PreparedStatement statement = plugin.databaseHandler.prepareStatement("SELECT * FROM kits");
        CachedRowSet result = plugin.databaseHandler.query(statement);

        try {
                String kit_name = result.getString("name");
                String armor = result.getString("armor");
                String inventory = result.getString("inventory");
                String item = result.getString("item");

                plugin.kitController.addKit(kit_name, serializeItemArray(armor), serializeItemArray(inventory), item, false);

        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    private String ConvertToString(ItemStack[] items) {
        StringBuilder converted_inv = new StringBuilder();
        for (ItemStack item : items) {
            converted_inv.append(ItemStackSerializer.serialize(item)).append("___");
        }
        return converted_inv.toString();
    }

    private ItemStack[] serializeItemArray(String content) {
        String[] splitted_string = content.split("___");
        ItemStack[] items = new ItemStack[splitted_string.length];
        for(int i = 0; i < splitted_string.length; i++) {
            items[i] = ItemStackSerializer.deserialize(splitted_string[i]);
        }
        return items;
    }

    public KitHandler(KitPvP plugin) {
        this.plugin = plugin;
        dbHandler = plugin.databaseHandler;
        loadKits();
    }

    public boolean kitExist(String kit) {
        PreparedStatement statement = dbHandler.prepareStatement("SELECT * FROM kits WHERE name=?", kit);
        CachedRowSet result = dbHandler.query(statement);
        return result != null;
    }

    public void saveKits() {
        for(KitModel kit : plugin.kitController.getKits()) {
            if(kitExist(kit.getName())) {
                updateKit(kit);
            }else{
                createKit(kit);
            }
        }
    }

}
