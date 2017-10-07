package byu.codemonkeys.tickettoride.server.model;

import org.junit.Before;
import org.junit.Test;

import byu.codemonkeys.tickettoride.server.exceptions.EmptyGameException;

import static org.junit.Assert.*;

public class PendingGameTest {
    private PendingGame game;
    private User user;

    @Before
    public void setUp() {
        user = new User("username", "password");
        game = new PendingGame("game-id", "game-name", user);
    }

    @Test
    public void testRemoveUser() {
        User user = new User("user", "secret");
        game.addUser(user);
        game.removeUser(user);
        assertEquals(1, game.getUsers().size());
    }

    @Test
    public void testReplaceOwner() {
        User user = new User("user", "secret");
        game.addUser(user);
        game.removeUser(this.user);
        assertEquals(user, game.getOwner());
    }

    @Test
    public void testIsStartable() {
        assertFalse(game.isStartable());
    }
}