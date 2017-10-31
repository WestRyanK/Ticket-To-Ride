package byu.codemonkeys.tickettoride.shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import byu.codemonkeys.tickettoride.shared.model.map.GameMap;

/**
 * We need to decide whether ActiveGame should extend GameBase. As it stands, ActiveGame doesn't
 * need any of the fields of GameBase except gameID. What we might want to do is move all the other
 * fields of GameBase to a shared PendingGame class.
 */
public class ActiveGame extends GameBase {
    public static final int MAX_TRAINS = 45;

    protected GameMap map;
    protected int turn;
    protected List<Player> players;
    protected Deck deck;

    public ActiveGame(GameBase game) {
        map = new GameMap();
        turn = 0;
        deck = new Deck();

        // Copy GameBase fields
        this.gameID = game.getID();
        this.gameName = game.getName();
        this.gameOwner = game.getOwner();
        this.gameUsers = game.getUsers();
        this.started = true;
    }

    public ActiveGame(List<Player> players, int turn) {
        this.players = players;
        this.turn = turn;
    }

    /**
     * Returns the user's player.
     * @return the user's Self object.
     * @throws NoSuchElementException this is thrown if there is no player marked as the user's
     *         self. This should never be thrown because it indicates a bug. Perhaps an assertion
     *         should be used instead in the future.
     */
    public Self getSelf() throws NoSuchElementException {
        for (Player player : players) {
            if (player.getType() == Player.Type.Self) {
                return (Self) player;
            }
        }

        throw new NoSuchElementException("There is no player with type Self.");
    }

    public GameMap getMap() {
        return map;
    }

    public void setMap(GameMap map) {
        this.map = map;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
