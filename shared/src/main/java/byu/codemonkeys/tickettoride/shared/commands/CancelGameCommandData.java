package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a CancelGameCommand to the server to cancel a pending game.
 */
public class CancelGameCommandData extends CommandData {
	
	public CancelGameCommandData() {
		super(CommandType.CANCEL_GAME);
	}
}
