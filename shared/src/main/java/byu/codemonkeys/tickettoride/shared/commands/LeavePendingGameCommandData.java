package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a LeavePendingGameCommand to the server to leave the game the user has joined.
 */
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
