package byu.codemonkeys.tickettoride.shared.commands;

public abstract class CommandData {
	private String commandType;
	
	protected CommandData(String commandType) {
		this.commandType = commandType;
	}
	
	public String getCommandType() {
		return commandType;
	}
}
