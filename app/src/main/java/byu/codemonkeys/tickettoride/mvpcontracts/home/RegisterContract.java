package byu.codemonkeys.tickettoride.mvpcontracts.home;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface RegisterContract {
	interface View {
		void setUsername(String username);
		
		String getUsername();
		
		void setPassword(String password);
		
		String getPassword();
		
		void setCanRegister(Boolean canRegister);
	}
	
	interface Presenter {
		void register();
		
		void cancel();
		
		void setDefaults();
		
		boolean canRegister();
	}
}
