package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.DestinationCardsChosenCommandData;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;

/**
 * Created by Ryan on 11/18/2017.
 */

public class DestinationCardsChosenCommand extends DestinationCardsChosenCommandData implements IClientCommand {
	public DestinationCardsChosenCommand(String username,
										 int cardsDrawnCount,
										 int destinationCardsInDeckCount,
										 int playersDestinationCardCount) {
		super(username, cardsDrawnCount, destinationCardsInDeckCount, playersDestinationCardCount);
	}
	
	@Override
	public void execute() {
		Player player = ModelRoot.getInstance().getGame().getPlayer(this.username);
		if (player.getClass() == Opponent.class) {
			Opponent opponent = (Opponent) player;
			opponent.setNumDestinationCards(this.playersDestinationCardCount);
		}
		
		ModelRoot.getInstance().getGame().getDeck().setDestinationCardsCount(this.destinationCardsInDeckCount);
	}
	
	@Override
	public String toString() {
		return String.format("[$s chose $d destination cards]",
							 this.username,
							 this.cardsDrawnCount);
	}
}
