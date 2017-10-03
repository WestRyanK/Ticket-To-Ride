package byu.codemonkeys.tickettoride.server;

import byu.codemonkeys.tickettoride.server.exceptions.AlreadyExistsException;
import byu.codemonkeys.tickettoride.server.model.RootModel;
import byu.codemonkeys.tickettoride.server.model.ServerSession;
import byu.codemonkeys.tickettoride.server.model.User;
import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;


public class ServerFacade implements IServer {
    private static final ServerFacade ourInstance = new ServerFacade();
    private RootModel rootModel;

    private ServerFacade() {
        rootModel = RootModel.getInstance();
    }

    public static ServerFacade getInstance() {
        return ourInstance;
    }

    @Override
    public LoginResult login(String username, String password) {
        boolean valid = rootModel.verifyLogin(username, password);
        if (valid) {
            return executeLogin(username);
        }
        return new LoginResult("invalid login credentials");
    }

    @Override
    public LoginResult register(String username, String password) {
        try {
            rootModel.registerNewUser(username, password);
        } catch (AlreadyExistsException e) {
            return new LoginResult("username already exists");
        }
        return executeLogin(username);
    }

    private LoginResult executeLogin(String username) {
        User user = rootModel.getUser(username);
        String authToken = rootModel.generateUniqueID();
        ServerSession session = new ServerSession(user, authToken);
        rootModel.addSession(session);
        return new LoginResult(session);
    }

    @Override
    public Result logout(String authToken) {
        rootModel.removeSession(authToken);
        return new Result();
    }

    @Override
    public PendingGamesResult createGame(String authToken, String gameName, Integer minPlayers, Integer maxPlayers) {
        return null;
    }

    @Override
    public PendingGamesResult joinPendingGame(String authToken, String gameID) {
        return null;
    }

    @Override
    public PendingGamesResult leavePendingGame(String authToken, String gameID) {
        return null;
    }

    @Override
    public PendingGamesResult cancelGame(String authToken, String gameID) {
        return null;
    }

    @Override
    public PendingGamesResult getPendingGames(String authToken) {
        if (rootModel.authorize(authToken)) {
            return new PendingGamesResult();
        }
        return null;
    }

    @Override
    public StartGameResult startGame(String authToken, String gameID) {
        return null;
    }
}
