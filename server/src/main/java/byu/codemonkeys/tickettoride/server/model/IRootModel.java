package byu.codemonkeys.tickettoride.server.model;

import java.util.List;
import java.util.NoSuchElementException;

public interface IRootModel {
    public boolean verifyLogin(String username, String password);

    public boolean authorize(String authToken);

    public void removeSession(String authToken);

    public User getUser(String username);

    public void registerNewUser(String username, String password);

    public String generateUniqueID();

    public void addSession(ServerSession session);

    public ServerSession getSession(String authToken);

    public List<PendingGame> getPendingGames();

    public PendingGame getPendingGame(String gameID);

    public void addPendingGame(PendingGame game);

    public void removePendingGame(String gameID);

    public ActiveGame activateGame(String gameID) throws NoSuchElementException;

    public ActiveGame getActiveGame(String gameID);
}
