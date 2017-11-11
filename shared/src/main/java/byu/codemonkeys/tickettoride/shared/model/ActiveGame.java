package byu.codemonkeys.tickettoride.shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.shared.model.cards.Deck;
import byu.codemonkeys.tickettoride.shared.model.cards.IDeck;
import byu.codemonkeys.tickettoride.shared.model.map.GameMap;

import static sun.audio.AudioPlayer.player;

/**
 * We need to decide whether ActiveGame should extend GameBase. As it stands, ActiveGame doesn't
 * need any of the fields of GameBase except gameID. What we might want to do is move all the other
 * fields of GameBase to a shared PendingGame class.
 */
public class ActiveGame extends GameBase implements Observer {
	public static final int MAX_TRAINS = 45;
	public static final String MAP_UPDATE = "MapUpdate";
	public static final String TURN_UPDATE = "TurnUpdate";
	public static final String PLAYERS_UPDATE = "PlayersUpdate";
	public static final String DECK_UPDATE = "DeckUpdate";
	
	protected GameMap map;
	protected int turn;
	protected List<Player> players;
	protected IDeck deck;
	
	public ActiveGame(GameBase game) {
		map = new GameMap();
		turn = 0;
		this.players = new ArrayList<>();
		deck = new Deck();
		
		// Copy GameBase fields
		this.gameID = game.getID();
		this.gameName = game.getName();
		this.gameOwner = game.getOwner();
		this.gameUsers = game.getUsers();
		this.started = true;
	}
	
	public static ActiveGame copyActiveGame(ActiveGame game) {
		ActiveGame activeGame = new ActiveGame(game);
		activeGame.setObservesChildren(true);
		activeGame.deck = game.getDeck();
		List<Player> players = new ArrayList<>();
		for (Player player : game.getPlayers()) {
			players.add(Player.copyPlayer(player));
		}
		activeGame.setPlayers(players);
		
		return activeGame;
	}
	
	/**
	 * Returns the user's player.
	 *
	 * @return the user's Self object.
	 * @throws NoSuchElementException this is thrown if there is no player marked as the user's
	 *                                self. This should never be thrown because it indicates a bug. Perhaps an assertion
	 *                                should be used instead in the future.
	 */
	public Self getSelf() throws NoSuchElementException {
		for (Player player : players) {
			if (player instanceof Self) {
				return (Self) player;
			}
		}
		
		throw new NoSuchElementException("There is no player with type Self.");
	}
	
	public GameMap getMap() {
		return map;
	}
	
	public void setMap(GameMap map) {
		if (this.map != null && this.observesChildren())
			this.map.deleteObserver(this);
		this.map = map;
		if (this.map != null && this.observesChildren())
			this.map.addObserver(this);
		setChanged();
		notifyObservers(MAP_UPDATE);
	}

	public boolean isPlayersTurn(String username) {
		return players.get(turn).getUsername().equals(username);
	}
	
	public int getTurn() {
		return turn;
	}
	
	public void setTurn(int turn) {
		this.turn = turn;
		setChanged();
		notifyObservers(TURN_UPDATE);
	}
	
	public void nextTurn() {
		this.turn = (turn + 1) % players.size();
		setChanged();
		notifyObservers(TURN_UPDATE);
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public Player getPlayer(UserBase user) {
		for (Player player : players) {
			//            if (user.equals(player)) {
			if (user.userName.equals(player.userName)) {
				return player;
			}
		}
		
		return null;
	}
	
	public void setPlayers(List<Player> players) {
		if (this.players != null && this.observesChildren()) {
			for (Player player : this.players) {
				player.deleteObserver(this);
			}
		}
		this.players = players;
		if (this.players != null && this.observesChildren()) {
			for (Player player : this.players) {
				player.addObserver(this);
				player.setObservesChildren(true);
			}
		}
		setChanged();
		notifyObservers(PLAYERS_UPDATE);
	}
	
	public IDeck getDeck() {
		return deck;
	}
	
	public void setDeck(IDeck deck) {
		this.deck = deck;
		setChanged();
		notifyObservers(DECK_UPDATE);
	}
	
	@Override
	public void update(Observable observable, Object o) {
		setChanged();
		notifyObservers(o);
	}
}
