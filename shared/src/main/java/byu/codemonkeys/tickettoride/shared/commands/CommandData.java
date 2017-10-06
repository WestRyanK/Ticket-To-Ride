package byu.codemonkeys.tickettoride.shared.commands;

public abstract class CommandData {
	private String commandType;
	private String authToken;
	
	protected CommandData(String commandType) {
		this.commandType = commandType;
	}
	
	public String getCommandType() {
		return commandType;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getAuthToken() {
		return authToken;
	}
}
