package byu.codemonkeys.persistance;


import java.util.Map;

import byu.codemonkeys.tickettoride.shared.model.UserBase;

public interface IUserDAO {
    void saveUser(String username, UserBase user);
    UserBase getUser(String username);
    Map<String, UserBase> getAllUsers();

    void clear();
}
