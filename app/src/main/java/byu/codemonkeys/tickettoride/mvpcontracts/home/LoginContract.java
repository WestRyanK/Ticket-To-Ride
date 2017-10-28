package byu.codemonkeys.tickettoride.mvpcontracts.home;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface LoginContract {
	interface View {
		void setUsername(String username);
		
		String getUsername();
		
		void setPassword(String password);
		
		String getPassword();
		
		void setCanLogin(Boolean canLogin);
	}
	
	interface Presenter {
		void login();
		
		void navigateRegisterUser();
		
		void navigateConnectionSettings();
		
		void setDefaults();
		
		boolean canLogin();
	}
}
