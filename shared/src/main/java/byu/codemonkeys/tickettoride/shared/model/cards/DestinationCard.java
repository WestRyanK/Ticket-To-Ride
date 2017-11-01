package byu.codemonkeys.tickettoride.shared.model.cards;

/**
 * Created by Ryan on 10/18/2017.
 */

public class DestinationCard {
	// region DestinationA Property
	private int destinationA;
	
	public int getDestinationA() {
		return destinationA;
	}
	// endregion
	
	// region DestinationB Property
	private int destinationB;
	
	public int getDestinationB() {
		return destinationB;
	}
	// endregion
	
	// region Point Value Property
	private int pointValue;
	
	public int getPointValue() {
		return pointValue;
	}
	// endregion
	
	public DestinationCard(int destinationA, int destinationB, int pointValue) {
		this.destinationA = destinationA;
		this.destinationB = destinationB;
		this.pointValue = pointValue;
	}
}
