package me.jeroenyt.kitpvp.models;

import org.bukkit.Location;

public class Server {

    private Location spawn;

    public Server(Location spawn) {
        this.spawn = spawn;
    }

    public Location getSpawn() {
        return spawn;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

}
