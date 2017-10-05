package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;

/**
 * Created by Megan on 10/3/2017.
 */

interface ModelFacade {
    // Retrieves the current user
    User getUser();
    // Login a user, will throw error in the case of failure
    void loginUser(String username, String password);
    // Register a user, will throw error in the case of failure
    void registerUser(String username, String password);
    void logoutUser();

    ClientSession getSession();
    void setSession(ClientSession session);

    ArrayList<PendingGame> getPendingGames();
    PendingGame createPendingGame(PendingGame game);

    // will throw error in the case of failure
    PendingGame joinPendingGame(PendingGame game);
    // gets pending game that the logged in user is apart of
    PendingGame getPendingGame();
    /*
    will throw error in the case of failure
    No game parameter required since a user can only be in one game at a time
    */
    void leavePendingGame();
    Game startPendingGame(PendingGame game);

}
