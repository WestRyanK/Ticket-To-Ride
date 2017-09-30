package byu.codemonkeys.tickettoride.shared.commands;

public class LeavePendingGameCommandData extends CommandData {
	private String gameID;
	
	public LeavePendingGameCommandData(String gameID) {
		super(CommandType.LEAVE_PENDING_GAME);
		this.gameID = gameID;
	}
	
	public String getGameID() {
		return gameID;
	}
}
