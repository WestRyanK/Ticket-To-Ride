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
										 int cardsChosenCount,
										 int destinationCardsInDeckCount,
										 int playersDestinationCardCount) {
		super(username, cardsChosenCount, destinationCardsInDeckCount, playersDestinationCardCount);
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
		return String.format("[%1$s chose %2$d destination card%3$s]",
							 this.username,
							 this.cardsChosenCount, this.cardsChosenCount == 1 ? "" : "s");
	}
}
