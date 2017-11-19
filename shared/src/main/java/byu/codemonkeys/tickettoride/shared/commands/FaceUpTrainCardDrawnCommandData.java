package byu.codemonkeys.tickettoride.shared.commands;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

/**
 * Created by Ryan on 11/14/2017.
 */

public class FaceUpTrainCardDrawnCommandData extends CommandData {
	protected final String username;
	
	protected final TrainCard drawnCard;
	
	protected final List<TrainCard> newFaceUpCards;
	
	public FaceUpTrainCardDrawnCommandData(String username,
											  TrainCard drawnCard,
											  List<TrainCard> newFaceUpCards) {
		super(CommandType.FACEUP_TRAIN_CARD_DRAWN);
		this.username = username;
		this.drawnCard= drawnCard;
		this.newFaceUpCards = newFaceUpCards;
	}
}
