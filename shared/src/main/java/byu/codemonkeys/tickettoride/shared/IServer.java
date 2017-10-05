package byu.codemonkeys.tickettoride.shared;

import byu.codemonkeys.tickettoride.shared.results.*;

/**
 * Created by meganrich on 10/4/17.
 */

public interface IServer {
    Result login(String username, String password);
    Result logout();
    Result register(String username, String password);
    Result createGame (String gameName);
    Result joinPendingGame(String gameID);
    Result leavePendingGame(String gameID);
    Result startGame(String gameID);
    Result cancelGame(String gameID);
    Result getPendingGames();


}
