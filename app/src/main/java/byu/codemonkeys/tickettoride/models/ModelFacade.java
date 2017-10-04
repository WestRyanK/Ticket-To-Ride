package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;

/**
 * Created by Megan on 10/3/2017.
 */

interface ModelFacade {
    // Retrieves the current user
    User getUser();
    // Login a user, returns success status
    boolean loginUser(String username, String password);
    // Register a user, returns success status
    boolean registerUser(String username, String password);
    boolean logoutUser();

    ClientSession getSession();
    void setSession(ClientSession session);

    ArrayList<PendingGame> getPendingGames();
    PendingGame createPendingGame(PendingGame game);

    boolean joinPendingGame(PendingGame game);
    // No game parameter required since a user can only be in one game at a time
    boolean leavePendingGame();
    Game startPendingGame(PendingGame game);

}
