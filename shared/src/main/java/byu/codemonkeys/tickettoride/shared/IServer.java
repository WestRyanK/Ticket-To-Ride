package byu.codemonkeys.tickettoride.shared;

import byu.codemonkeys.tickettoride.shared.model.Session;
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
     * @param session Unique token used to authenticate with the server.
     * @return Result indicating success or failure.
     */
    Result logout(Session session);

    PendingGameResult createGame(Session session, String gameName);
    PendingGameResult joinPendingGame(Session session, String gameID);
    PendingGamesResult leavePendingGame(Session session, String gameID);
    PendingGamesResult cancelGame(Session sesion, String gameID);
    PendingGamesResult getPendingGames(Session session);
    StartGameResult startGame(Session session, String gameID);
}
