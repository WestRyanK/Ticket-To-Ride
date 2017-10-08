package byu.codemonkeys.tickettoride.mvpcontracts;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface ConnectionSettingsContract {
	interface View {
		void setHostName(String host);
		
		String getHostName();
		
		void setPort(String port);
		
		String getPort();
		
		void setCanSave(Boolean canSave);
	}
	
	interface Presenter {
		void saveConnectionSettings();
		
		void cancel();
		
		void setDefaults();
		
		boolean canSaveConnectionSettings();
	}
}
