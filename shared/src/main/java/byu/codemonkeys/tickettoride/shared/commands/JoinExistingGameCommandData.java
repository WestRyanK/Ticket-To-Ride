package byu.codemonkeys.tickettoride.shared.commands;

/**
 * Created by Ryan on 11/29/2017.
 */

public class JoinExistingGameCommandData extends CommandData {
	
	private final String gameId;
	
	public JoinExistingGameCommandData( String gameId) {
		super(CommandType.JOIN_EXISTING_GAME);
		this.gameId = gameId;
	}
	
	public String getGameId() {
		return gameId;
	}
}
