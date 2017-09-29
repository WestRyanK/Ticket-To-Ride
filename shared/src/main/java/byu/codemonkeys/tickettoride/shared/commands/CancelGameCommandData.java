package byu.codemonkeys.tickettoride.shared.commands;

public class CancelGameCommandData extends CommandData {
	private String gameID;
	
	public CancelGameCommandData(String gameID) {
		super("CancelGame");
		this.gameID = gameID;
	}
	
	public String getGameID() {
		return gameID;
	}
}
