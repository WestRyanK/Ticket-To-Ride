package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.commands.LoginCommandData;
import byu.codemonkeys.tickettoride.shared.commands.RegisterCommandData;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class LoginCommand extends LoginCommandData implements ICommand {
	public LoginCommand(String userName, String password) {
		super(userName, password);
	}
	
	@Override
	public Result execute() {
		return new LoginResult();
	}
}
