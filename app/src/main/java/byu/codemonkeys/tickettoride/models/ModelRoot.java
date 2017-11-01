package byu.codemonkeys.tickettoride.models;

import java.util.List;
import java.util.Observable;

import byu.codemonkeys.tickettoride.models.history.ChatManager;
import byu.codemonkeys.tickettoride.models.history.CommandHistoryEntry;
import byu.codemonkeys.tickettoride.models.history.HistoryManager;
import byu.codemonkeys.tickettoride.shared.model.*;
import byu.codemonkeys.tickettoride.shared.model.cards.DestinationCard;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public class ModelRoot extends Observable {
	private static ModelRoot instance;
	private UserBase user;
	private GameBase pendingGame;
	private List<GameBase> pendingGames;
	private Session session;
	private ActiveGame game;
	private HistoryManager history;
	private ChatManager chat;
	private List<TrainCard> trainCards;
	private List<DestinationCard> destinationCards;

	
	private ModelRoot() {
		history = new HistoryManager();
		chat = new ChatManager();
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

	public List<Message> getMessages(){
		return chat.getMessages();
	}

	public void removeTrainCard(TrainCard card){
		trainCards.remove(card);
	}

	public void removeDestinationCard(DestinationCard card) {
		destinationCards.remove(card);
	}

	public List<CommandHistoryEntry> getGameHistory() {
		return history.getCommandHistory();
	}

	public void addTrainCards(List<TrainCard> cards){
		trainCards.addAll(cards);
	}

	public void addDestinationCards(List<DestinationCard> cards){
		destinationCards.addAll(cards);
	}

	public void addDestinationCard(DestinationCard card){
		destinationCards.add(card);
	}

	public void addTrainCard(TrainCard card){
		trainCards.add(card);
	}

	public int getLastReadCommandIndex() {
		return history.getLastReadCommandIndex();
	}

	public void addMessage(Message message) {
		chat.addMessage(message);
	}

	public HistoryManager getHistoryManager() {
		return history;
	}
}
