package byu.codemonkeys.tickettoride.shared.commands;

/**
 * The client sends a LoginCommand to the server to log in.
 */
public class LoginCommandData extends CommandData {
	private String userName;
	private String password;
	
	public LoginCommandData(String userName, String password) {
		super(CommandType.LOGIN);
		this.userName = userName;
		this.password = password;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
}
