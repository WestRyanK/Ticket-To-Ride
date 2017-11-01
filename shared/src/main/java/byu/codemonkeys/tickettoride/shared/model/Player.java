package byu.codemonkeys.tickettoride.shared.model;

import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public abstract class Player extends UserBase {

	public static class Type {
		public static final String Self = "self";
		public static final String Opponent = "opponent";
	}
	
	protected String type;
	protected int score;
	protected PlayerColor color;
	protected int numTrainCards;
	protected int numDestinationCards;
	protected int numTrains;
	
	public static final String PLAYER_SCORE_UPDATE = "ScoreUpdate";
	public static final String PLAYER_COLOR_UPDATE = "ColorUpdate";
	public static final String PLAYER_TRAINS_UPDATE = "NumTrainsUpdate";
	public static final String PLAYER_TRAIN_CARDS_UPDATE = "NumTrainCardsUpdate";
	public static final String PLAYER_DESTINATION_CARDS_UPDATE = "NumDestinationCardsUpdate";
	
	protected Player(String userName, String type) {
		super(userName);
		this.type = type;
		this.numTrains = ActiveGame.MAX_TRAINS;
	}
	
	public static Player copyPlayer(Player player) {
		if (player.getClass() == Self.class) {
			return new Self(player);
		} else if (player.getClass() == Opponent.class) {
			return new Opponent(player);
		} else
			return null;
	}
	
	protected Player(String userName, String type, PlayerColor color) {
		super(userName);
		this.type = type;
		this.color = color;
		this.numTrains = ActiveGame.MAX_TRAINS;
	}
	
	public String getType() {
		return type;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) throws IllegalArgumentException {
		if (score < 0) {
			throw new IllegalArgumentException("A player's score may not be negative.");
		}
		
		this.score = score;
		setChanged();
		notifyObservers(PLAYER_SCORE_UPDATE);
	}
	
	public PlayerColor getColor() {
		return color;
	}
	
	public void setColor(PlayerColor color) {
		this.color = color;
		setChanged();
		notifyObservers(PLAYER_COLOR_UPDATE);
	}
	
	public int getNumTrainCards() {
		return numTrainCards;
	}
	
	public void addTrainCard(TrainCard card) {
		++numTrainCards;
		setChanged();
		notifyObservers(PLAYER_TRAIN_CARDS_UPDATE);
	}
	
	public int getNumDestinationCards() {
		return numDestinationCards;
	}
	
	public int getNumTrains() {
		return numTrains;
	}
	
	public void setNumTrains(int numTrains) {
		this.numTrains = numTrains;
		setChanged();
		notifyObservers(PLAYER_TRAINS_UPDATE);
	}
}
