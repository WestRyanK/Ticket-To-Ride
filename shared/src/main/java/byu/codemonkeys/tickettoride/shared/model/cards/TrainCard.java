package byu.codemonkeys.tickettoride.shared.model.cards;

/**
 * Created by meganrich on 10/18/17.
 */

public class TrainCard {
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
}
