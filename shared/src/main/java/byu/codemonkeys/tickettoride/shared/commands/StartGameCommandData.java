package byu.codemonkeys.tickettoride.shared.commands;

public class StartGameCommandData extends CommandData {
	private String gameID;
	
	public StartGameCommandData() {
		super(CommandType.START_GAME);
	}

}
