package byu.codemonkeys.tickettoride.mvpcontracts.game;

/**
 * Created by Ryan on 10/30/2017.
 */

public interface GameContract {
	interface View {
		
	}
	
	interface Presenter {
		void startPolling();
		
		void stopPolling();
	}
}
