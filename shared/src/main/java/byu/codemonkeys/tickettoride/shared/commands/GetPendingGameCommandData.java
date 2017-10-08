package byu.codemonkeys.tickettoride.shared.commands;

public class GetPendingGameCommandData extends CommandData {
	protected String gameID;
	public GetPendingGameCommandData(String gameID) {
		super(CommandType.GET_PENDING_GAME);
		this.gameID = gameID;
	}


	public String getGameID() {
		return this.gameID;
	}
}

