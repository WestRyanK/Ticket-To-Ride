package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.shared.commands.CancelGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.commands.RegisterCommandData;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class CancelGameCommand extends CancelGameCommandData implements ICommand {
	public CancelGameCommand(String gameID) {
		super(gameID);
	}
	
	@Override
	public Result execute() {
		return new Result();
	}
}
