package byu.codemonkeys.tickettoride.server;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.server.exceptions.AlreadyExistsException;
import byu.codemonkeys.tickettoride.server.exceptions.EmptyGameException;
import byu.codemonkeys.tickettoride.server.exceptions.FullGameException;
import byu.codemonkeys.tickettoride.server.model.ActiveGame;
import byu.codemonkeys.tickettoride.server.model.IRootModel;
import byu.codemonkeys.tickettoride.server.model.PendingGame;
import byu.codemonkeys.tickettoride.server.model.RootModel;
import byu.codemonkeys.tickettoride.server.model.ServerSession;
import byu.codemonkeys.tickettoride.server.model.User;
import byu.codemonkeys.tickettoride.shared.IServer;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.SendMessageCommandData;
import byu.codemonkeys.tickettoride.shared.commands.SetupGameCommandData;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.Message;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.results.DestinationCardResult;
import byu.codemonkeys.tickettoride.shared.results.HistoryResult;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;


public class ServerFacade implements IServer {
    private static ServerFacade ourInstance;
    private IRootModel rootModel;

    private ServerFacade() {
        this(RootModel.getInstance());
    }

    private ServerFacade(IRootModel model) {
        rootModel = model;
    }

    public static void initialize(IRootModel model) {
        ourInstance = new ServerFacade(model);
    }

    public static ServerFacade getInstance() {
        if (ourInstance == null) {
            ourInstance = new ServerFacade();
        }

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
        } catch (IllegalArgumentException e) {
            return new LoginResult("username may not be null");
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
    public PendingGameResult createGame(String authToken, String gameName) {
        ServerSession session = rootModel.getSession(authToken);

        if (session == null) {
            return new PendingGameResult("Authentication Error");
        }

        User user = session.getUser();

        PendingGame game = new PendingGame(rootModel.generateUniqueID(), gameName, user);

        rootModel.addPendingGame(game);
        
       session.setGameID(game.getID());

        return new PendingGameResult(game);
    }

    @Override
    public PendingGameResult joinPendingGame(String authToken, String gameID) {
        if (!rootModel.authorize(authToken)) {
            return new PendingGameResult("Authentication Error");
        }

        PendingGame game = rootModel.getPendingGame(gameID);

        if (game == null) {
            return new PendingGameResult("Invalid Game ID");
        }

        ServerSession session = rootModel.getSession(authToken);

        User user = session.getUser();

        if (game.hasUser(user)) {
            return new PendingGameResult("You already joined the game");
        }

        try {
            game.addUser(user);
            session.setGameID(gameID);
        } catch (FullGameException e) {
            return new PendingGameResult("The game is full");
        }

        return new PendingGameResult(game);
    }

    @Override
    public PendingGamesResult leavePendingGame(String authToken) {
        ServerSession session = rootModel.getSession(authToken);

        if (session == null) {
            return new PendingGamesResult("Authentication Error");
        }

        String gameID = session.getGameID();

        PendingGame game = rootModel.getPendingGame(gameID);

        if (game == null) {
            return new PendingGamesResult("Invalid Game ID");
        }

        User user = session.getUser();

        if (game.getOwner().equals(user)) {
            rootModel.removePendingGame(gameID);
        }

        try {
            game.removeUser(user);
        } catch (EmptyGameException e) {
            rootModel.removePendingGame(gameID);
        }

        return new PendingGamesResult(toGameBaseList(rootModel.getPendingGames()));
    }

    @Override
    public PendingGamesResult cancelGame(String authToken) {
        ServerSession session = rootModel.getSession(authToken);

        if (session == null) {
            return new PendingGamesResult("Authentication Error");
        }

        String gameID = session.getGameID();

        PendingGame game = rootModel.getPendingGame(gameID);

        if (game == null) {
            return new PendingGamesResult("Invalid Game ID");
        }

        User user = session.getUser();

        if (!game.getOwner().equals(user)) {
            return new PendingGamesResult("Only the game owner may cancel the game");
        }

        rootModel.removePendingGame(gameID);

        return new PendingGamesResult(toGameBaseList(rootModel.getPendingGames()));
    }

    @Override
    public PendingGamesResult getPendingGames(String authToken) {
        if (rootModel.authorize(authToken)) {
            return new PendingGamesResult(toGameBaseList(rootModel.getPendingGames()));
        }

        return new PendingGamesResult("Not Authorized");
    }

    /**
     * Converts a List<PendingGame> to a List<GameBase>.
     * @param pendingGames a List<PendingGame>.
     * @return the converted List.
     */
    private List<GameBase> toGameBaseList(List<PendingGame> pendingGames) {
        List<GameBase> games = new ArrayList<>();
        games.addAll(pendingGames);

        return games;
    }

    @Override
    public PendingGameResult getPendingGame(String authToken) {
        ServerSession session = rootModel.getSession(authToken);
        if (session == null) {
            return new PendingGameResult("invalid authentication");
        }
        PendingGame game = rootModel.getPendingGame(session.getGameID());
        if (game == null) {
            return new PendingGameResult("Invalid Game ID");
        }
        return new PendingGameResult(game);
    }

    @Override
    public StartGameResult startGame(String authToken) {
        ServerSession session = rootModel.getSession(authToken);

        if (session == null) {
            return new StartGameResult("Authentication Error");
        }

        String gameID = session.getGameID();

        PendingGame game = rootModel.getPendingGame(gameID);

        if (game == null) {
            return new StartGameResult("Invalid Game ID");
        }

        User user = session.getUser();

        if (!user.equals(game.getOwner())) {
            return new StartGameResult("Only the game owner may start the game");
        }

        if (!game.isStartable()) {
            return new StartGameResult("Too Few Players");
        }

        ActiveGame activeGame = rootModel.activateGame(gameID);

        for (Player player : activeGame.getPlayers()) {
            byu.codemonkeys.tickettoride.shared.model.ActiveGame preparedGame =
                    activeGame.prepareForClient(player);
            activeGame.sendCommand(new SetupGameCommandData(preparedGame), player.getUsername());
        }

        return new StartGameResult(activeGame);
    }

    @Override
    public HistoryResult updateHistory(String authToken, int lastSeenCommandIndex) {
        ServerSession session = rootModel.getSession(authToken);
        ActiveGame game = rootModel.getActiveGame(session.getGameID());
        List<CommandData> history = game.getGameHistory(session.getUser().getUsername(), lastSeenCommandIndex);
        return new HistoryResult(history);
    }

    @Override
    public DestinationCardResult drawDestinationCards(String authToken) {
        return null;
    }

    @Override
    public DestinationCardResult chooseDestinationCards(String authToken, int numSelected, List<DestinationCard> selected) {
        return null;
    }

    @Override
    public Result sendMessage(String authToken, Message message) {
        ServerSession session = rootModel.getSession(authToken);
        if (session == null) {
            return Result.failed("Authentication Error");
        }
        String gameID = session.getGameID();
        SendMessageCommandData messageCommand = new SendMessageCommandData(message);
        ActiveGame game = rootModel.getActiveGame(gameID);
        if (game == null) {
            return Result.failed("Player is not part of an active game");
        }
        game.broadcastCommand(messageCommand);
        return Result.success();
    }
}
