package byu.codemonkeys.tickettoride.shared.commands;

import byu.codemonkeys.tickettoride.shared.model.Session;

public abstract class CommandData {
	private String commandType;
	private Session userSession;
	
	protected CommandData(String commandType) {
		this.commandType = commandType;
	}
	
	public String getCommandType() {
		return commandType;
	}

	public void setUserSession(Session userSession) {
		this.userSession = userSession;
	}

	public Session getUserSession() {
		return userSession;
	}
}
