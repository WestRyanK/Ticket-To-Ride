package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import byu.codemonkeys.tickettoride.server.exceptions.AlreadyExistsException;
import byu.codemonkeys.tickettoride.shared.model.GameBase;

public class RootModel implements IRootModel {
    private static final RootModel ourInstance = new RootModel();

    /**
     * users maps a username to User objects that represent registered users
     */
    private Map<String, User> users;

    /**
     * currentSessions maps authtokens to a users session data
     */
    private Map<String, ServerSession> currentSessions;

    /**
     * pendingGames maps gameIDs to pendingGame objects.
     */
    private Map<String, PendingGame> pendingGames;

    /**
     * activeGames maps gameIDs to ActiveGame objects.
     */
    private Map<String, ActiveGame> activeGames;

    public static RootModel getInstance() {
        return ourInstance;
    }

    private RootModel() {
        users = new HashMap<>();
        currentSessions = new HashMap<>();
        pendingGames = new HashMap<>();
        activeGames = new HashMap<>();
    }

    /**
     * Checks a username and password against registered users
     * and indicate if the credentials are correct.
     * @param username a users identifier
     * @param password secret used to verify correct credentials
     * @return a boolean indicating if the users login credentials were correct
     */
    public boolean verifyLogin(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    /**
     * Validates that an authToken belongs to a currently logged in user
     * @param authToken Unique ID used to authenticate with the server
     * @return boolean indicating if the authToken is an authorized token
     */
    public boolean authorize(String authToken) {
        if (currentSessions.containsKey(authToken)) {
            return true;
        }
        return false;
    }

    /**
     * Removes a Session from the current sessions, effectively logging a user out.
     * Does not check if the session actually exists.
     * @param authToken Unique ID that indicates which session to delete
     */
    public void removeSession(String authToken) {
        currentSessions.remove(authToken);
    }

    /**
     * Retrieves the user associated with the given username.
     * @param username unique username to retrieve the User object
     * @return The User Object if it exists
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * Registers a new user with the server. If one already exists an AlreadyExists exception is thrown.
     * @param username unique name to identify the user.
     * @param password secret used to authenticate the user upon login.
     */
    public void registerNewUser(String username, String password) {
        if (users.containsKey(username)) {
            throw new AlreadyExistsException();
        }
        User user = new User(username, password);
        users.put(username, user);
    }

    /**
     * Generates a UUID
     * @return a UUID
     */
    public String generateUniqueID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Adds a session to the servers currentSessions, effectively logging a user in.
     * @param session User session to be added, must be constructed before hand.
     */
    public void addSession(ServerSession session) {
        currentSessions.put(session.getAuthToken(), session);
    }

    /**
     * Retrieves a user session if one exists
     * @param authToken Unique token to indicate which session to retrieve
     * @return A ServerSession or null if none is found.
     */
    public ServerSession getSession(String authToken) {
        return currentSessions.get(authToken);
    }

    /**
     * Retrieves the List of currently pending games.
     * @return List of PendingGame objects
     */
    public List<PendingGame> getPendingGames() {
        List<PendingGame> games = new ArrayList<>();

        for (Map.Entry<String, PendingGame> entry : pendingGames.entrySet()) {
            PendingGame game = entry.getValue();

            if (!game.isStarted()) {
                games.add(game);
            }
        }

        return games;
    }

    /**
     * Retrieves the game with the specified ID.
     * @param gameID the ID of the game to retrieve.
     * @return the found PendingGame or null if none was found.
     */
    public PendingGame getPendingGame(String gameID) {
        return pendingGames.get(gameID);
    }

    /**
     * Add the specified PendingGame to the Model.
     * @param game a PendingGame.
     */
    public void addPendingGame(PendingGame game) {
        pendingGames.put(game.getID(), game);
    }

    /**
     * Deletes the pending game with the specified ID.
     * @param gameID a game ID.
     */
    public void removePendingGame(String gameID) {
        pendingGames.remove(gameID);
    }

    /**
     * Changes the pending game with the specified ID to an active game.
     * @param gameID a game ID.
     */
    public ActiveGame activateGame(String gameID) throws NoSuchElementException {
        PendingGame pendingGame = getPendingGame(gameID);

        if (pendingGame == null) {
            throw new NoSuchElementException();
        }

        ActiveGame activeGame = new ActiveGame(pendingGame);

        activeGames.put(activeGame.getID(), activeGame);

        pendingGame.setStarted(true);

        return activeGame;
    }
}
