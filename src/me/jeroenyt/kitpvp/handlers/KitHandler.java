package me.jeroenyt.kitpvp.handlers;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.models.KitModel;
import me.jeroenyt.kitpvp.utils.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class KitHandler {

    private final KitPvP plugin = KitPvP.getInstance();
    private final DatabaseHandler dbHandler = plugin.databaseHandler;

    public KitHandler(){
        loadKits();
    }
    public boolean kitExist(String kit){
        try {
            return dbHandler.sqlQuery("SELECT * FROM kits WHERE name=?", Arrays.asList(kit)).next();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public void createKit(KitModel kit) {
        dbHandler.sqlQuery("INSERT INTO kits (name, armor, inventory) VALUES (?,?,?)",
                Arrays.asList(kit.getName(), ConvertToString(kit.getArmorContents()), ConvertToString(kit.getInventoryContents())));
    }

    public void updateKit(KitModel kit) {
        if (kit.isUpdate()) {
            dbHandler.sqlQuery("UPDATE kits SET armor=?, inventory=?" +
                            " WHERE name=?",
                    Arrays.asList(ConvertToString(kit.getArmorContents()), ConvertToString(kit.getInventoryContents()), kit.getName()));
        }
    }

    public void saveKits(){
        for(KitModel kit : plugin.kitController.getKits()){
            if(kitExist(kit.getName())){
                updateKit(kit);
            }else{
                createKit(kit);
            }
        }
    }

    public void loadKits(){
        try{
            ResultSet result = plugin.databaseHandler.sqlQuery("SELECT * FROM kits", null);
            while(result.next()){
                String kit_name = result.getString("name");
                String armor = result.getString("armor");
                String inventory = result.getString("inventory");
                String item = result.getString("item");

                plugin.kitController.addKit(kit_name, serializeItemArray(armor), serializeItemArray(inventory), item,false);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private String ConvertToString(ItemStack[] items){
        StringBuilder converted_inv = new StringBuilder();
        for (ItemStack item : items) {
            converted_inv.append(ItemStackSerializer.serialize(item)).append("___");
        }
        return converted_inv.toString();
    }

    private ItemStack[] serializeItemArray(String content){
        String[] splitted_string = content.split("___");
        ItemStack[] items = new ItemStack[splitted_string.length];
        for(int i = 0; i < splitted_string.length; i++){
            items[i] = ItemStackSerializer.deserialize(splitted_string[i]);
        }
        return items;
    }
}
