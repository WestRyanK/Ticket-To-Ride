package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observable;

import byu.codemonkeys.tickettoride.shared.model.*;
import byu.codemonkeys.tickettoride.shared.model.DestinationCard;

/**
 * Created by Megan on 10/3/2017.
 */
public class ModelRoot extends Observable {
	private static ModelRoot instance;
	private UserBase user;
	private GameBase pendingGame;
	private List<GameBase> pendingGames;
	private Session session;
	private ActiveGame game;
	private List<GameHistoryEntry> gameHistory;
	private List<Message> messages;
	private List<byu.codemonkeys.tickettoride.shared.model.TrainCard> trainCards;
	private List<DestinationCard> destinationCards;

	
	private ModelRoot() {
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
		this.game = game;
	}
	
	public ActiveGame getGame() {
		return this.game;
	}

	public void addMessage(Message m){
		messages.add(m);
	}

	public List<Message> getMessages(){
		return messages;
	}

	public void removeTrainCard(byu.codemonkeys.tickettoride.shared.model.TrainCard card){
		trainCards.remove(card);
	}

	public void removeDestinationCard(DestinationCard card) {
		destinationCards.remove(card);
	}

	public List<GameHistoryEntry> getGameHistory() {
		return gameHistory;
	}

	public void addTrainCards(List<byu.codemonkeys.tickettoride.shared.model.TrainCard> cards){
		trainCards.addAll(cards);
	}

	public void addDestinationCards(List<DestinationCard> cards){
		destinationCards.addAll(cards);
	}
}
