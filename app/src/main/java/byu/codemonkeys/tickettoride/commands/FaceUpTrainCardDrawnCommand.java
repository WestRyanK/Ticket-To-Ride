package byu.codemonkeys.tickettoride.commands;

import java.util.List;

import byu.codemonkeys.tickettoride.models.ModelRoot;
import byu.codemonkeys.tickettoride.shared.commands.FaceUpTrainCardDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.model.Opponent;
import byu.codemonkeys.tickettoride.shared.model.Player;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

/**
 * Created by Ryan on 11/14/2017.
 */

public class FaceUpTrainCardDrawnCommand extends FaceUpTrainCardDrawnCommandData implements IClientCommand {
	
	protected FaceUpTrainCardDrawnCommand(String username,
										  int drawnCardIndex,
										  List<TrainCard> newFaceUpCards) {
		super(username, drawnCardIndex, newFaceUpCards);
	}
	
	@Override
	public void execute() {
		Player player = ModelRoot.getInstance().getGame().getPlayer(this.username);
		if (player.getClass() == Opponent.class) {
			Opponent opponent = (Opponent) player;
			opponent.setNumTrains(opponent.getNumTrainCards() + 1);
		}
		
		ModelRoot.getInstance().getGame().getDeck().setRevealed(newFaceUpCards);
	}
	
	// TODO: Add toString
}
