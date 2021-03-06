package byu.codemonkeys.tickettoride.shared.commands;

/**
 * Created by Ryan on 11/14/2017.
 */

public class DeckTrainCardDrawnCommandData extends CommandData {
	
	protected final String username;
	
	protected final int trainCardsInDeckCount;
	
	protected final int playerTrainCardsCount;
	
	public DeckTrainCardDrawnCommandData(String username,
										 int trainCardsInDeckCount,
										 int playerTrainCardsCount) {
		super(CommandType.DECK_TRAIN_CARD_DRAWN);
		this.username = username;
		this.trainCardsInDeckCount = trainCardsInDeckCount;
		this.playerTrainCardsCount = playerTrainCardsCount;
	}
}
