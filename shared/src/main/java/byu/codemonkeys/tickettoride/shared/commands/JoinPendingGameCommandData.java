package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a JoinPendingGameCommand to the server to join a pending game.
 */
public class JoinPendingGameCommandData extends CommandData {
	private String gameID;
	
	public JoinPendingGameCommandData(String gameID) {
		super(CommandType.JOIN_PENDING_GAME);
		this.gameID = gameID;
	}
	
	public String getGameID() {
		return gameID;
	}
}
