package byu.codemonkeys.tickettoride.shared;


import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;

public interface IServer {
    LoginResult login(String username, String password);
    LoginResult register(String username, String password);
    Result logout(String authToken);
    PendingGamesResult createGame(String authToken, String gameName, Integer minPlayers, Integer maxPlayers);
    PendingGamesResult joinPendingGame(String authToken, String gameID);
    PendingGamesResult leavePendingGame(String authToken, String gameID);
    PendingGamesResult cancelGame(String authToken, String gameID);
    PendingGamesResult getPendingGames(String authToken);
    StartGameResult startGame(String authToken, String gameID);
}
