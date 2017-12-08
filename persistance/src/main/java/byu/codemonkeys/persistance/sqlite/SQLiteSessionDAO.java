package byu.codemonkeys.persistance.sqlite;

import java.sql.Connection;
import java.util.Map;

import byu.codemonkeys.persistance.ISessionDAO;

public class SQLiteSessionDAO extends SQLiteDAO implements ISessionDAO {
    public SQLiteSessionDAO() {
        super();
    }

    public SQLiteSessionDAO(Connection connection) {
        super(connection);
    }

    @Override
    public void saveSessionData(String authToken, String sessionJson) {

    }

    @Override
    public String getSessionData(String authToken) {
        return null;
    }

    @Override
    public Map<String, String> getAllSessionData() {
        return null;
    }

    @Override
    public void clear() {
        super.clear("sessions");
    }
}
