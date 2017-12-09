package byu.codemonkeys.persistance.sqlite;

import java.sql.SQLException;
import java.util.Map;

import byu.codemonkeys.persistance.IUserDAO;

public class SQLiteUserDAO extends SQLiteDAO implements IUserDAO {
    public SQLiteUserDAO() {
        super("users", "username");
    }

    @Override
    public void saveUserData(String username, String userJson) {
        try {
            insert(username, userJson);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUserData(String username) {
        return get(username);
    }

    @Override
    public Map<String, String> getAllUserData() {
        return all();
    }

    @Override
    public void clear() {
        super.clear();
    }
}
