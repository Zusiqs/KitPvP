package me.jeroenyt.kitpvp.models;

import me.jeroenyt.kitpvp.KitPvP;
import org.bukkit.Location;

public class ServerModel {

    private Location spawn;

    public ServerModel(Location spawn){
        this.spawn = spawn;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

}
