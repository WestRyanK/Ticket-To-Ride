package byu.codemonkeys.tickettoride.server.model;

import java.util.HashMap;
import java.util.Map;

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

    public static RootModel getInstance() {
        return ourInstance;
    }

    private RootModel() {
        users = new HashMap<>();
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

    public User getUser(String username) {
        return users.get(username);
    }

    public String generateAuthToken() {
        return TokenGenerator.generateToken();
    }

    public void addSession(ServerSession session) {
        currentSessions.put(session.getAuthToken(), session);
    }
}
