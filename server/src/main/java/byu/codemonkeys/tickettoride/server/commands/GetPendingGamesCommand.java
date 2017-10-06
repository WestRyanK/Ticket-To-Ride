package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.CreateGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.GetPendingGamesCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class GetPendingGamesCommand extends GetPendingGamesCommandData implements ICommand {
	public GetPendingGamesCommand() {
		super();
	}
	
	@Override
	public Result execute() {
		return ServerFacade.getInstance().getPendingGames(this.getAuthToken());
	}
}
