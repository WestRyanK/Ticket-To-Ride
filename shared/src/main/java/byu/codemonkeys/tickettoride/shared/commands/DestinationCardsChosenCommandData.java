package byu.codemonkeys.tickettoride.shared.commands;

/**
 * Created by Ryan on 11/18/2017.
 */

public class DestinationCardsChosenCommandData extends CommandData {
	protected final String username;
	
	protected final int cardsChosenCount;
	
	protected final int destinationCardsInDeckCount;
	
	protected final int playersDestinationCardCount;
	
	public DestinationCardsChosenCommandData(String username,
											 int cardsChosenCount,
											 int destinationCardsInDeckCount,
											 int playersDestinationCardCount) {
		super(CommandType.DESTINATION_CARDS_CHOSEN);
		this.username = username;
		this.cardsChosenCount = cardsChosenCount;
		this.destinationCardsInDeckCount = destinationCardsInDeckCount;
		this.playersDestinationCardCount = playersDestinationCardCount;
	}
}
