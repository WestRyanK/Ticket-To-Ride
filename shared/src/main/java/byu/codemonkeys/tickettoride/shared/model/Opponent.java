package byu.codemonkeys.tickettoride.shared.model;

public class Opponent extends Player {
	public Opponent(String userName) {
		super(userName, Type.Opponent);
	}
	
	public Opponent(Player player) {
		this(player.getUsername());
		this.numTrainCards = player.getNumTrainCards();
		this.numTrains = player.getNumTrains();
		this.score = player.getScore();
		this.numDestinationCards = player.getNumDestinationCards();
		this.color = player.getColor();
	}
	
	public void setNumDestinationCards(int numDestinationCards) {
		this.numDestinationCards = numDestinationCards;
		setChanged();
		notifyObservers(Player.PLAYER_DESTINATION_CARDS_UPDATE);
	}
	
	public void setNumTrainCards(int numTrainCards){
		this.numTrainCards = numTrainCards;
		setChanged();
		notifyObservers(Player.PLAYER_TRAIN_CARDS_UPDATE);
	}
}
