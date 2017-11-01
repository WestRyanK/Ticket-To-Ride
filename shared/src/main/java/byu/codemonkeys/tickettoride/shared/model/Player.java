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
	
	protected Player(String userName, String type) {
		super(userName);
		this.type = type;
		this.numTrains = ActiveGame.MAX_TRAINS;
	}
	
	public static Player copyPlayer(Player player) {
		if (player.getType() == Type.Self) {
			return new Self(player);
		} else if (player.getType() == Type.Opponent) {
			return new Opponent(player);
		}
		else
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
		//        setChanged();
		//        notifyObservers(SCORE_UPDATE);
	}
	
	public PlayerColor getColor() {
		return color;
	}
	
	public void setColor(PlayerColor color) {
		this.color = color;
		//        setChanged();
		//        notifyObservers(COLOR_UPDATE);
	}
	
	public int getNumTrainCards() {
		return numTrainCards;
	}
	
	public void addTrainCard(TrainCard card) {
		++numTrainCards;
		//        setChanged();
		//        notifyObservers(PLAYER_TRAIN_CARDS_UPDATE);
	}
	
	public int getNumDestinationCards() {
		return numDestinationCards;
	}
	
	public int getNumTrains() {
		return numTrains;
	}
	
	public void setNumTrains(int numTrains) {
		this.numTrains = numTrains;
		//        setChanged();
		//        notifyObservers(PLAYER_TRAINS_UPDATE);
	}
}
