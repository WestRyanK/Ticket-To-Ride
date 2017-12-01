package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a GetPendingGamesCommand to the server to retrieve information about all pending
 * games (the lobby).
 */
public class GetExistingGamesCommandData extends CommandData {
	
	public GetExistingGamesCommandData() {
		super(CommandType.GET_EXISTING_GAMES);
	}
}
