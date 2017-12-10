package byu.codemonkeys.tickettoride.server;


import org.junit.*;

import byu.codemonkeys.tickettoride.server.commands.DrawDeckTrainCardCommand;
import byu.codemonkeys.tickettoride.server.model.ActiveGame;
import byu.codemonkeys.tickettoride.server.model.PendingGame;
import byu.codemonkeys.tickettoride.server.model.RootModel;
import byu.codemonkeys.tickettoride.server.model.ServerSession;
import byu.codemonkeys.tickettoride.server.model.User;
import byu.codemonkeys.tickettoride.shared.commands.CommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.model.turns.Turn;

import static org.junit.Assert.assertEquals;


public class PersistenceProviderTest {
    private static int maxCommands = 5;

    @BeforeClass
    public static void init() {
        PersistenceFacade.initPersistanceFacade(maxCommands, "sqlite");
    }

    @Before
    public void setUp() {
        PersistenceFacade.getInstance().clear();
    }

    @Test
    public void testServerRestore() {
        PersistenceFacade provider = PersistenceFacade.getInstance();

        User user1 = new User("user1", "top secret");
        User user2 = new User("user2", "top secret");
        User user3 = new User("user3", "top secret");

        provider.saveUser(user1);
        provider.saveUser(user2);
        provider.saveUser(user3);

        ServerSession session1 = new ServerSession(user1, "user1_token");
        ServerSession session2 = new ServerSession(user2, "user2_token");
        ServerSession session3 = new ServerSession(user3, "user3_token");

        PendingGame pendingGame = new PendingGame("some_game_id", "some_game", user1);
        pendingGame.addUser(user2);
        pendingGame.addUser(user3);

        ActiveGame game = new ActiveGame(pendingGame);

        session1.setGameID(game.getID());
        session2.setGameID(game.getID());
        session3.setGameID(game.getID());

        provider.saveSession(session1);
        provider.saveSession(session2);
        provider.saveSession(session3);

        provider.trackGame(game);

        provider.restoreServer();

        RootModel rootModel = RootModel.getInstance();

        ActiveGame actualGame = rootModel.getActiveGame("some_game_id");

        User actualUser1 = rootModel.getUser("user1");
        User actualUser2 = rootModel.getUser("user2");
        User actualUser3 = rootModel.getUser("user3");

        ServerSession actualSession1 = rootModel.getSession("user1_token");
        ServerSession actualSession2 = rootModel.getSession("user2_token");
        ServerSession actualSession3 = rootModel.getSession("user3_token");

        /* Check Users were properly restored */
        assertEquals(user1.getUsername(), actualUser1.getUsername());
        assertEquals(user2.getUsername(), actualUser2.getUsername());
        assertEquals(user3.getUsername(), actualUser3.getUsername());

        assertEquals(user1.getPassword(), actualUser1.getPassword());
        assertEquals(user2.getPassword(), actualUser2.getPassword());
        assertEquals(user3.getPassword(), actualUser3.getPassword());

        /* Make sure turns are chained properly */
        Turn expectedTurn = game.getTurn();
        Turn actualTurn = actualGame.getTurn();
        assertEquals(expectedTurn.getPlayerIndex(), actualTurn.getPlayerIndex());

        expectedTurn = expectedTurn.getNextTurn();
        actualTurn = actualTurn.getNextTurn();
        assertEquals(expectedTurn.getPlayerIndex(), actualTurn.getPlayerIndex());

        expectedTurn = expectedTurn.getNextTurn();
        actualTurn = actualTurn.getNextTurn();
        assertEquals(expectedTurn.getPlayerIndex(), actualTurn.getPlayerIndex());

        expectedTurn = expectedTurn.getNextTurn();
        actualTurn = actualTurn.getNextTurn();
        assertEquals(expectedTurn.getPlayerIndex(), actualTurn.getPlayerIndex());

        /* Check Players are all in the game */
        assertEquals(game.getPlayers(), actualGame.getPlayers());

        /* Make sure decks are equal */
        assertEquals(game.getDeck(), actualGame.getDeck());

        /* Check that sessions are correct */
        assertEquals(session1, actualSession1);
        assertEquals(session2, actualSession2);
        assertEquals(session3, actualSession3);
    }

    @Ignore("This fails in the pipeline, but succeeds locally")
    @Test
    public void testServerRestore_WithCommands() {
        PersistenceFacade.initPersistanceFacade(maxCommands, "mock");

        PersistenceFacade provider = PersistenceFacade.getInstance();

        User user1 = new User("user1", "top secret");
        User user2 = new User("user2", "top secret");
        User user3 = new User("user3", "top secret");

        provider.saveUser(user1);
        provider.saveUser(user2);
        provider.saveUser(user3);

        ServerSession session1 = new ServerSession(user1, "user1_token");
        ServerSession session2 = new ServerSession(user2, "user2_token");
        ServerSession session3 = new ServerSession(user3, "user3_token");

        PendingGame pendingGame = new PendingGame("some_game_id", "some_game", user1);
        pendingGame.addUser(user2);
        pendingGame.addUser(user3);

        ActiveGame game = new ActiveGame(pendingGame);

        session1.setGameID(game.getID());
        session2.setGameID(game.getID());
        session3.setGameID(game.getID());

        provider.saveSession(session1);
        provider.saveSession(session2);
        provider.saveSession(session3);

        provider.trackGame(game);

        CommandData command = new DrawDeckTrainCardCommand();
        command.setAuthToken("user1_token");
        command.setGameID(game.getID());

        provider.saveCommand(game.getID(), (ICommand) command);

        provider.restoreServer();

        RootModel rootModel = RootModel.getInstance();

        ActiveGame actualGame = rootModel.getActiveGame("some_game_id");

        assertEquals(game.getDeck().getTrainCardsDeckCount() - 1, actualGame.getDeck().getTrainCardsDeckCount());
    }
}
