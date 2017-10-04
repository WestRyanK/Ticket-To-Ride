package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.CreateGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class CreateGameCommand extends CreateGameCommandData implements ICommand {
	public CreateGameCommand(String gameName, int minPlayers, int maxPlayers) {
		super(gameName, minPlayers, maxPlayers);
	}
	
	@Override
	public Result execute() {
		return ServerFacade.getInstance().createGame(this.getAuthToken(), this.getGameName(),
				this.getMinPlayers(), this.getMaxPlayers());
	}
}
