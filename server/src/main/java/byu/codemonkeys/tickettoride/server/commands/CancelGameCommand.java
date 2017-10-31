package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.CancelGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.commands.RegisterCommandData;
import byu.codemonkeys.tickettoride.shared.results.LoginResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class CancelGameCommand extends CancelGameCommandData implements ICommand {
	@Override
	public Result execute() {
		return ServerFacade.getInstance().cancelGame(this.getAuthToken());
	}
}
