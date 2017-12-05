package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a LeavePendingGameCommand to the server to leave the game the user has joined.
 */
public class LeavePendingGameCommandData extends CommandData {
	
	public LeavePendingGameCommandData() {
		super(CommandType.LEAVE_PENDING_GAME);
	}
}
