package byu.codemonkeys.tickettoride.shared.model;


import java.util.Observable;

public class UserBase extends ObservableExt{
	protected String userName;
	public static final int MAX_USERNAME_LENGTH = 12;
	public static final int MIN_USERNAME_LENGTH = 6;
	public static final int MAX_PASSWORD_LENGTH = 20;
	public static final int MIN_PASSWORD_LENGTH = 8;
	
	public UserBase(String userName) {
		this.userName = userName;
	}
	
	public String getUsername() {
		return userName;
	}
	
	public static boolean isValidUsername(String username) {
		return (username != null &&
				!username.contains(" ") &&
				username.length() >= MIN_USERNAME_LENGTH &&
				username.length() <= MAX_USERNAME_LENGTH);
	}
	
	public static boolean isValidPassword(String password) {
		return (password != null &&
				password.length() >= MIN_PASSWORD_LENGTH &&
				password.length() <= MAX_PASSWORD_LENGTH);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		
		if (this == o)
			return true;
		
		if (getClass() != o.getClass())
			return false;
		
		UserBase other = (UserBase) o;
		
		return userName.equals(other.getUsername());
	}
}
