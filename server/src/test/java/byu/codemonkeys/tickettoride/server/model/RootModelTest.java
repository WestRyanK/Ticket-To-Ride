package byu.codemonkeys.tickettoride.server.model;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import byu.codemonkeys.tickettoride.server.exceptions.AlreadyExistsException;

import static org.junit.Assert.*;

public class RootModelTest {
    private RootModel rootModel;

    @Before
    public void setUp() {
        rootModel = RootModel.getInstance();
    }

    @Test
    public void testVerifyLogin() {
        try {
            rootModel.registerNewUser("username", "password");
        } catch (Exception e) {}

        assertTrue(rootModel.verifyLogin("username", "password"));
        assertFalse(rootModel.verifyLogin("username", "secret"));
        assertFalse(rootModel.verifyLogin("user", "password"));
    }

    @Test
    public void testAuthorize() {
        User user = new User("username", "password");
        String token = UUID.randomUUID().toString();
        ServerSession session  = new ServerSession(user, token);

        rootModel.addSession(session);

        assertTrue(rootModel.authorize(token));
        assertFalse(rootModel.authorize(UUID.randomUUID().toString()));
        assertFalse(rootModel.authorize(null));
    }

    @Test
    public void testRemoveSession() {
        User user = new User("username", "password");
        String token = UUID.randomUUID().toString();
        ServerSession session = new ServerSession(user, token);

        rootModel.addSession(session);

        assertNotNull(rootModel.getSession(token));

        rootModel.removeSession(token);

        assertNull(rootModel.getSession(token));
    }

    @Test
    public void testGetUser() {
        assertNull(rootModel.getUser("username"));

        rootModel.registerNewUser("username", "password");

        assertNotNull(rootModel.getUser("username"));
        assertNull(rootModel.getUser("user"));
    }

    @Test
    public void testRegisterNewUser() {
        try {
            rootModel.registerNewUser("username", "password");
            rootModel.registerNewUser("username", "secret");
            assertTrue(false);
        } catch (AlreadyExistsException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGenerateUniqueID() {
        assertNotNull(rootModel.generateUniqueID());
    }

    @Test
    public void testAddSession() {
        User user = new User("username", "password");
        String token = UUID.randomUUID().toString();
        ServerSession session = new ServerSession(user, token);

        assertNull(rootModel.getSession(token));

        rootModel.addSession(session);

        assertNotNull(rootModel.getSession(token));
    }

    @Test
    public void testGetPendingGames() {
        User user = new User("username", "password");
        PendingGame game = new PendingGame(rootModel.generateUniqueID(), "My First Game", user);

        assertEquals(0, rootModel.getPendingGames().size());

        rootModel.addPendingGame(game);

        assertEquals(1, rootModel.getPendingGames().size());
    }

    @Test
    public void testRemovePendingGame() {
        User user = new User("username", "password");
        PendingGame game = new PendingGame(rootModel.generateUniqueID(), "My First Game", user);

        rootModel.addPendingGame(game);

        assertEquals(1, rootModel.getPendingGames().size());

        rootModel.removePendingGame(game.getID());

        assertEquals(0, rootModel.getPendingGames().size());
    }

    @Test
    public void testActivateGame() {
        User user = new User("username", "password");
        PendingGame pendingGame = new PendingGame(rootModel.generateUniqueID(), "My First Game", user);

        rootModel.addPendingGame(pendingGame);

        assertEquals(1, rootModel.getPendingGames().size());

        ActiveGame activeGame = rootModel.activateGame(pendingGame.getID());

        assertEquals(0, rootModel.getPendingGames().size());
        assertEquals(pendingGame.getID(), activeGame.getID());
        assertEquals(pendingGame.getName(), activeGame.getName());
        assertEquals(pendingGame.getOwner(), activeGame.getOwner());
    }
}