package byu.codemonkeys.tickettoride.shared.model;


import java.util.List;

public class GameBase {
    protected String gameID, gameName;
    protected UserBase gameOwner;
    protected List<UserBase> gameUsers;
}
