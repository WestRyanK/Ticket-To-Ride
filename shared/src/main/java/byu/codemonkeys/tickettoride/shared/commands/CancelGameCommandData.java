package byu.codemonkeys.tickettoride.shared.commands;

public class CancelGameCommandData extends CommandData {
	private String gameID;
	
	public CancelGameCommandData(String gameID) {
		super(CommandType.CANCEL_GAME);
		this.gameID = gameID;
	}
	
	public String getGameID() {
		return gameID;
	}
}
