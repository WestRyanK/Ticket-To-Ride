package byu.codemonkeys.tickettoride.shared.model.cards;

public class TrainCard implements Comparable {
	private final CardType cardColor;
	
	public CardType getCardColor() {
		return cardColor;
	}
	
	public TrainCard(CardType cardColor) {
		this.cardColor = cardColor;
	}
	
	@Override
	public String toString() {
		return cardColor.name();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (o instanceof TrainCard) {
			TrainCard other = (TrainCard) o;

			if (this.cardColor.equals(other.cardColor))
				return true;
		}

		return false;
	}

	@Override
	public int compareTo(Object o) {
		TrainCard that = (TrainCard) o;
		return cardColor.compareTo(that.cardColor);
	}
}
