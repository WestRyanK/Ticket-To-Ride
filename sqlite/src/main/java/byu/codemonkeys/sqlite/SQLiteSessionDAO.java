package byu.codemonkeys.sqlite;

import java.sql.SQLException;
import java.util.Map;

import byu.codemonkeys.persistance.ISessionDAO;

public class SQLiteSessionDAO extends SQLiteDAO implements ISessionDAO {
    public SQLiteSessionDAO() {
        super("sessions", "token");
    }

    @Override
    public void saveSessionData(String authToken, String sessionJson) {
        try {
            insert(authToken, sessionJson);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getSessionData(String authToken) {
        return get(authToken);
    }

    @Override
    public Map<String, String> getAllSessionData() {
        return all();
    }

    @Override
    public void deleteSessionData(String authToken) {
        try {
            delete(authToken);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void clear() {
        super.clear();
    }
}
