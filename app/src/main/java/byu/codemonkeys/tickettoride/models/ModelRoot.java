package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import byu.codemonkeys.tickettoride.models.history.CommandHistoryEntry;
import byu.codemonkeys.tickettoride.models.history.HistoryManager;
import byu.codemonkeys.tickettoride.shared.model.*;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public class ModelRoot extends Observable implements Observer {
	private static ModelRoot instance;
	private UserBase user;
	private GameBase pendingGame;
	private List<GameBase> pendingGames;
	private Session session;
	private ActiveGame game;
	private HistoryManager history;
	private List<TrainCard> trainCards;
	private List<DestinationCard> destinationCards;
	
	
	private ModelRoot() {
		history = new HistoryManager();
	}
	
	public static ModelRoot getInstance() {
		if (instance == null) {
			instance = new ModelRoot();
		}
		return instance;
	}
	
	public void clear() {
		instance = null;
	}
	
	public UserBase getUser() {
		return this.user;
	}
	
	public void setUser(UserBase user) {
		this.user = user;
	}
	
	public List<GameBase> getPendingGames() {
		return pendingGames;
	}
	
	public GameBase getPendingGame() {
		return pendingGame;
	}
	
	public void setPendingGame(GameBase game) {
		pendingGame = game;
		setChanged();
		notifyObservers(ModelFacade.PENDING_GAME_UPDATE);
	}
	
	public void setPendingGames(List<GameBase> games) {
		this.pendingGames = games;
		setChanged();
		notifyObservers(ModelFacade.PENDING_GAMES_UPDATE);
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public void setGame(ActiveGame game) {
		if (this.game != null && this.game.countObservers() > 0)
			this.game.deleteObservers();
		this.game = game;
		if (this.game != null) {
			this.game.addObserver(this);
			this.game.setObservesChildren(true);
		}
		setChanged();
		notifyObservers(ModelFacade.GAME_UPDATE);
	}
	
	public ActiveGame getGame() {
		return this.game;
	}
	
	public List<CommandHistoryEntry> getGameHistory() {
		return history.getCommandHistory();
	}
	
	public int getLastReadCommandIndex() {
		return history.getLastReadCommandIndex();
	}
	
	public void addMessage(Message message) {
		// no-op
	}
	
	public HistoryManager getHistoryManager() {
		return history;
	}
	
	public void historyUpdated() {
		setChanged();
		notifyObservers(ModelFacade.HISTORY_UPDATE);
	}
	
	@Override
	public void update(Observable observable, Object o) {
		setChanged();
		notifyObservers(o);
	}
}
