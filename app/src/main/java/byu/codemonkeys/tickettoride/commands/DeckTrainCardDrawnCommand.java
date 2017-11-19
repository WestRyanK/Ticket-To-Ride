package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.DeckTrainCardDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;

/**
 * Created by Ryan on 11/14/2017.
 */

public class DeckTrainCardDrawnCommand extends DeckTrainCardDrawnCommandData implements IClientCommand {
	
	protected DeckTrainCardDrawnCommand(String username) {
		super(username);
	}
	
	@Override
	public void execute() {
		Player player = ModelRoot.getInstance().getGame().getPlayer(this.username);
		if (player.getClass() == Opponent.class) {
			Opponent opponent = (Opponent) player;
			opponent.setNumTrains(opponent.getNumTrainCards() + 1);
		}
		
		int numHidden = ModelRoot.getInstance().getGame().getDeck().getNumHidden();
		 ModelRoot.getInstance().getGame().getDeck().setNumHidden(numHidden - 1);
		
	}
	
	// TODO: Add toString
}
