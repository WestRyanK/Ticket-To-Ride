package byu.codemonkeys.tickettoride.server;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.UUID;

import byu.codemonkeys.tickettoride.shared.Serializer;
import byu.codemonkeys.tickettoride.shared.results.PendingGameResult;

import static org.junit.Assert.*;

public class ServerFacadeTest {
    private static String exampleGameID;

    private ServerFacade serverFacade;

    @BeforeClass
    public static void setUpClass() {
        String token = ServerFacade.getInstance().register("username", "password").getUserSession().getAuthToken();
        exampleGameID = ServerFacade.getInstance().createGame(token, "A Game").getGame().getID();
    }

    @Before
    public void setUp() {
        serverFacade = ServerFacade.getInstance();
    }

    @Test
    public void testLogin() {
        assertTrue(serverFacade.login("username", "password").isSuccessful());
        assertFalse(serverFacade.login("username", "secret").isSuccessful());
    }

    @Test
    public void testCreateGame() {
        assertFalse(serverFacade.createGame(UUID.randomUUID().toString(), "My First Game").isSuccessful());

        String token = serverFacade.login("username", "password").getUserSession().getAuthToken();

        PendingGameResult result = serverFacade.createGame(token, "My First Game");

        assertTrue(result.isSuccessful());

        serverFacade.leavePendingGame(token, result.getGame().getID());
    }

    @Test
    public void testJoinPendingGame() {
        assertFalse(serverFacade.joinPendingGame(UUID.randomUUID().toString(), exampleGameID).isSuccessful());

        String usernameToken = serverFacade.login("username", "password").getUserSession().getAuthToken();

        assertFalse(serverFacade.joinPendingGame(usernameToken, exampleGameID).isSuccessful());

        String userToken = serverFacade.register("user", "secret").getUserSession().getAuthToken();

        assertTrue(serverFacade.joinPendingGame(userToken, exampleGameID).isSuccessful());
    }

    @Test
    public void testLeavePendingGame() {
        assertFalse(serverFacade.leavePendingGame(UUID.randomUUID().toString(), exampleGameID).isSuccessful());

        String usernameToken = serverFacade.login("username", "password").getUserSession().getAuthToken();

        serverFacade.joinPendingGame(usernameToken, exampleGameID);

        assertTrue(serverFacade.leavePendingGame(usernameToken, exampleGameID).isSuccessful());
    }

    @Test
    public void testCancelGame() {
        assertFalse(serverFacade.cancelGame(UUID.randomUUID().toString(), exampleGameID).isSuccessful());
    }
}