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
										  TrainCard drawnCard,
										  List<TrainCard> newFaceUpCards, int trainCardsDeckSize, int playerTrainCardCount) {
		super(username, drawnCard, newFaceUpCards, trainCardsDeckSize, playerTrainCardCount);
	}
	
	@Override
	public void execute() {
		Player player = ModelRoot.getInstance().getGame().getPlayer(this.username);
		if (player.getClass() == Opponent.class) {
			Opponent opponent = (Opponent) player;
			opponent.setNumTrainCards(this.playerTrainCardsCount);
		}

		ModelRoot.getInstance().getGame().getTurn().drawFaceUpTrainCard(drawnCard);
		ModelRoot.getInstance().getGame().getDeck().setFaceUpTrainCards(newFaceUpCards);
		ModelRoot.getInstance().getGame().getDeck().setTrainCardsDeckCount(trainCardsDeckSize);
	}
	
	@Override
	public String toString() {
		return String.format("[%1$s drew a %2$s card]", this.username, this.drawnCard);
	}
}
