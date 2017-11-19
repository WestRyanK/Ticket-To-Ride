package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.DeckTrainCardDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;

/**
 * Created by Ryan on 11/14/2017.
 */

public class DeckTrainCardDrawnCommand extends DeckTrainCardDrawnCommandData implements IClientCommand {
	
	protected DeckTrainCardDrawnCommand(String username, int trainCardsInDeckCount) {
		super(username, trainCardsInDeckCount);
	}
	
	@Override
	public void execute() {
		Player player = ModelRoot.getInstance().getGame().getPlayer(this.username);
		if (player.getClass() == Opponent.class) {
			Opponent opponent = (Opponent) player;
			opponent.setNumTrains(opponent.getNumTrainCards() + 1);
		}
		
		ModelRoot.getInstance().getGame().getDeck().setTrainCardsDeckCount(trainCardsInDeckCount);
	}
	
	@Override
	public String getAuthToken() {
		return String.format("[%1$s drew a train card from the deck]", this.username);
	}
}
