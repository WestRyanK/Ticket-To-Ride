package byu.codemonkeys.tickettoride.mvpcontracts.game;

/**
 * Created by Ryan on 10/16/2017.
 */

public interface GameSidebarContract {
	interface View {
		
	}
	
	interface Presenter {
		void navigateDestinationCards();
		
		void navigateDrawTrainCards();
		
		void navigateChatHistory();
	}
}
