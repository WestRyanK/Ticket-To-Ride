package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import byu.codemonkeys.tickettoride.server.exceptions.AlreadyExistsException;

public class RootModel {
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

    public static RootModel getInstance() {
        return ourInstance;
    }

    private RootModel() {
        users = new HashMap<>();
        currentSessions = new HashMap<>();
        pendingGames = new HashMap<>();
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
     * Retrieves the List of currently pending games.
     * @return List of PendingGame objects
     */
    public List<PendingGame> getPendingGames() {
        return new ArrayList<>(pendingGames.values());
    }
}
