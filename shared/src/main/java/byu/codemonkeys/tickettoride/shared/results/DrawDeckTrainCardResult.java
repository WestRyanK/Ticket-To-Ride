package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

public class DrawDeckTrainCardResult extends Result {
	private final TrainCard drawnCard;
	
	public DrawDeckTrainCardResult(TrainCard drawnCard) {
		super();
		this.drawnCard = drawnCard;
	}
	
	public DrawDeckTrainCardResult(String errorMessage) {
		super(errorMessage);
		drawnCard = null;
	}
	
	public TrainCard getDrawnCard() {
		return drawnCard;
	}
}
