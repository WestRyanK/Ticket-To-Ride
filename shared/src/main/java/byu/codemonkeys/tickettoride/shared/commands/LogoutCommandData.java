package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a RegisterCommand to the server to register a new user.
 */
public class LogoutCommandData extends CommandData {
	
	public LogoutCommandData() {
		super(CommandType.LOGOUT);
	}
}
