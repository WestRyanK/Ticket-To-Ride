package byu.codemonkeys.persistance.mock;

import java.util.HashMap;
import java.util.Map;

import byu.codemonkeys.persistance.ISessionDAO;


public class MockSessionDAO implements ISessionDAO {
    Map<String, String> sessions;

    public MockSessionDAO() {
        sessions = new HashMap<>();
    }

    @Override
    public void saveSessionData(String authToken, String sessionJson) {
        sessions.put(authToken, sessionJson);
    }

    @Override
    public String getSessionData(String authToken) {
        return sessions.get(authToken);
    }

    @Override
    public Map<String, String> getAllSessionData() {
        return sessions;
    }

    @Override
    public void deleteSessionData(String authToken) {
        sessions.remove(authToken);
    }

    @Override
    public void clear() {
        sessions = new HashMap<>();
    }
}
