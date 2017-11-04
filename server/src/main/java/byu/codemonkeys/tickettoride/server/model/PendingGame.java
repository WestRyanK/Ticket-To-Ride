package byu.codemonkeys.tickettoride.server.model;

import java.util.ArrayList;
import java.util.List;

import byu.codemonkeys.tickettoride.server.exceptions.EmptyGameException;
import byu.codemonkeys.tickettoride.server.exceptions.FullGameException;
import byu.codemonkeys.tickettoride.shared.model.GameBase;
import byu.codemonkeys.tickettoride.shared.model.UserBase;


public class PendingGame extends GameBase {
    
    public PendingGame(String gameID, String gameName, User owner) {
        this.gameName = gameName;
        this.gameOwner = owner;
        this.gameID = gameID;
        this.gameUsers = new ArrayList<>();
        this.started = false;

        this.gameUsers.add(owner);
    }

    /**
     * Adds the specified User to the game.
     * @param user a User.
     * @throws FullGameException if the game is full (the player limit has been reached).
     */
    public void addUser(User user) throws FullGameException {
        if (gameUsers.size() >= MAX_PLAYERS) {
            throw new FullGameException();
        }

        this.gameUsers.add(user);
    }

    /**
     * Removes the specified User from the game.
     * @param user a User.
     */
    public void removeUser(User user) throws EmptyGameException {
        this.gameUsers.remove(user);

        if (user.equals(gameOwner)) {
            replaceOwner();
        }
    }

    public boolean isStartable() {
        return gameUsers.size() >= MIN_PLAYERS;
    }

    /**
     * Sets the game owner to an arbitrary player.
     * @throws EmptyGameException if there are no players in the game.
     */
    private void replaceOwner() throws EmptyGameException {
        if (gameUsers.size() == 0) {
            throw new EmptyGameException();
        }

        this.gameOwner = this.gameUsers.iterator().next();
    }
}
