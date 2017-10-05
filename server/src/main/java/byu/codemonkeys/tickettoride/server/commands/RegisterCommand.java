package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.commands.RegisterCommandData;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class RegisterCommand extends RegisterCommandData implements ICommand {
	public RegisterCommand(String userName, String password) {
		super(userName, password);
	}
	
	@Override
	public Result execute() {
		return ServerFacade.getInstance().register(getUserName(), getPassword());
	}
}
