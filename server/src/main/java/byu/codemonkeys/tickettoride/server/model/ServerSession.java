package byu.codemonkeys.tickettoride.server.model;

import byu.codemonkeys.tickettoride.shared.model.Session;


public class ServerSession extends Session {
    private User user;
    private String gameID;

    public ServerSession(User user, String authToken) {
        this.user = user;
        this.authToken = authToken;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o instanceof ServerSession) {
            ServerSession that = (ServerSession) o;

            if (this.authToken.equals(that.authToken) &&
                    this.user.equals(that.user) &&
                    this.gameID.equals(that.gameID)) {
                return true;
            }
        }
        return false;
    }
}
