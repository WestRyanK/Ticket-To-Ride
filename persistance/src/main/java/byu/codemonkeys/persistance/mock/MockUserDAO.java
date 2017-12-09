package byu.codemonkeys.persistance.mock;

import java.util.HashMap;
import java.util.Map;

import byu.codemonkeys.persistance.IUserDAO;


public class MockUserDAO implements IUserDAO {
    Map<String, String> users;

    public MockUserDAO() {
        users = new HashMap<>();
    }

    @Override
    public void saveUserData(String username, String userJson) {
        users.put(username, userJson);
    }

    @Override
    public String getUserData(String username) {
        return users.get(username);
    }

    @Override
    public Map<String, String> getAllUserData() {
        return users;
    }

    @Override
    public void clear() {
        users = new HashMap<>();
    }
}
