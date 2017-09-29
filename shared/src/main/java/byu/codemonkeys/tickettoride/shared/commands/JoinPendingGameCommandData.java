package byu.codemonkeys.tickettoride.shared.commands;

public class JoinPendingGameCommandData extends CommandData {
	private String gameID;
	
	public JoinPendingGameCommandData(String gameID) {
		super("JoinPendingGame");
		this.gameID = gameID;
	}
	
	public String getGameID() {
		return gameID;
	}
}
