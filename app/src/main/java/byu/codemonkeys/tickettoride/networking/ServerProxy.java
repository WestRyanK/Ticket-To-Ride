package byu.codemonkeys.tickettoride.networking;

import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.results.*;
import byu.codemonkeys.tickettoride.shared.commands.*;



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
    public Result logout(String authToken) {
        LogoutCommandData data = new LogoutCommandData();
        data.setAuthToken(authToken);
        return communicator.sendLogout(data);
    }

    @Override
    public LoginResult register(String username, String password) {
        RegisterCommandData data = new RegisterCommandData(username, password);
        return communicator.sendRegister(data);
    }

    @Override
    public PendingGameResult createGame(String authToken, String gameName) {
        CreateGameCommandData data = new CreateGameCommandData(gameName);
        data.setAuthToken(authToken);
        return communicator.sendCreateGame(data);
    }

    @Override
    public PendingGameResult joinPendingGame(String authToken, String gameID) {
        JoinPendingGameCommandData data = new JoinPendingGameCommandData(gameID);
        data.setAuthToken(authToken);
        return communicator.sendJoinPendingGame(data);
    }

    //TODO(compy-386): fix this to gameID everywhere else
    @Override
    public PendingGamesResult leavePendingGame(String authToken, String gameID) {
        LeavePendingGameCommandData data = new LeavePendingGameCommandData(gameID);
        data.setAuthToken(authToken);
        return communicator.sendLeavePendingGame(data);
    }

    @Override
    public StartGameResult startGame(String authToken, String gameID) {
        StartGameCommandData data = new StartGameCommandData(gameID);
        data.setAuthToken(authToken);
        return communicator.sendStartGame(data);
    }

    @Override
    public PendingGamesResult cancelGame(String authToken, String gameID) {
        CancelGameCommandData data = new CancelGameCommandData(gameID);
        data.setAuthToken(authToken);
        return communicator.sendCancelGame(data);
    }

    @Override
    public PendingGamesResult getPendingGames(String authToken) {
        GetPendingGamesCommandData data = new GetPendingGamesCommandData();
        data.setAuthToken(authToken);
        return communicator.sendGetPendingGames(data);
    }
}
