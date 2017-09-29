package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.shared.commands.GetPendingGamesCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.commands.JoinPendingGameCommandData;
import byu.codemonkeys.tickettoride.shared.results.PendingGamesResult;
import byu.codemonkeys.tickettoride.shared.results.Result;

public class JoinPendingGameCommand extends JoinPendingGameCommandData implements ICommand {
	public JoinPendingGameCommand(String gameID) {
		super(gameID);
	}
	
	@Override
	public Result execute() {
		return new PendingGamesResult();
	}
}
