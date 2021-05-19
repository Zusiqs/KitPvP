package me.jeroenyt.kitpvp.controllers;

import me.jeroenyt.kitpvp.models.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserController {
    private final List<UserModel> users;

    public UserController(){
        users = new ArrayList<>();
    }

    public void addUser(UserModel user){
        users.add(user);
    }

    public void removeUser(UUID uuid){
        users.removeIf(player -> player.getUuid().equals(uuid));
    }

    public UserModel getUser(UUID uuid){
        return users.stream().filter(player -> player.getUuid().equals(uuid)).findAny().get();
    }

    public List<UserModel> getUsers(){
        return users;
    }

}
