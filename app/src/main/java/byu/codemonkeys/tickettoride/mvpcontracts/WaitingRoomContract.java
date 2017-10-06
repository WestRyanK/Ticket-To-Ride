package byu.codemonkeys.tickettoride.mvpcontracts;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface WaitingRoomContract {
	interface View {
		void setPendingGameInfo(PendingGame pendingGame);
	}
	
	interface Presenter {
		void startGame();
		
		void leaveGame();
	}
}
