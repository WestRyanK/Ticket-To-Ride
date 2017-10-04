package byu.codemonkeys.tickettoride.server.model;

import java.sql.Timestamp;

import byu.codemonkeys.tickettoride.shared.model.Session;


public class ServerSession extends Session {
    private transient User user;
    private transient String gameID;
    private transient Timestamp lastActivity;

    public ServerSession(User user, String authToken) {
        this.user = user;
        this.authToken = authToken;
        lastActivity = new Timestamp(System.currentTimeMillis());
    }

    public User getUser() {
        return user;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getGameID() {
        return gameID;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }
}
