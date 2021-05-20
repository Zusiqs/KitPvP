package me.jeroenyt.kitpvp.models;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private int kills;
    private int deaths;

    public User(UUID uuid, int kills, int deaths) {
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void increaseDeaths() {
        this.deaths += 1;
    }

    public void increaseKills() {
        this.kills += 1;
    }

}
