package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.commands.JoinPendingGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.LeavePendingGameCommandData;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class LeavePendingGameCommand extends LeavePendingGameCommandData implements ICommand {
	public LeavePendingGameCommand(String gameID) {
		super(gameID);
	}
	
	@Override
	public Result execute() {
		return ServerFacade.getInstance().leavePendingGame(this.getAuthToken(), this.getGameID());
	}
}
