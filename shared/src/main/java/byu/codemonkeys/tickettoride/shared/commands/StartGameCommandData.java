package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a StartGameCommand to the server to start the pending game for which the user is
 * in the waiting room.
 */
public class StartGameCommandData extends CommandData {
	private String gameID;
	
	public StartGameCommandData(String gameID) {
		super(CommandType.START_GAME);
		this.gameID = gameID;
	}
	
	public String getGameID() {
		return gameID;
	}
}
