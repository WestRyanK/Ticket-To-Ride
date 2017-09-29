package byu.codemonkeys.tickettoride.mvpcontracts;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface LoginContract {
	interface View {
		void setUsername(String username);
		
		void setPassword(String password);
		
		void setCanLogin(Boolean canLogin);
	}
	
	interface Presenter {
		void login();
		
		void navigateRegisterUser();
		
		void navigateConnectionSettings();
		
		void setUsername(String username);
		
		void setPassword(String password);
	}
}
