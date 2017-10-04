package byu.codemonkeys.tickettoride.mvpcontracts;

import byu.codemonkeys.tickettoride.models.PendingGame;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface WaitingRoomContract {
	interface View {
		void setPendingGameInfo(PendingGame pendingGame);
		
		void setIsStartGameVisible(boolean isStartGameVisible);
	}
	
	interface Presenter {
		void startGame();
		
		void leaveGame();
	}
}
