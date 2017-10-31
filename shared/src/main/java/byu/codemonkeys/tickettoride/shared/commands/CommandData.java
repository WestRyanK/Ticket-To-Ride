package byu.codemonkeys.tickettoride.shared.commands;

public abstract class CommandData {
	private String commandType;
	private String authToken;
	private int queuedPosition; // only gets set if command gets queued up
	
	protected CommandData(String commandType) {
		this.commandType = commandType;
		this.queuedPosition = -1; //not set
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

	public void setQueuedPosition(int queuedPosition) {
		this.queuedPosition = queuedPosition;
	}

	public int getQueuedPosition() {
		return queuedPosition;
	}
}
