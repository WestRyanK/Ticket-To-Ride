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
	
	protected final int trainCardsDeckSize;
	
	protected final int playerTrainCardsCount;
	
	public FaceUpTrainCardDrawnCommandData(String username,
										   TrainCard drawnCard,
										   List<TrainCard> newFaceUpCards,
										   int trainCardsDeckSize,
										   int playerTrainCardsCount) {
		super(CommandType.FACEUP_TRAIN_CARD_DRAWN);
		this.username = username;
		this.drawnCard= drawnCard;
		this.newFaceUpCards = newFaceUpCards;
		this.trainCardsDeckSize = trainCardsDeckSize;
		this.playerTrainCardsCount = playerTrainCardsCount;
	}
}
