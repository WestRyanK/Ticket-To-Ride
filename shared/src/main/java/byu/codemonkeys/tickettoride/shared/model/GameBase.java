package byu.codemonkeys.tickettoride.shared.model;


import java.util.List;

public class GameBase {
    protected String gameID, gameName;
    protected UserBase gameOwner;
    protected List<UserBase> gameUsers;

    public String getID() {
        return gameID;
    }

    public String getName() {
        return gameName;
    }

    public UserBase getOwner() {
        return gameOwner;
    }

    public List<UserBase> getUsers() {
        return gameUsers;
    }

    public boolean hasUser(UserBase user) {
        return gameUsers.contains(user);
    }
}
