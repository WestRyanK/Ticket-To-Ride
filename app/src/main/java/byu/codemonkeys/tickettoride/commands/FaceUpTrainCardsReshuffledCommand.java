package byu.codemonkeys.tickettoride.commands;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.commands.FaceUpTrainCardDrawnCommandData;
import byu.codemonkeys.tickettoride.shared.commands.FaceUpTrainCardsReshuffledCommandData;
import byu.codemonkeys.tickettoride.shared.model.cards.TrainCard;

/**
 * Created by Ryan on 11/20/2017.
 */

public class FaceUpTrainCardsReshuffledCommand extends FaceUpTrainCardsReshuffledCommandData implements IClientCommand {
	
	@Override
	public void execute() {
		
	}
	
	@Override
	public String toString() {
		return "[Uh oh! There were more than two face up wild cards! The face up cards have been shuffled]";
	}
}
