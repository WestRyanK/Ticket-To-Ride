package byu.codemonkeys.tickettoride.shared;


import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;

public interface IServer {
    LoginResult login(String username, String password);
    LoginResult register(String username, String password);
    Result logout();
    PendingGamesResult createGame(String gameName, Integer minPlayers, Integer maxPlayers);
    PendingGamesResult joinPendingGame(String gameID);
    PendingGamesResult leavePendingGame(String gameID);
    PendingGamesResult cancelGame(String gameID);
    PendingGamesResult getPendingGames();
    StartGameResult startGame(String gameID);
}
