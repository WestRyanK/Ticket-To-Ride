package byu.codemonkeys.tickettoride.mvpcontracts;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface ConnectionSettingsContract {
	interface View {
		void setHost(String host);
		
		void setPort(String port);
		
		void setCanSave(Boolean canSave);
	}
	
	interface Presenter {
		void saveConnectionSettings();
		
		void cancel();
		
		void setHost(String host);
		
		void setPort(String port);
		
		void setDefaults();
	}
}
