package byu.codemonkeys.tickettoride.shared.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.shared.model.cards.Deck;
import byu.codemonkeys.tickettoride.shared.model.cards.IDeck;
import byu.codemonkeys.tickettoride.shared.model.map.GameMap;
import byu.codemonkeys.tickettoride.shared.model.turns.ActiveTurn;
import byu.codemonkeys.tickettoride.shared.model.turns.OtherTurn;
import byu.codemonkeys.tickettoride.shared.model.turns.Turn;

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
	public static final String GAME_OVER = "GameOver";
	
	protected GameMap map;
	protected Turn turn;
	protected List<Player> players;
	protected IDeck deck;
	
	protected int minAllowedDestinationCardsDrawn;
	public static final int INITIAL_MIN_ALLOWED_DESTINATION_CARDS_DRAWN = 2;
	public static final int SUBSEQUENT_MIN_ALLOWED_DESTINATION_CARDS_DRAWN = 1;
	
	public ActiveGame(GameBase game) {
		map = new GameMap();
		this.players = new ArrayList<>();
		deck = new Deck();
		
		// Copy GameBase fields
		this.minAllowedDestinationCardsDrawn = INITIAL_MIN_ALLOWED_DESTINATION_CARDS_DRAWN;
		this.gameID = game.getID();
		this.gameName = game.getName();
		this.gameOwner = game.getOwner();
		this.gameUsers = game.getUsers();
		this.started = true;
	}
	
	public void setTurn(String username) {
		while (!this.getPlayers().get(this.turn.getPlayerIndex()).getUsername().equals(username)) {
			this.nextTurn();
		}
	}
	
	public void setUpTurns() {

		if (turn != null) {
			Turn temp = turn;
			Turn nextTurn;

			int index = (turn.getPlayerIndex() + 1) % players.size();

			for (int i = 1; i < players.size(); i++) {
				if (players.get(index) instanceof Self) {
					nextTurn = new ActiveTurn(index);
				} else {
					nextTurn = new OtherTurn(index);
				}
				temp.setNextTurn(nextTurn);
				temp = temp.getNextTurn();
				index++;
			}

			temp.setNextTurn(turn);
		} else {
			Turn firstTurn;

			if (players.get(0) instanceof Self) {
				firstTurn = new ActiveTurn(0);
			} else {
				firstTurn = new OtherTurn(0);
			}

			Turn temp = firstTurn;
			Turn nextTurn;

			for (int i = 1; i < players.size(); i++) {
				if (players.get(i) instanceof Self) {
					nextTurn = new ActiveTurn(i);
				} else {
					nextTurn = new OtherTurn(i);
				}
				temp.setNextTurn(nextTurn);
				temp = temp.getNextTurn();
			}

			temp.setNextTurn(firstTurn);
			turn = firstTurn;
		}
	}
	
	public static ActiveGame copyActiveGame(ActiveGame game) {
		ActiveGame activeGame = new ActiveGame(game);
		activeGame.setObservesChildren(true);
		activeGame.setDeck(game.getDeck());
		activeGame.setMap(game.map);
		activeGame.getMap().updateClaimedRoutes();
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
		return players.get(turn.getPlayerIndex()).getUsername().equals(username);
	}
	
	public Player getCurrentPlayer() {
		return players.get(turn.getPlayerIndex());
	}
	
	public Turn getTurn() {
		return turn;
	}
	
	public void setTurn(Turn turn) {
		this.turn = turn;
		setChanged();
		notifyObservers(TURN_UPDATE);
	}
	
	public void nextTurn() {
		turn.reset();
		turn = turn.getNextTurn();
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
	
	public Player getPlayer(String username) {
		for (Player player : players) {
			//            if (user.equals(player)) {
			if (player.getUsername().equals(username)) {
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
		if (this.deck != null && this.observesChildren())
			((Deck) this.deck).deleteObserver(this);
		this.deck = deck;
		if (this.deck != null && this.observesChildren())
			((Deck) this.deck).addObserver(this);
		setChanged();
		notifyObservers(DECK_UPDATE);
	}
	
	public int getMinAllowedDestinationCardsDrawn() {
		return minAllowedDestinationCardsDrawn;
	}
	
	public void setMinAllowedDestinationCardsDrawn(int minAllowedDestinationCardsDrawn) {
		this.minAllowedDestinationCardsDrawn = minAllowedDestinationCardsDrawn;
	}
	
	@Override
	public void update(Observable observable, Object o) {
		setChanged();
		notifyObservers(o);
	}
	
	public void end() {
		setChanged();
		notifyObservers(GAME_OVER);
	}
}
