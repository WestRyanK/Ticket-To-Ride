package byu.codemonkeys.tickettoride.shared.commands;

public abstract class CommandData {
	private String commandName;
	
	protected CommandData(String commandName) {
		this.commandName = commandName;
	}
	
	public String getCommandName() {
		return commandName;
	}
}
