package byu.codemonkeys.tickettoride.shared;


import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;

public interface IServer {
    /**
     * Authenticates a user using the provided username and password
     * @param username Unique name identifying the user
     * @param password Secret to validate the users credentials
     * @return A LoginResult that contains a user session with an AuthToken if successful, otherwise returns an error message.
     */
    LoginResult login(String username, String password);

    /**
     * Registers a new user
     * @param username Unique name to identify the user
     * @param password Secret to validate the users credentials
     * @return A LoginResult that contains a user session with an AuthToken if successful, otherwise returns an error message.
     */
    LoginResult register(String username, String password);

    /**
     * Logs a user out, clearing their session data.
     * @param authToken Unique token used to authenticate with the server.
     * @return Result indicating success or failure.
     */
    Result logout(String authToken);

    PendingGameResult createGame(String authToken, String gameName);
    PendingGameResult joinPendingGame(String authToken, String gameID);
    PendingGamesResult leavePendingGame(String authToken, String gameID);
    PendingGamesResult cancelGame(String authToken, String gameID);
    PendingGamesResult getPendingGames(String authToken);
    PendingGameResult getPendingGame(String authToken);
    StartGameResult startGame(String authToken, String gameID);
}
