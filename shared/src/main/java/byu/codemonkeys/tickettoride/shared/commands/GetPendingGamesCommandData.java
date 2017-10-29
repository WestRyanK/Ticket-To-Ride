package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a GetPendingGamesCommand to the server to retrieve information about all pending
 * games (the lobby).
 */
public class GetPendingGamesCommandData extends CommandData {
	protected String gameID;
	public GetPendingGamesCommandData() {
		super(CommandType.GET_PENDING_GAMES);
	}
}
