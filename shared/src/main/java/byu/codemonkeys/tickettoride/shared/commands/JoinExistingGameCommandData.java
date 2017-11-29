package byu.codemonkeys.tickettoride.shared.commands;

/**
 * Created by Ryan on 11/29/2017.
 */

public class JoinExistingGameCommandData extends CommandData {
	public JoinExistingGameCommandData(String authToken) {
		super(CommandType.JOIN_EXISTING_GAME);
		this.setAuthToken(authToken);
	}
}
