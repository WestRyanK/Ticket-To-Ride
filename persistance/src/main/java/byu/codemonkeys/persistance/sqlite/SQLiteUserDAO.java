package byu.codemonkeys.persistance.sqlite;

import java.sql.Connection;
import java.util.Map;

import byu.codemonkeys.persistance.IUserDAO;

public class SQLiteUserDAO extends SQLiteDAO implements IUserDAO {
    public SQLiteUserDAO() {
        super();
    }

    public SQLiteUserDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void saveUserData(String username, String userJson) {

    }

    @Override
    public String getUserData(String username) {
        return null;
    }

    @Override
    public Map<String, String> getAllUserData() {
        return null;
    }

    @Override
    public void clear() {
        super.clear("users");
    }
}
