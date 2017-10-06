package byu.codemonkeys.tickettoride.networking;

import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.results.*;
import byu.codemonkeys.tickettoride.shared.commands.*;
import byu.codemonkeys.tickettoride.shared.model.Session;



public class ServerProxy implements IServer {
    private static ServerProxy instance;

    private ClientCommunicator communicator;

    private ServerProxy() {
        communicator = ClientCommunicator.getInstance();
    }

    public static ServerProxy getInstance() {
        if (instance == null) {
            instance = new ServerProxy();
        }
        return instance;
    }

    @Override
    public LoginResult login(String username, String password) {
        LoginCommandData data = new LoginCommandData(username, password);
        return communicator.sendLogin(data);
    }

    @Override
    public Result logout(Session session) {
        LogoutCommandData data = new LogoutCommandData();
        data.setUserSession(session);
        return communicator.sendLogout(data);
    }

    @Override
    public LoginResult register(String username, String password) {
        RegisterCommandData data = new RegisterCommandData(username, password);
        return communicator.sendRegister(data);
    }

    @Override
    public PendingGameResult createGame(Session session, String gameName) {
        CreateGameCommandData data = new CreateGameCommandData(gameName);
        data.setUserSession(session);
        return communicator.sendCreateGame(data);
    }

    @Override
    public PendingGameResult joinPendingGame(Session session, String gameID) {
        JoinPendingGameCommandData data = new JoinPendingGameCommandData(gameID);
        data.setUserSession(session);
        return communicator.sendJoinPendingGame(data);
    }

    @Override
    public PendingGamesResult leavePendingGame(Session session, String gameID) {
        LeavePendingGameCommandData data = new LeavePendingGameCommandData(gameID);
        data.setUserSession(session);
        return communicator.sendLeavePendingGame(data);
    }

    @Override
    public StartGameResult startGame(Session session, String gameID) {
        StartGameCommandData data = new StartGameCommandData(gameID);
        data.setUserSession(session);
        return communicator.sendStartGame(data);
    }

    @Override
    public PendingGamesResult cancelGame(Session session, String gameID) {
        CancelGameCommandData data = new CancelGameCommandData(gameID);
        data.setUserSession(session);
        return communicator.sendCancelGame(data);
    }

    @Override
    public PendingGamesResult getPendingGames(Session session) {
        GetPendingGamesCommandData data = new GetPendingGamesCommandData();
        data.setUserSession(session);
        return communicator.sendGetPendingGames(data);
    }
}
