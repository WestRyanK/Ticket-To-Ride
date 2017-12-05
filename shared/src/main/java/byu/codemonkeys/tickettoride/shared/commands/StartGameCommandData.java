package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a StartGameCommand to the server to start the pending game for which the user is
 * in the waiting room.
 */
public class StartGameCommandData extends CommandData {
	
	public StartGameCommandData() {
		super(CommandType.START_GAME);
	}

}
