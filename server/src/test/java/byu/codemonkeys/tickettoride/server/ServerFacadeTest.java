package byu.codemonkeys.tickettoride.server;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import byu.codemonkeys.tickettoride.server.model.MockRootModel;
import byu.codemonkeys.tickettoride.server.model.RootModel;

import static org.junit.Assert.*;

public class ServerFacadeTest {
    @BeforeClass
    public static void setUp() {
        ServerFacade.initialize(new MockRootModel());
    }

//    @AfterClass
//    public static void tearDown() {
//        ServerFacade.initialize(RootModel.getInstance());
//    }

    @Test
    public void testGetInstance() {
        assertNotNull(ServerFacade.getInstance());
    }

    @Test
    public void testLogin() {
        assertFalse(ServerFacade.getInstance().login("user", "password").isSuccessful());
        assertFalse(ServerFacade.getInstance().login("username", "secret").isSuccessful());
        assertTrue(ServerFacade.getInstance().login("username", "password").isSuccessful());
    }

    @Test
    public void testRegister() {
        assertFalse(ServerFacade.getInstance().register("username", "secret").isSuccessful());
        assertTrue(ServerFacade.getInstance().register("user", "password").isSuccessful());
    }

    @Test
    public void testLogout() {
        assertTrue(ServerFacade.getInstance().logout("any-auth-token").isSuccessful());
    }

    @Test
    public void testCreateGame() {
        assertFalse(ServerFacade.getInstance().createGame("invalid-auth-token", "game-name").isSuccessful());
        assertTrue(ServerFacade.getInstance().createGame("auth-token", "game-name").isSuccessful());
    }

    @Test
    public void joinPendingGame() {
        assertFalse(ServerFacade.getInstance().joinPendingGame("invalid-auth-token", "game-1-id").isSuccessful());
        assertFalse(ServerFacade.getInstance().joinPendingGame("auth-token", "invalid-game-id").isSuccessful());
        assertFalse(ServerFacade.getInstance().joinPendingGame("auth-token", "game-1-id").isSuccessful());
        assertFalse(ServerFacade.getInstance().joinPendingGame("auth-token", "full-game-id").isSuccessful());
        assertTrue(ServerFacade.getInstance().joinPendingGame("auth-token", "game-2-id").isSuccessful());
    }

    @Test
    public void testLeavePendingGame() {
        assertFalse(ServerFacade.getInstance().leavePendingGame("invalid-auth-token", "game-1-id").isSuccessful());
        assertFalse(ServerFacade.getInstance().leavePendingGame("auth-token","invalid-game-id").isSuccessful());
        assertTrue(ServerFacade.getInstance().leavePendingGame("auth-token", "game-1-id").isSuccessful());
    }

    @Test
    public void testCancelGame() {
        assertFalse(ServerFacade.getInstance().cancelGame("invalid-auth-token", "game-1-id").isSuccessful());
        assertFalse(ServerFacade.getInstance().cancelGame("auth-token", "invalid-game-id").isSuccessful());
        assertFalse(ServerFacade.getInstance().cancelGame("auth-token", "game-2-id").isSuccessful());
        assertTrue(ServerFacade.getInstance().cancelGame("auth-token", "game-1-id").isSuccessful());
    }

    @Test
    public void testGetPendingGames() {
        assertFalse(ServerFacade.getInstance().getPendingGames("invalid-auth-token").isSuccessful());
        assertTrue(ServerFacade.getInstance().getPendingGames("auth-token").isSuccessful());
    }

    @Test
    public void testGetPendingGame() {
        assertTrue(ServerFacade.getInstance().getPendingGame("auth-token").isSuccessful());
        assertFalse(ServerFacade.getInstance().getPendingGame("invalid-auth-token").isSuccessful());
    }

    @Test
    public void testStartGame() {
        assertFalse(ServerFacade.getInstance().startGame("invalid-auth-token", "game-1-id").isSuccessful());
        assertFalse(ServerFacade.getInstance().startGame("auth-token", "invalid-game-id").isSuccessful());
        assertFalse(ServerFacade.getInstance().startGame("auth-token", "full-game-id").isSuccessful());
        assertFalse(ServerFacade.getInstance().startGame("auth-token", "game-1-id").isSuccessful());
        assertTrue(ServerFacade.getInstance().startGame("auth-token-0", "full-game-id").isSuccessful());
    }
}