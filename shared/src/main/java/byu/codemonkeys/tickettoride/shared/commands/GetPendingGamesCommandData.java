package byu.codemonkeys.tickettoride.shared.commands;

public class GetPendingGamesCommandData extends CommandData {
	protected String gameID;
	public GetPendingGamesCommandData() {
		super(CommandType.GET_PENDING_GAMES);
	}
}
