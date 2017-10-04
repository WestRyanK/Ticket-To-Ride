package byu.codemonkeys.tickettoride.models;

import java.util.ArrayList;

/**
 * Created by Megan on 10/3/2017.
 */

interface ModelFacade {
  // Retrieves the current user
  public User getUser();
  // Login a user, returns success status
  public boolean loginUser(String username, String password);
  // Register a user, returns success status
  public boolean registerUser(String username, String password);

  public ClientSession getSession();
  public void setSession(ClientSession session);

  public ArrayList<PendingGame> getPendingGames();
  public PendingGame createPendingGame(PendingGame game);

  public boolean joinPendingGame(PendingGame game);
  // No game parameter required since a user can only be in one game at a time
  public boolean leavePendingGame();
  public Game startPendingGame(PendingGame game);

}
