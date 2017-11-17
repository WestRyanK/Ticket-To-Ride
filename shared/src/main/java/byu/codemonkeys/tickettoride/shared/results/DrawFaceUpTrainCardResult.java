package byu.codemonkeys.tickettoride.shared.results;

import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

/**
 * Created by Ryan on 11/10/2017.
 */

public class DrawFaceUpTrainCardResult extends Result {
	
	private final TrainCard drawnCard;
	
	public DrawFaceUpTrainCardResult(String errorMessage) {
		super(errorMessage);
		drawnCard = null;
	}
	
	public DrawFaceUpTrainCardResult(TrainCard drawnCard) {
		super();
		this.drawnCard = drawnCard;
	}
	
	public TrainCard getDrawnCard() {
		return drawnCard;
	}
}
