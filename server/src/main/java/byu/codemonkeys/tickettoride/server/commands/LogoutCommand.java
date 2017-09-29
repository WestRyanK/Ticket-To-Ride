package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.commands.LoginCommandData;
import byu.codemonkeys.tickettoride.shared.commands.LogoutCommandData;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class LogoutCommand extends LogoutCommandData implements ICommand {
	public LogoutCommand() {
		super();
	}
	
	@Override
	public Result execute() {
		return new Result();
	}
}
