package byu.codemonkeys.tickettoride.models.player;

/**
 * Created by Ryan on 10/3/2017.
 */

public class Player {
	private String username;
	private PlayerColorEnum playerColor;
	private int score;
	private int trainsCount;
	private int trainCardsCount;
	private int destinationCardsCount;
	
	public Player(String username,
				  PlayerColorEnum playerColor,
				  int score,
				  int trainsCount,
				  int trainCardsCount,
				  int destinationCardsCount) {
		this.username = username;
		this.playerColor = playerColor;
		this.score = score;
		this.trainsCount = trainsCount;
		this.trainCardsCount = trainCardsCount;
		this.destinationCardsCount = destinationCardsCount;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public PlayerColorEnum getPlayerColor() {
		return playerColor;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getTrainCardsCount() {
		return trainCardsCount;
	}
	
	public int getTrainsCount() {
		return trainsCount;
	}
	
	public int getDestinationCardsCount() {
		return destinationCardsCount;
	}
}
