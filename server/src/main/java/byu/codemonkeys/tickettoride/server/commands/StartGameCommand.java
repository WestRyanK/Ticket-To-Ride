package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.commands.StartGameCommandData;
import byu.codemonkeys.tickettoride.shared.results.Result;
import byu.codemonkeys.tickettoride.shared.results.StartGameResult;

public class StartGameCommand extends StartGameCommandData implements ICommand {
	
	public StartGameCommand(String gameID) {
		super(gameID);
	}
	
	@Override
	public Result execute() {
		return new StartGameResult();
	}
}
