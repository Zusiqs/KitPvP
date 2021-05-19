package me.jeroenyt.kitpvp.listeners;

import me.jeroenyt.kitpvp.KitPvP;
import me.jeroenyt.kitpvp.models.UserModel;
import me.jeroenyt.kitpvp.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final KitPvP plugin = KitPvP.getInstance();
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(plugin.userHandler.playerExists(player.getUniqueId())){
            plugin.userController.addUser(plugin.userHandler.getPlayer(player.getUniqueId()));
        }else{
            plugin.userController.addUser(new UserModel(player.getUniqueId(), 0,0));
        }

        player.teleport(plugin.serverController.getServer().getSpawn());
        Utils.giveStartItems(player);
    }
}
