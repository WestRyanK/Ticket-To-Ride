package byu.codemonkeys.tickettoride.commands;

import byu.codemonkeys.tickettoride.shared.commands.DeckTrainCardDrawnCommandData;

/**
 * Created by Ryan on 11/14/2017.
 */

public class DeckTrainCardDrawnCommand extends DeckTrainCardDrawnCommandData implements IClientCommand {
	
	protected DeckTrainCardDrawnCommand(String username) {
		super(username);
	}
	
	@Override
	public void execute() {
	}
	
	// TODO: Add toString
}
