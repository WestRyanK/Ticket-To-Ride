package byu.codemonkeys.tickettoride.shared.model;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * We need to decide whether ActiveGame should extend GameBase. As it stands, ActiveGame doesn't
 * need any of the fields of GameBase except gameID. What we might want to do is move all the other
 * fields of GameBase to a shared PendingGame class.
 */
public class ActiveGame {
    public static final int MAX_TRAINS = 45;

//    private GameMap map;
    private int turn;

    private List<Player> players;

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

    public int getTurn() {
        return turn;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
