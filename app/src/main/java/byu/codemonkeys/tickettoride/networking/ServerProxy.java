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
    public Result login(String username, String password) {
        LoginCommandData data = new LoginCommandData(username, password);
        return communicator.send(Routes.LOGIN, data);
    }

    @Override
    public Result logout() {
        LogoutCommandData data = new LogoutCommandData();
        return communicator.send(Routes.LOGOUT, data);
    }

    @Override
    public Result register(String username, String password) {
        RegisterCommandData data = new RegisterCommandData(username, password);
        return communicator.send(Routes.REGISTER, data);
    }

    @Override
    public Result createGame(String gameName) {
        CreateGameCommandData data = new CreateGameCommandData(gameName, 2, 5);
        return communicator.send(Routes.CREATE_GAME, data);
    }

    @Override
    public Result joinPendingGame(String gameID) {
        JoinPendingGameCommandData data = new JoinPendingGameCommandData(gameID);
        return communicator.send(Routes.JOIN_PENDING_GAME, data);
    }

    @Override
    public Result leavePendingGame(String gameID) {
        LeavePendingGameCommandData data = new LeavePendingGameCommandData(gameID);
        return communicator.send(Routes.LEAVE_PENDING_GAME, data);
    }

    @Override
    public Result startGame(String gameID) {
        StartGameCommandData data = new StartGameCommandData(gameID);
        return communicator.send(Routes.START_GAME, data);
    }

    @Override
    public Result cancelGame(String gameID) {
        CancelGameCommandData data = new CancelGameCommandData(gameID);
        return communicator.send(Routes.CANCEL_GAME, data);
    }

    @Override
    public Result getPendingGames() {
        GetPendingGamesCommandData data = new GetPendingGamesCommandData();
        return communicator.send(Routes.GET_PENDING_GAMES, data);
    }
}
