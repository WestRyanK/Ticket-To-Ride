package byu.codemonkeys.tickettoride.models.cards;

/**
 * Created by Ryan on 10/17/2017.
 */

public class TrainCard {
	private CardColorEnum cardColor;
	
	public CardColorEnum getCardColor() {
		return cardColor;
	}
	
	public TrainCard(CardColorEnum cardColor) {
		this.cardColor = cardColor;
	}
}
