package byu.codemonkeys.tickettoride.mvpcontracts;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface RegisterContract {
	interface View {
		void setUsername(String username);
		
		void setPassword(String password);
		
		void setCanRegister(Boolean canRegister);
	}
	
	interface Presenter {
		void register();
		
		void cancel();
		
		void setUsername(String username);
		
		void setPassword(String password);
		
		void setDefaults();
	}
}
