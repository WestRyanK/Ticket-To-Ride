package byu.codemonkeys.tickettoride.shared.commands;

public class CreateGameCommandData extends CommandData {
	private String gameName;
	private int minPlayers;
	private int maxPlayers;
	
	public CreateGameCommandData(String gameName, int minPlayers, int maxPlayers) {
		super(CommandType.CREATE_GAME);
		this.gameName = gameName;
		this.minPlayers = minPlayers;
		this.maxPlayers = maxPlayers;
	}
	
	public int getMaxPlayers() {
		return maxPlayers;
	}
	
	public int getMinPlayers() {
		return minPlayers;
	}
	
	public String getGameName() {
		return gameName;
	}
}
