package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.GetExistingGamesCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 11/29/2017.
 */

public class GetExistingGamesCommand extends GetExistingGamesCommandData implements ICommand {
	@Override
	public Result execute() {
		return ServerFacade.getInstance().getExistingGames(this.getAuthToken());
	}
}
