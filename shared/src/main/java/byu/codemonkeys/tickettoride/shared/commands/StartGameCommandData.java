package byu.codemonkeys.tickettoride.shared.commands;

public class StartGameCommandData extends CommandData {
	private String gameID;
	
	public StartGameCommandData(String gameID) {
		super("StartGame");
		this.gameID = gameID;
	}
	
	public String getGameID() {
		return gameID;
	}
}
