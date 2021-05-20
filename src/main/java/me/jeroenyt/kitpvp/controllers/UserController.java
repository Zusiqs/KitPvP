package me.jeroenyt.kitpvp.controllers;

import me.jeroenyt.kitpvp.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserController {
    private final List<User> users;

    public UserController() {
        users = new ArrayList<>();
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(UUID uuid) {
        users.removeIf(player -> player.getUuid().equals(uuid));
    }

    public User getUser(UUID uuid) {
        return users.stream().filter(player -> player.getUuid().equals(uuid)).findAny().get();
    }

    public List<User> getUsers() {
        return users;
    }

}
