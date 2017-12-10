package byu.codemonkeys.tickettoride.server;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import byu.codemonkeys.persistance.IActiveGameDAO;
import byu.codemonkeys.persistance.IPersistanceProvider;
import byu.codemonkeys.persistance.ISessionDAO;
import byu.codemonkeys.persistance.IUserDAO;
import byu.codemonkeys.persistance.mock.MockProvider;
import byu.codemonkeys.persistance.sqlite.SQLiteProvider;
import byu.codemonkeys.tickettoride.server.model.ActiveGame;
import byu.codemonkeys.tickettoride.server.model.RootModel;
import byu.codemonkeys.tickettoride.server.model.ServerSession;
import byu.codemonkeys.tickettoride.server.model.TrackedGame;
import byu.codemonkeys.tickettoride.server.model.User;
import byu.codemonkeys.tickettoride.server.util.CommandUtils;
import byu.codemonkeys.tickettoride.server.util.GameDeserializer;
import byu.codemonkeys.tickettoride.shared.Serializer;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class PersistenceFacade {
    private int maxCommands;
    private IPersistanceProvider dbPlugin;
    private Serializer serializer;

    private IActiveGameDAO gameDAO;
    private IUserDAO userDAO;
    private ISessionDAO sessionDAO;

    private Map<String, TrackedGame> trackedGames;

    private static PersistenceFacade instance = null;

    public static void initPersistanceFacade(int maxCommands, String pluginPath) {
        System.out.println("--Loading DataBase Plugin");
        if (instance == null) {
            // TODO: Load provider dynamically from pluginPath
			File loc = new File(pluginPath);
	
			File[] flist = loc.listFiles(new FileFilter() {
				public boolean accept(File file) {return file.getPath().toLowerCase().endsWith(".jar");}
			});
			System.out.print("File count!" + String.valueOf(flist.length));
			URL[] urls = new URL[flist.length];
			URLClassLoader ucl = new URLClassLoader(urls);
			ServiceLoader<IPersistanceProvider> loader = ServiceLoader.load(IPersistanceProvider.class, ucl);
	
			IPersistanceProvider provider;
            switch (pluginPath) {
                case "sqlite":
                    provider = new SQLiteProvider();
                    break;
                default:
                    provider = new MockProvider();
            }

            instance = new PersistenceFacade(maxCommands, provider);
            System.out.println("--No database plugin found, server state will not be saved");
        }
    }

    public static PersistenceFacade getInstance() {
        if (instance == null) {
            initPersistanceFacade(5, "mock");
            //throw new RuntimeException("PersistenceFacade is uninitialized");
        }
        return instance;
    }

    private PersistenceFacade(int maxCommands, IPersistanceProvider dbPlugin) {
        this.maxCommands = maxCommands;
        this.dbPlugin = dbPlugin;
        serializer = new Serializer();

        trackedGames = new HashMap<>();

        dbPlugin.init();

        gameDAO = dbPlugin.newActiveGameDAO();
        userDAO = dbPlugin.newUserDAO();
        sessionDAO = dbPlugin.newSessionDAO();
    }

    public void trackGame(ActiveGame game) {
        if (game != null) {
            trackedGames.put(game.getID(), new TrackedGame(game));

            //Record initial game snapshot
            String json = serializer.serialize(trackedGames.get(game.getID()));
            gameDAO.saveGameData(game.getID(), json);
        }
    }

    public void removeGame(String gameID) {
        trackedGames.remove(gameID);
        gameDAO.deleteGameData(gameID);
    }

    public void saveSession(ServerSession session) {
        if (session != null) {
            String json = serializer.serialize(session);
            sessionDAO.saveSessionData(session.getAuthToken(), json);
        }
    }

    public void removeSession(String authToken) {
        sessionDAO.deleteSessionData(authToken);
    }

    public void saveUser(User user) {
        if (user != null) {
            String json = serializer.serialize(user);
            userDAO.saveUserData(user.getUsername(), json);
        }
    }

    public void saveCommand(String gameID, ICommand command) {
        if (trackedGames.containsKey(gameID) && command != null) {
            String json = serializer.serialize(command);
            gameDAO.saveCommandData(gameID, json);

            TrackedGame game = trackedGames.get(gameID);
            game.incrementCommands();
            if (game.getNumStoredCommands() >= maxCommands) {
                json = serializer.serialize(game.prepareToBeStored());
                gameDAO.saveGameData(gameID, json);
                game.clearCommands();
            }
        }
    }

    public void restoreServer() {
        System.out.println("--Restoring Server State");

        Map<String, String> gameData = gameDAO.getAllGameData();
        Map<String, String> userData = userDAO.getAllUserData();
        Map<String, String> sessionData = sessionDAO.getAllSessionData();

        Map<String, ActiveGame> games = new HashMap<>();
        Map<String, User> users = new HashMap<>();
        Map<String, ServerSession> sessions = new HashMap<>();

        for (String json : gameData.values()) {
            TrackedGame game = GameDeserializer.deserialize(json);
            game.getGame().setUpTurns();
            game.getGame().setTurn(game.getCurrentPlayersTurn());
            games.put(game.getGame().getID(), game.getGame());
        }

        for (String json : userData.values()) {
            User user = serializer.deserialize(json, User.class);
            users.put(user.getUsername(), user);
        }

        for (String json : sessionData.values()) {
            ServerSession session = serializer.deserialize(json, ServerSession.class);
            sessions.put(session.getAuthToken(), session);
        }

        RootModel.restoreModel(users, sessions, games);

        for (ActiveGame game : games.values()) {
            List<String> commands = gameDAO.getAllCommandData(game.getID());
            if (commands != null) {
                for (String json : commands) {
                    ICommand command = CommandUtils.buildCommand(json);
                    Result result = command.execute();
                    if (!result.isSuccessful()) {
                        throw new RuntimeException("Error Restoring Server, command could not be applied: " + json);
                    }
                }
            }
        }

        System.out.println("--Server Restore Complete");
    }

    public void clear() {
        gameDAO.clear();
        userDAO.clear();
        sessionDAO.clear();

        trackedGames = new HashMap<>();
    }


}
