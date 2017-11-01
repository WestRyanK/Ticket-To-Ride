package byu.codemonkeys.tickettoride.shared.model.cards;

/**
 * Created by Ryan on 10/18/2017.
 */

public class DestinationCard {
	//region Properties
	// region ID Property
	private final int id;
	
	public int getId() {
		return id;
	}
	// endregion
	
	// region DestinationA Property
	private final int destinationA;
	
	public int getDestinationA() {
		return destinationA;
	}
	// endregion
	
	// region DestinationB Property
	private final int destinationB;
	
	public int getDestinationB() {
		return destinationB;
	}
	// endregion
	
	// region Point Value Property
	private final int pointValue;
	
	public int getPointValue() {
		return pointValue;
	}
	// endregion
	// endregion
	
	public DestinationCard(int id, int destinationA, int destinationB, int pointValue) {
		this.destinationA = destinationA;
		this.destinationB = destinationB;
		this.pointValue = pointValue;
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (o == this) {
			return true;
		}

		if (o.getClass() != this.getClass()) {
			return false;
		}

		DestinationCard other = (DestinationCard) o;

		return (destinationA == other.destinationA) && (destinationB == other.destinationB);
	}
}
