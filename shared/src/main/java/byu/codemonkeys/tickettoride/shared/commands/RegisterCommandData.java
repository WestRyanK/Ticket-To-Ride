package byu.codemonkeys.tickettoride.shared.commands;

public class RegisterCommandData extends CommandData {
	private String userName;
	private String password;
	
	public RegisterCommandData(String userName, String password) {
		super(CommandType.REGISTER);
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
