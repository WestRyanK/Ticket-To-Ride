package byu.codemonkeys.tickettoride.shared.commands;

public class LeavePendingGameCommandData extends CommandData {
	private String gameID;
	
	public LeavePendingGameCommandData() {
		super(CommandType.LEAVE_PENDING_GAME);
	}
}
