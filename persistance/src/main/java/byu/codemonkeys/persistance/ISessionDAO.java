package byu.codemonkeys.persistance;


import java.util.Map;

import byu.codemonkeys.tickettoride.shared.model.Session;

public interface ISessionDAO {
    void saveSession(String authToken, Session session);
    Session getSession(String authToken);
    Map<String, Session> getAllSessions();

    void clear();
}
