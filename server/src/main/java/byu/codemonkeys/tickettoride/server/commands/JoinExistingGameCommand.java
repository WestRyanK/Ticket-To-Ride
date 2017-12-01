package byu.codemonkeys.tickettoride.server.commands;

import byu.codemonkeys.tickettoride.server.ServerFacade;
import byu.codemonkeys.tickettoride.shared.commands.JoinExistingGameCommandData;
import byu.codemonkeys.tickettoride.shared.commands.ICommand;
import byu.codemonkeys.tickettoride.shared.results.Result;

/**
 * Created by Ryan on 11/29/2017.
 */

public class JoinExistingGameCommand extends JoinExistingGameCommandData implements ICommand {
	public JoinExistingGameCommand(String authToken, String gameId) {
		super(gameId);
		this.setAuthToken(authToken);
	}
	
	@Override
	public Result execute() {
		return ServerFacade.getInstance().joinExistingGame(this.getAuthToken(), this.getGameId());
	}
}
