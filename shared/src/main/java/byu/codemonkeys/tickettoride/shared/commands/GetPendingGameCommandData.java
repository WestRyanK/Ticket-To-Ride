package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a GetPendingGameCommand to the server to retrieve information about the game for
 * which the user is in the waiting room.
 */
public class GetPendingGameCommandData extends CommandData {
	public GetPendingGameCommandData() {
		super(CommandType.GET_PENDING_GAME);
	}
}

