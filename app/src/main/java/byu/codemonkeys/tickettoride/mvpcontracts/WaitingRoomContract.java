package byu.codemonkeys.tickettoride.mvpcontracts;

import java.util.List;

import byu.codemonkeys.tickettoride.shared.model.UserBase;

/**
 * Created by Ryan on 9/29/2017.
 */

public interface WaitingRoomContract {
	interface View {
		void setPendingGameName(String gameName);
		
		void setWaitingUsers(List<UserBase> users);
		
		void setIsStartGameVisible(boolean isStartGameVisible);
	}
	
	interface Presenter {
		void startGame();
		
		void leaveGame();
		
		void setDefaults();
		
		void startPolling();
		
		void stopPolling();
	}
}
